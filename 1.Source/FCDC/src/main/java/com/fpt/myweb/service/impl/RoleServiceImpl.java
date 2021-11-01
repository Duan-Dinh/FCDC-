package com.fpt.myweb.service.impl;


import com.fpt.myweb.entity.Role;
import com.fpt.myweb.repository.RoleRepository;
import com.fpt.myweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> getAllUser() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(long id) {
        return roleRepository.getById(id);
    }
}
