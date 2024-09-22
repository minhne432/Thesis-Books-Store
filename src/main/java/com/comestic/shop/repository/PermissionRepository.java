package com.comestic.shop.repository;

import com.comestic.shop.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
     Optional<Permission> findByPermissionName(String permissionName);
}
