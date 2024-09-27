package com.comestic.shop.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int provinceID;

    @Column(nullable = false)
    private String provinceName;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<District> districts;

    // Getters and Setters

    public int getProvinceID() {
        return provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}