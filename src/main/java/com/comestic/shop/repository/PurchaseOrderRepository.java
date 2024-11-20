package com.comestic.shop.repository;

import com.comestic.shop.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {
    // You can add custom methods if needed
    // For example:
    // List<PurchaseOrder> findBySupplierId(Long supplierId);
}
