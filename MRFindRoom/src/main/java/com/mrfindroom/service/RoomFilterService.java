package com.mrfindroom.service;

import com.mrfindroom.data.RoomData;
import com.mrfindroom.model.Room;
import com.mrfindroom.model.SmartFilter;
import com.mrfindroom.util.TextNormalizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Toàn bộ logic lọc & tìm kiếm phòng — chuyển từ script.js
 */
public class RoomFilterService {

    // ── Synonym map — tương đương synonyms{} trong script.js ──
    private static final Map<String, List<String>> SYNONYMS = new LinkedHashMap<>();
    static {
        SYNONYMS.put("wifi",            Arrays.asList("wifi","mang","internet","wi-fi"));
        SYNONYMS.put("may lanh",        Arrays.asList("may lanh","dieu hoa","dieu hoa","lanh","ac"));
        SYNONYMS.put("giu xe",          Arrays.asList("giu xe","dau xe","xe may","parking","gui xe"));
        SYNONYMS.put("nong lanh",       Arrays.asList("nong lanh","binh nong lanh","water heater","voi nong"));
        SYNONYMS.put("wc rieng",        Arrays.asList("wc rieng","toilet rieng","nha ve sinh rieng","phong tam rieng","wc","toilet"));
        SYNONYMS.put("gan truong",      Arrays.asList("gan truong","gan dai hoc","gan truong hoc","gan dh","sinh vien"));
        SYNONYMS.put("ban cong",        Arrays.asList("ban cong","balcony","san thuong"));
        SYNONYMS.put("re",              Arrays.asList("re","gia re","tiet kiem","sinh vien"));
        SYNONYMS.put("trung tam",       Arrays.asList("trung tam","hai chau","bach dang"));
        SYNONYMS.put("noi that",        Arrays.asList("noi that","day du","co tu","co giuong","co ban"));
        SYNONYMS.put("khong chung chu", Arrays.asList("khong chung chu","tu do","rieng tu"));
        SYNONYMS.put("thoang",          Arrays.asList("thoang","thoang mat","cua so","thoang dang"));
        SYNONYMS.put("bien",            Arrays.asList("bien","my khe","view bien","son tra"));
    }

    // ── Price ranges — tương đương priceRanges{} trong script.js ──
    public static final Map<String, long[]> PRICE_RANGES = new LinkedHashMap<>();
    static {
        PRICE_RANGES.put("Dưới 1 triệu",  new long[]{0, 1_000_000});
        PRICE_RANGES.put("1 – 2 triệu",   new long[]{1_000_000, 2_000_000});
        PRICE_RANGES.put("2 – 3 triệu",   new long[]{2_000_000, 3_000_000});
        PRICE_RANGES.put("3 – 5 triệu",   new long[]{3_000_000, 5_000_000});
        PRICE_RANGES.put("5 – 7 triệu",   new long[]{5_000_000, 7_000_000});
        PRICE_RANGES.put("Trên 7 triệu",  new long[]{7_000_000, Long.MAX_VALUE});
    }

    // ── Amenity labels for UI ──
    public static final Map<String, String> AMENITY_LABELS = new LinkedHashMap<>();
    static {
        AMENITY_LABELS.put("wifi",     "📶 Wifi");
        AMENITY_LABELS.put("ac",       "❄️ Máy lạnh");
        AMENITY_LABELS.put("parking",  "🛵 Giữ xe");
        AMENITY_LABELS.put("hotwater", "🚿 Nóng lạnh");
        AMENITY_LABELS.put("toilet",   "🚽 WC riêng");
        AMENITY_LABELS.put("school",   "🏫 Gần trường");
    }

    // ── Expand keyword with synonyms ──
    private Set<String> expandKeyword(String kw) {
        String normKw = TextNormalizer.normalize(kw);
        Set<String> expanded = new LinkedHashSet<>();
        expanded.add(normKw);
        for (List<String> variants : SYNONYMS.values()) {
            boolean matched = variants.stream()
                .anyMatch(v -> {
                    String nv = TextNormalizer.normalize(v);
                    return nv.contains(normKw) || normKw.contains(nv);
                });
            if (matched) {
                variants.forEach(v -> expanded.add(TextNormalizer.normalize(v)));
            }
        }
        return expanded;
    }

    /** Score của 1 phòng với keyword — tương đương scoreRoom() trong script.js */
    public int scoreRoom(Room room, String keyword) {
        if (keyword == null || keyword.isBlank()) return 1;
        String haystack = TextNormalizer.normalize(room.toSearchString());
        String[] tokens = keyword.toLowerCase().trim().split("\\s+");
        int matchCount = 0;
        for (String token : tokens) {
            for (String term : expandKeyword(token)) {
                if (haystack.contains(term)) { matchCount++; break; }
            }
        }
        return matchCount;
    }

