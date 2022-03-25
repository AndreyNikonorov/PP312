package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class RoleService {
    private RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    public Role getRoleById(long id) {
        return roleRepository.findByRoleid(id);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }


    public void removeRole(long id) {
        roleRepository.deleteById(id);
    }


//    public RoleRepository getRoleRepository() {
//        return roleRepository;
//    }
//
//    public void setRoleRepository(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
}
