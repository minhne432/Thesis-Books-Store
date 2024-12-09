package com.comestic.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionCheckerService {

    @Autowired
    private PermissionService permissionService;

    /**
     * Kiểm tra xem người dùng hiện tại có permission tương ứng hay không.
     * @param permissionRequired chuỗi định danh permission cần kiểm tra
     * @return true nếu user có permission, false nếu không
     */
    public boolean hasPermission(String permissionRequired) {
        Set<String> permissions = permissionService.getUserPermissions();
        return permissions.contains(permissionRequired);
    }
}