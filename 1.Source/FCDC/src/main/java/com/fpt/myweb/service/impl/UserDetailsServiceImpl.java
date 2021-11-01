package com.fpt.myweb.service.impl;

import com.fpt.myweb.config.UserDetailsImpl;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserDetailsImpl.build(user);
    }

}
