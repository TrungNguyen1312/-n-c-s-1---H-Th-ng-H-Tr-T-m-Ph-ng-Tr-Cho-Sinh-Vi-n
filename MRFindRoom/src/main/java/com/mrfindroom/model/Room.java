package com.mrfindroom.model;

/**
 * Model phòng trọ — tương đương object trong rooms[] của script.js
 */
public class Room {
    private final int id;
    private final String name;
    private final String area;
    private final String address;
    private final long price;
    private final int deposit;
    private final String imageUrl;
    private final String phone;
    private final boolean nearSchool;
    private final boolean wifi;
    private final boolean airConditioner;
    private final boolean parking;
    private final boolean hotWater;
    private final boolean ownToilet;
    private final boolean balcony;
    private final String description;

    public Room(int id, String name, String area, String address, long price, int deposit,
                String imageUrl, String phone,
                boolean nearSchool, boolean wifi, boolean airConditioner,
                boolean parking, boolean hotWater, boolean ownToilet, boolean balcony,
                String description) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.address = address;
        this.price = price;
        this.deposit = deposit;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.nearSchool = nearSchool;
        this.wifi = wifi;
        this.airConditioner = airConditioner;
        this.parking = parking;
        this.hotWater = hotWater;
        this.ownToilet = ownToilet;
        this.balcony = balcony;
        this.description = description;
    }

    // Getters
    public int getId()            { return id; }
    public String getName()       { return name; }
    public String getArea()       { return area; }
    public String getAddress()    { return address; }
    public long getPrice()        { return price; }
    public int getDeposit()       { return deposit; }
    public String getImageUrl()   { return imageUrl; }
    public String getPhone()      { return phone; }
    public boolean isNearSchool() { return nearSchool; }
    public boolean isWifi()       { return wifi; }
    public boolean isAirConditioner() { return airConditioner; }
    public boolean isParking()    { return parking; }
    public boolean isHotWater()   { return hotWater; }
    public boolean isOwnToilet()  { return ownToilet; }
    public boolean isBalcony()    { return balcony; }
    public String getDescription(){ return description; }

    /**
     * Lấy giá trị amenity theo tên key (dùng cho filter logic)
     */
    public boolean hasAmenity(String key) {
        return switch (key) {
            case "wifi"     -> wifi;
            case "ac"       -> airConditioner;
            case "parking"  -> parking;
            case "hotwater" -> hotWater;
            case "toilet"   -> ownToilet;
            case "school"   -> nearSchool;
            case "balcony"  -> balcony;
            default         -> false;
        };
    }

    /**
     * Trả về chuỗi tất cả field dùng cho fuzzy search
     */
    public String toSearchString() {
        return String.join(" ", name, area, address, description, phone);
    }

    @Override
    public String toString() {
        return name + " - " + area + " - " + String.format("%,d", price) + " VNĐ/tháng";
    }
}
