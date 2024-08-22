package com.example.webstore.service.role;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.exceptions.RoleNotFoundException;
import com.example.webstore.model.Role;
import com.example.webstore.repository.RoleRepository;

/**
 * Класс сервиса для работы с ролями пользователей. Внедряемые зависимости: 
 * roleRepository - crud репозиторий
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> readAll() {
        return (List<Role>)roleRepository.findAll();
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        }
        else {
            throw new RoleNotFoundException(String.format("Роль с именем %s не найдена!", name));
        }
    }
    
}
