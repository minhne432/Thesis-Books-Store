package com.comestic.shop.repository;

import com.comestic.shop.model.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Integer> {

    List<CouponUsage> findByOrder_OrderID(int orderID);

    List<CouponUsage> findByCoupon_CouponId(Long couponId);

    List<CouponUsage> findByCustomer_CustomerID(int customerID);
}
