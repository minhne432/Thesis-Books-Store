package com.comestic.shop.repository;

import com.comestic.shop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<Inventory> findByBranchId(Long branchId);
}
