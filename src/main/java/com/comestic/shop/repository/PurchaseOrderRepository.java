package com.comestic.shop.repository;

import com.comestic.shop.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<PurchaseOrder> findBySupplierId(Long supplierId);
}
