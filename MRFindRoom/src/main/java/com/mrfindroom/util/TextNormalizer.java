package com.mrfindroom.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Tiện ích chuẩn hoá chuỗi tiếng Việt — tương đương hàm normalize() trong script.js
 */
public class TextNormalizer {

    private static final Pattern DIACRITIC = Pattern.compile("[\\p{InCombiningDiacriticalMarks}]");

    /**
     * Chuẩn hoá: bỏ dấu, thường hoá, thay đ→d, trim
     */
    public static String normalize(String text) {
        if (text == null) return "";
        String nfd = Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD);
        String noAccent = DIACRITIC.matcher(nfd).replaceAll("");
        return noAccent.replace("đ", "d").trim();
    }
}
