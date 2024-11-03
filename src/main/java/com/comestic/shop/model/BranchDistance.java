package com.comestic.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BranchDistance")
public class BranchDistance {

    @EmbeddedId
    private BranchDistanceId id;

    @ManyToOne
    @MapsId("branchId")
    @JoinColumn(name = "BranchID", nullable = false)
    private Branch branch;

    @ManyToOne
    @MapsId("wardID")
    @JoinColumn(name = "WardID", nullable = false)
    private Ward ward;

    @Column(name = "Distance", nullable = false, precision = 10, scale = 2)
    private BigDecimal distance;

    // Constructors
    public BranchDistance() {}

    public BranchDistance(Branch branch, Ward ward, BigDecimal distance) {
        this.branch = branch;
        this.ward = ward;
        this.distance = distance;
        this.id = new BranchDistanceId(branch.getBranchId(), ward.getWardID());
    }

    // Getters and Setters
    public BranchDistanceId getId() {
        return id;
    }

    public void setId(BranchDistanceId id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
        if (this.id == null) {
            this.id = new BranchDistanceId();
        }
        this.id.setBranchId(branch.getBranchId());
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
        if (this.id == null) {
            this.id = new BranchDistanceId();
        }
        this.id.setWardID(ward.getWardID());
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }
}
