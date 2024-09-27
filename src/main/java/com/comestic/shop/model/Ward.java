package com.comestic.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wardID;

    @ManyToOne
    @JoinColumn(name = "districtID", nullable = false)
    private District district;

    @Column(nullable = false)
    private String wardName;

    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<Address> addresses;

    // Getters and Setters

    public int getWardID() {
        return wardID;
    }

    public District getDistrict() {
        return district;
    }

    public String getWardName() {
        return wardName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setWardID(int wardID) {
        this.wardID = wardID;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
