package com.mrfindroom.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Kết quả filter từ AI Gemini hoặc parser thủ công
 */
public class SmartFilter {
    public Long maxPrice;
    public Long minPrice;
    public List<String> areas     = new ArrayList<>();
    public List<String> amenities = new ArrayList<>();
    public String friendlyReply   = "";
    public int count              = 0;

    public boolean hasAnyFilter() {
        return maxPrice != null || minPrice != null
                || !areas.isEmpty() || !amenities.isEmpty();
    }
}
