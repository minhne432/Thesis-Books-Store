package com.comestic.shop.dto;

public class DistrictDTO {
    private int districtID;
    private String districtName;

    // Constructors
    public DistrictDTO(int districtID, String districtName) {
        this.districtID = districtID;
        this.districtName = districtName;
    }

    // Getters and setters
    public int getDistrictID() { return districtID; }
    public void setDistrictID(int districtID) { this.districtID = districtID; }

    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }
}
