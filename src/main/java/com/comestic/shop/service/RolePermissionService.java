package com.comestic.shop.service;

import com.comestic.shop.model.RolePermission;
import com.comestic.shop.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    // Lấy tất cả RolePermissions
    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionRepository.findAll();
    }

    // Lấy RolePermission theo ID
    public Optional<RolePermission> getRolePermissionById(int id) {
        return rolePermissionRepository.findById(id);
    }

    // Thêm RolePermission mới
    @Transactional
    public RolePermission addRolePermission(RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

    // Cập nhật RolePermission theo ID
    @Transactional
    public RolePermission updateRolePermission(int id, RolePermission rolePermissionDetails) {
        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(id);
        if (optionalRolePermission.isPresent()) {
            RolePermission rolePermission = optionalRolePermission.get();
            rolePermission.setRole(rolePermissionDetails.getRole());
            rolePermission.setPermission(rolePermissionDetails.getPermission());
            return rolePermissionRepository.save(rolePermission);
        } else {
            throw new IllegalArgumentException("RolePermission not found with id: " + id);
        }
    }

    // Xóa RolePermission theo ID
    @Transactional
    public void deleteRolePermission(int id) {
        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(id);
        if (optionalRolePermission.isPresent()) {
            rolePermissionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("RolePermission not found with id: " + id);
        }
    }
}
