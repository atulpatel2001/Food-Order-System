package com.food.app.user.services.imple;

import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.model.Role;
import com.food.app.user.repo.RoleRepository;
import com.food.app.user.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImple implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role updateRole(Role role) {
        Optional<Role> oldRole = this.roleRepository.findByName(role.getName());

        if(oldRole.isPresent()){
            Role oldRoles = oldRole.get();

            oldRoles.setName(role.getName());
            return this.roleRepository.save(oldRoles);
        }
        else {
            throw new ResourceNotFoundException("Role", "Name", role.getName());
        }
    }

    @Override
    public void deleteRole(String name) {
        this.roleRepository.deleteByName(name);
    }
}
