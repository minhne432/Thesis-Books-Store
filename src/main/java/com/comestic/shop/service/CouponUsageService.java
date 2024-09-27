package com.comestic.shop.service;

import com.comestic.shop.model.CouponUsage;
import com.comestic.shop.repository.CouponUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponUsageService {

    @Autowired
    private CouponUsageRepository couponUsageRepository;

    // Lấy danh sách tất cả các CouponUsage
    public List<CouponUsage> getAllCouponUsages() {
        return couponUsageRepository.findAll();
    }

    // Lấy CouponUsage theo ID
    public Optional<CouponUsage> getCouponUsageById(int id) {
        return couponUsageRepository.findById(id);
    }

    // Lấy danh sách CouponUsage theo CustomerID
    public List<CouponUsage> getCouponUsagesByCustomerId(int customerId) {
        return couponUsageRepository.findByCustomer_CustomerID(customerId);
    }

    // Lấy danh sách CouponUsage theo CouponID
    public List<CouponUsage> getCouponUsagesByCouponId(Long couponId) {
        return couponUsageRepository.findByCoupon_CouponId(couponId);
    }

    // Lấy danh sách CouponUsage theo OrderID
    public List<CouponUsage> getCouponUsagesByOrderId(int orderId) {
        return couponUsageRepository.findByOrder_OrderID(orderId);
    }

    // Thêm mới CouponUsage
    public CouponUsage addCouponUsage(CouponUsage couponUsage) {
        return couponUsageRepository.save(couponUsage);
    }

    // Cập nhật CouponUsage
    public CouponUsage updateCouponUsage(int id, CouponUsage couponUsageDetails) {
        Optional<CouponUsage> optionalCouponUsage = couponUsageRepository.findById(id);
        if (optionalCouponUsage.isPresent()) {
            CouponUsage couponUsage = optionalCouponUsage.get();

            couponUsage.setCoupon(couponUsageDetails.getCoupon());
            couponUsage.setCustomer(couponUsageDetails.getCustomer());
            couponUsage.setOrder(couponUsageDetails.getOrder());
            couponUsage.setRedemptionDate(couponUsageDetails.getRedemptionDate());
            couponUsage.setDiscountAmount(couponUsageDetails.getDiscountAmount());

            return couponUsageRepository.save(couponUsage);
        } else {
            // Xử lý khi không tìm thấy CouponUsage
            return null; // Hoặc ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    // Xóa CouponUsage theo ID
    public void deleteCouponUsage(int id) {
        couponUsageRepository.deleteById(id);
    }
}
