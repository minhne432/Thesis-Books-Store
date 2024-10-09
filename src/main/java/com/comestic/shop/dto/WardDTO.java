package com.comestic.shop.dto;

public class WardDTO {
    private Integer wardID;
    private String wardName;

    public WardDTO(Integer wardID, String wardName) {
        this.wardID = wardID;
        this.wardName = wardName;
    }

    // Getters và setters

    public Integer getWardID() {
        return wardID;
    }

    public void setWardID(Integer wardID) {
        this.wardID = wardID;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardName() {
        return wardName;
    }
}
