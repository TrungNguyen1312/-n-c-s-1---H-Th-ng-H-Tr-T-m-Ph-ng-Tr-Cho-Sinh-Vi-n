package com.mrfindroom.service;

import com.google.gson.*;
import com.mrfindroom.model.SmartFilter;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

/**
 * Gọi Gemini AI — tương đương sendSmartMessage() trong script.js
 */
public class GeminiService {

    // ── API key — giống GEMINI_API_KEY trong script.js ──
    private static final String GEMINI_API_KEY = "AIzaSyDkA5FpEj29Gi2R5WBFD1C50xikF5it_kM";
    private static final String GEMINI_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

    private static final String SYSTEM_PROMPT =
        "Bạn là trợ lý tìm phòng trọ sinh viên tại Đà Nẵng tên là MR-FindRoom. Luôn trả lời thân thiện bằng tiếng Việt.\n\n" +
        "QUAN TRỌNG: Mọi phản hồi BẮT BUỘC kết thúc bằng đúng 1 thẻ <filter>...</filter>.\n" +
        "- friendlyReply KHÔNG ĐƯỢC để trống, luôn phải có nội dung.\n" +
        "- Nếu người dùng chào hỏi hoặc hỏi ngoài chủ đề phòng trọ → điền friendlyReply, còn lại để null/[].\n" +
        "- Nếu người dùng tìm phòng → điền đầy đủ tất cả các trường.\n\n" +
        "Định dạng (không thay đổi tên field):\n" +
        "<filter>{\"maxPrice\":null,\"minPrice\":null,\"areas\":[],\"amenities\":[],\"friendlyReply\":\"nội dung trả lời\"}</filter>\n\n" +
        "Quy tắc:\n" +
        "- areas: \"Hải Châu\",\"Thanh Khê\",\"Ngũ Hành Sơn\",\"Sơn Trà\",\"Liên Chiểu\",\"Cẩm Lệ\",\"Hòa Vang\"\n" +
        "- amenities: \"wifi\",\"ac\",\"parking\",\"hotwater\",\"toilet\",\"school\"\n" +
        "- Giá tính bằng VNĐ (\"2 triệu\" = 2000000)";

    private final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    private final Gson gson = new Gson();

    public record GeminiResult(String friendlyReply, SmartFilter filter, boolean hasFilter) {}

    /**
     * Gọi Gemini API với text của user, trả về GeminiResult
     */
    public GeminiResult query(String userText) throws IOException {
        String bodyJson = buildRequestBody(userText);
        Request request = new Request.Builder()
            .url(GEMINI_URL)
            .post(RequestBody.create(bodyJson, MediaType.get("application/json")))
            .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code() + ": " + responseBody);
            }
            return parseGeminiResponse(responseBody);
        }
    }

    private String buildRequestBody(String userText) {
        JsonObject body = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();
        part.addProperty("text", SYSTEM_PROMPT + "\n\nUser: " + userText);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        body.add("contents", contents);

        JsonObject genConfig = new JsonObject();
        genConfig.addProperty("temperature", 0.3);
        genConfig.addProperty("maxOutputTokens", 512);
        body.add("generationConfig", genConfig);

        return gson.toJson(body);
    }

    private GeminiResult parseGeminiResponse(String responseBody) {
        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray candidates = json.getAsJsonArray("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("Gemini không trả về kết quả");
        }

        String rawText = candidates.get(0)
            .getAsJsonObject()
            .getAsJsonObject("content")
            .getAsJsonArray("parts")
            .get(0)
            .getAsJsonObject()
            .get("text")
            .getAsString();

        // Trích <filter>...</filter>
        Pattern p = Pattern.compile("<filter>([\\s\\S]*?)</filter>");
        Matcher m = p.matcher(rawText);

        if (m.find()) {
            String filterJson = m.group(1).trim();
            SmartFilter sf = parseFilterJson(filterJson);
            boolean hasFilter = sf.hasAnyFilter();
            return new GeminiResult(sf.friendlyReply, sf, hasFilter);
        } else {
            // Không có tag filter → trả về plain text
            SmartFilter sf = new SmartFilter();
            sf.friendlyReply = rawText.trim();
            return new GeminiResult(rawText.trim(), sf, false);
        }
    }

    private SmartFilter parseFilterJson(String json) {
        SmartFilter sf = new SmartFilter();
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            if (obj.has("maxPrice") && !obj.get("maxPrice").isJsonNull())
                sf.maxPrice = obj.get("maxPrice").getAsLong();

            if (obj.has("minPrice") && !obj.get("minPrice").isJsonNull())
                sf.minPrice = obj.get("minPrice").getAsLong();

            if (obj.has("areas") && obj.get("areas").isJsonArray()) {
                for (JsonElement e : obj.getAsJsonArray("areas"))
                    sf.areas.add(e.getAsString());
            }

            if (obj.has("amenities") && obj.get("amenities").isJsonArray()) {
                for (JsonElement e : obj.getAsJsonArray("amenities"))
                    sf.amenities.add(e.getAsString());
            }

            if (obj.has("friendlyReply") && !obj.get("friendlyReply").isJsonNull())
                sf.friendlyReply = obj.get("friendlyReply").getAsString();

        } catch (Exception e) {
            sf.friendlyReply = "Có lỗi phân tích kết quả AI.";
        }
        return sf;
    }

    public boolean hasApiKey() {
        return GEMINI_API_KEY != null
            && !GEMINI_API_KEY.isBlank()
            && !GEMINI_API_KEY.equals("PASTE_YOUR_KEY_HERE");
    }
}
