package com.example.webstore.service.role;

import java.util.List;

import com.example.webstore.exceptions.RoleNotFoundException;
import com.example.webstore.model.Role;

public interface RoleService {
    public Role create(Role role);
    public Role findByName(String name) throws RoleNotFoundException;
    public List<Role> readAll();
    public Role update(Role role);
    public void delete(int id);
}
