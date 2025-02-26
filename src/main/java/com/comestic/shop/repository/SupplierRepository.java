package com.comestic.shop.repository;

import com.comestic.shop.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
     Optional<Supplier> findBySupplierName(String supplierName);

    // Find suppliers by name containing keyword, paginated
    Page<Supplier> findBySupplierNameContainingIgnoreCase(String supplierName, Pageable pageable);
}
