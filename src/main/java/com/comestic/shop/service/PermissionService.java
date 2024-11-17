package com.comestic.shop.service;

import com.comestic.shop.model.Permission;
import com.comestic.shop.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;


    // Phương thức lấy danh sách permissions của người dùng
    public Set<String> getUserPermissions() {
        // Lấy đối tượng Authentication của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Trả về một Set chứa tất cả các quyền (permissions)
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    // Phương thức kiểm tra quyền của người dùng cho một branchId cụ thể
    public boolean hasPermissionForBranch(Long branchId) {
        Set<String> permissions = getUserPermissions();
        return permissions.contains("view_orders_branch_" + branchId);
    }

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
