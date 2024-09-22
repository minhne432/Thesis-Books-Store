package com.comestic.shop.repository;

import com.comestic.shop.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // Optional<Branch> findByBranchName(String branchName);
}
