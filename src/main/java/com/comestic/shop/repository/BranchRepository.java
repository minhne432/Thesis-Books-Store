package com.comestic.shop.repository;

import com.comestic.shop.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // Optional<Branch> findByBranchName(String branchName);
    // Tìm kiếm chi nhánh theo tên chứa từ khóa, phân trang
    Page<Branch> findByBranchNameContainingIgnoreCase(String branchName, Pageable pageable);

    // Tìm kiếm chi nhánh theo ID
    Optional<Branch> findByBranchId(Long branchId);

    // Tìm tất cả các chi nhánh, phân trang
    @Override
    Page<Branch> findAll(Pageable pageable);
}
