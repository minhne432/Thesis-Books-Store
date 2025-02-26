package com.comestic.shop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtID; // Chuyển từ int sang Integer


    @ManyToOne
    @JoinColumn(name = "provinceID", nullable = false)
    private Province province;

    @Column(nullable = false)
    private String districtName;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Ward> wards;

    // Getters and Setters

    public Integer getDistrictID() {
        return districtID;
    }

    public Province getProvince() {
        return province;
    }

    public String getDistrictName() {
        return districtName;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}
