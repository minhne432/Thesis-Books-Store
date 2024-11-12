package com.comestic.shop.repository;

import com.comestic.shop.model.BranchDistance;
import com.comestic.shop.model.BranchDistanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchDistanceRepository extends JpaRepository<BranchDistance, BranchDistanceId> {

    @Query("""
        SELECT bd
        FROM BranchDistance bd
        WHERE bd.ward.wardID = :wardId
        ORDER BY bd.distance ASC
        """)
    List<BranchDistance> findByWardIdOrderByDistanceAsc(@Param("wardId") Integer wardId);


    @Query("""
        SELECT bd
        FROM BranchDistance bd
        WHERE bd.ward.wardID = :wardId
        ORDER BY bd.distance ASC
        """)
    BranchDistance findNearestBranchByWardId(@Param("wardId") Integer wardId);
}
