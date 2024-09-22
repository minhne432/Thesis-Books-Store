package com.comestic.shop.repository;

import com.comestic.shop.model.PurchaseOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailsRepository extends JpaRepository<PurchaseOrderDetails, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<PurchaseOrderDetails> findByPurchaseOrderId(Long purchaseOrderId);
}
