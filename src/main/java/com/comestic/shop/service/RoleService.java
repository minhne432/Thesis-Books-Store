package com.comestic.shop.service;

import com.comestic.shop.model.Role;
import com.comestic.shop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(int id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(int id, Role roleDetails) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setRoleName(roleDetails.getRoleName());
            role.setDescription(roleDetails.getDescription());
            return roleRepository.save(role);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
