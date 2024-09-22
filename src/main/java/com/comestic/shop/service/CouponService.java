package com.comestic.shop.service;

import com.comestic.shop.model.Coupon;
import com.comestic.shop.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }

    public Coupon addCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Long id, Coupon couponDetails) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            coupon.setCode(couponDetails.getCode());
            coupon.setDescription(couponDetails.getDescription());
            coupon.setDiscountType(couponDetails.getDiscountType());
            coupon.setDiscountValue(couponDetails.getDiscountValue());
            coupon.setStartDate(couponDetails.getStartDate());
            coupon.setEndDate(couponDetails.getEndDate());
            coupon.setMinimumOrderValue(couponDetails.getMinimumOrderValue());
            coupon.setMaxUsageLimit(couponDetails.getMaxUsageLimit());
            coupon.setIsActive(couponDetails.getIsActive());
            return couponRepository.save(coupon);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    public boolean isValidCoupon(Coupon coupon, LocalDate currentDate, BigDecimal orderTotal) {
        return coupon.isValid(currentDate, orderTotal);
    }

    public BigDecimal applyCouponDiscount(Coupon coupon, BigDecimal orderTotal) {
        return coupon.applyDiscount(orderTotal);
    }
}
