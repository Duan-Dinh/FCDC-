package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findUsersByPhone(String phone);

    List<User> findAllUserByRoleId(long roleId, Pageable pageable);

    List<User> findByRole(Role role);

    List<User> findByUsernameContaining(String text, Pageable pageable);

    List<User> findByUsernameContaining(String text);

    User findByUsername(String username);

    User findByPhone(String username);

    List<User> findByFullnameContaining(String text, Pageable pageable);

    List<User> findByFullnameContaining(String text);


}
