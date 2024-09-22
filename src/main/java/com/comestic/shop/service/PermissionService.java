package com.comestic.shop.service;

import com.comestic.shop.model.Permission;
import com.comestic.shop.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionById(int id) {
        return permissionRepository.findById(id);
    }

    public Optional<Permission> getPermissionByName(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName);
    }

    public Permission addPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(int id, Permission permissionDetails) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (optionalPermission.isPresent()) {
            Permission permission = optionalPermission.get();
            permission.setPermissionName(permissionDetails.getPermissionName());
            permission.setDescription(permissionDetails.getDescription());
            return permissionRepository.save(permission);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    public void deletePermission(int id) {
        permissionRepository.deleteById(id);
    }
}
