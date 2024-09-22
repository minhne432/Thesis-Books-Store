package com.comestic.shop.service;

import com.comestic.shop.model.UserRole;
import com.comestic.shop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getUserRoleById(int id) {
        return userRoleRepository.findById(id);
    }

    public List<UserRole> getUserRolesByCustomerId(int customerId) {
        return userRoleRepository.findByCustomerId(customerId);
    }

    public List<UserRole> getUserRolesByRoleId(int roleId) {
        return userRoleRepository.findByRoleId(roleId);
    }

    public UserRole addUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole updateUserRole(int id, UserRole userRoleDetails) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()) {
            UserRole userRole = optionalUserRole.get();
            userRole.setCustomer(userRoleDetails.getCustomer());
            userRole.setRole(userRoleDetails.getRole());
            return userRoleRepository.save(userRole);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteUserRole(int id) {
        userRoleRepository.deleteById(id);
    }
}
