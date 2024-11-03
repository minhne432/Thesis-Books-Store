package com.comestic.shop.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BranchDistanceId implements Serializable {

    private Long branchId;
    private Integer wardID;

    // Constructors
    public BranchDistanceId() {}

    public BranchDistanceId(Long branchId, Integer wardID) {
        this.branchId = branchId;
        this.wardID = wardID;
    }

    // Getters and Setters
    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Integer getWardID() {
        return wardID;
    }

    public void setWardID(Integer wardID) {
        this.wardID = wardID;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchDistanceId that = (BranchDistanceId) o;

        if (!Objects.equals(branchId, that.branchId)) return false;
        return Objects.equals(wardID, that.wardID);
    }

    @Override
    public int hashCode() {
        int result = branchId != null ? branchId.hashCode() : 0;
        result = 31 * result + (wardID != null ? wardID.hashCode() : 0);
        return result;
    }
}
