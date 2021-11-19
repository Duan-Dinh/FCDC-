package com.fpt.myweb.repository;

import com.fpt.myweb.entity.New;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findUsersByPhone(String phone);

    List<User> findAllByVillage(Village village);

    List<User> findAllUserByRoleId(long roleId);

    @Query(value = "SELECT u FROM User u where role_id = ?1 ORDER BY id")
    List<User> findAllUserByRoleId1(Pageable pageable, Long roleId);

    List<User> findByRole(Role role);

    List<User> findByUsernameContaining(String text, Pageable pageable);

    List<User> findByUsernameContaining(String text);

    User findByUsername(String username);

    @Query(value = "SELECT u FROM User u ORDER BY id")
    List<User> findAllUserWithPagination(Pageable pageable);

    User findByPhone(String username);

    List<User> findByFullnameContaining(String text, Pageable pageable);

    List<User> findByFullnameContaining(Pageable pageable,String text);

    @Query( value = "SELECT * FROM user as u WHERE u.id   NOT IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4", nativeQuery = true)
    List<User> notSentReport(String time);

    @Query( value = "SELECT * FROM user as u WHERE u.id    IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4", nativeQuery = true)
    List<User> sentReport(String time);

}
