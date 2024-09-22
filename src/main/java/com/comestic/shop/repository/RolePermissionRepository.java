package com.comestic.shop.repository;

import com.comestic.shop.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<RolePermission> findByRoleId(int roleId);
}
