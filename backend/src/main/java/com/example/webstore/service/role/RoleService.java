package com.example.webstore.service.role;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.Role;

public interface RoleService {
    public Role create(Role role);
    public Optional<Role> findByName(String name);
    public List<Role> readAll();
    public Role update(Role role);
    public void delete(int id);
}
