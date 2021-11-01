package com.fpt.myweb.service;

import com.fpt.myweb.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> getAllUser();

    public Role getRole(long id);

}