    /**
     * Lọc & sắp xếp phòng theo keyword + bộ lọc giá + amenity
     * Tương đương filterRooms() trong script.js
     */
    public List<Room> filter(String keyword,
                             Set<String> activePriceKeys,
                             Set<String> activeAmenityKeys) {
        return RoomData.ALL_ROOMS.stream()
            .map(room -> Map.entry(room, scoreRoom(room, keyword)))
            .filter(e -> {
                if (e.getValue() == 0) return false;
                // Lọc giá
                if (!activePriceKeys.isEmpty()) {
                    boolean priceMatch = activePriceKeys.stream().anyMatch(pk -> {
                        long[] range = PRICE_RANGES.get(pk);
                        return range != null
                            && e.getKey().getPrice() >= range[0]
                            && e.getKey().getPrice() <= range[1];
                    });
                    if (!priceMatch) return false;
                }
                // Lọc tiện nghi
                if (!activeAmenityKeys.isEmpty()) {
                    boolean amenityMatch = activeAmenityKeys.stream()
                        .allMatch(ak -> e.getKey().hasAmenity(ak));
                    if (!amenityMatch) return false;
                }
                return true;
            })
            .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * Lọc theo SmartFilter từ AI — tương đương applySmartFilter() trong script.js
     */
    public List<Room> applySmartFilter(SmartFilter sf) {
        return RoomData.ALL_ROOMS.stream()
            .filter(room -> {
                if (sf.maxPrice != null && room.getPrice() > sf.maxPrice) return false;
                if (sf.minPrice != null && room.getPrice() < sf.minPrice) return false;
                if (!sf.areas.isEmpty()) {
                    String normAddr = TextNormalizer.normalize(room.getArea() + " " + room.getAddress());
                    boolean areaMatch = sf.areas.stream()
                        .anyMatch(a -> normAddr.contains(TextNormalizer.normalize(a)));
                    if (!areaMatch) return false;
                }
                for (String a : sf.amenities) {
                    if (!room.hasAmenity(a)) return false;
                }
                return true;
            })
            .collect(Collectors.toList());
    }

    /**
     * Parser thủ công (fallback khi không có AI)
     * Tương đương parseSmartQuery() trong script.js
     *
     * FIX lỗi 3 & 4:
     *   - Sửa tất cả regex matches() dùng group pattern .*(X|Y|Z).* thay vì .*X|Y|Z.*
     *   - Xoá block contains() trùng lặp phía dưới
     */
    public SmartFilter parseSmartQuery(String text) {
        SmartFilter out = new SmartFilter();
        String t = TextNormalizer.normalize(text);

        // Giá dưới X triệu
        var underM = java.util.regex.Pattern.compile("duoi\\s*([\\d,.]+)\\s*(trieu|tr\\b|000000)")
            .matcher(t);
        if (underM.find()) {
            out.maxPrice = (long)(Double.parseDouble(underM.group(1).replace(",",".")) * 1_000_000);
        }

        // Giá trên X triệu
        var aboveM = java.util.regex.Pattern.compile("tren\\s*([\\d,.]+)\\s*(trieu|tr\\b)")
            .matcher(t);
        if (aboveM.find()) {
            out.minPrice = (long)(Double.parseDouble(aboveM.group(1).replace(",",".")) * 1_000_000);
        }

        // Khoảng X-Y triệu
        var rangeM = java.util.regex.Pattern.compile("([\\d,.]+)\\s*[-–~]\\s*([\\d,.]+)\\s*(trieu|tr\\b)")
            .matcher(t);
        if (rangeM.find()) {
            out.minPrice = (long)(Double.parseDouble(rangeM.group(1).replace(",",".")) * 1_000_000);
            out.maxPrice = (long)(Double.parseDouble(rangeM.group(2).replace(",",".")) * 1_000_000);
        }

        // FIX: dùng .*(A|B|C).* để matches() hoạt động đúng với nhiều từ khoá
        // FIX: bỏ block contains() trùng lặp ở cuối (đã bao gồm trong matches bên dưới)
        if (t.matches(".*(wifi|mang|internet).*"))                          out.amenities.add("wifi");
        if (t.matches(".*(may lanh|dieu hoa|lanh).*"))                      out.amenities.add("ac");
        if (t.matches(".*(giu xe|gui xe|dau xe|parking).*"))                out.amenities.add("parking");
        if (t.matches(".*(nong lanh|binh nong|voi nong).*"))                out.amenities.add("hotwater");
        if (t.matches(".*(wc rieng|toilet rieng|nha ve sinh rieng).*"))     out.amenities.add("toilet");
        if (t.matches(".*(gan truong|gan dh|gan dai hoc).*"))               out.amenities.add("school");

        List<String> areaKeywords = Arrays.asList(
            "hai chau","thanh khe","ngu hanh son","son tra",
            "lien chieu","cam le","hoa vang","hoang sa"
        );
        for (String a : areaKeywords) {
            if (t.contains(a)) out.areas.add(a);
        }

        // REMOVED: block contains() trùng lặp đã xoá
        // if (t.contains("wifi"))     out.amenities.add("wifi");
        // if (t.contains("may lanh") || t.contains("dieu hoa")) out.amenities.add("ac");

        return out;
    }
}