package com.comestic.shop.service;

import com.comestic.shop.model.RolePermission;
import com.comestic.shop.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionRepository.findAll();
    }

    public Optional<RolePermission> getRolePermissionById(int id) {
        return rolePermissionRepository.findById(id);
    }

    public RolePermission addRolePermission(RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

    public RolePermission updateRolePermission(int id, RolePermission rolePermissionDetails) {
        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(id);
        if (optionalRolePermission.isPresent()) {
            RolePermission rolePermission = optionalRolePermission.get();
            rolePermission.setRole(rolePermissionDetails.getRole());
            rolePermission.setPermission(rolePermissionDetails.getPermission());
            return rolePermissionRepository.save(rolePermission);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteRolePermission(int id) {
        rolePermissionRepository.deleteById(id);
    }
}
