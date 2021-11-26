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

    //findAllPatientForStaff
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = 'F0' and fullname like %?2% ORDER BY id")
    List<User> findAllPatientForStaff(Long village,String key,String search ,Pageable pageable);
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = 'F0' and fullname like %?2% ORDER BY id")
    List<User> findAllPatientForStaff(Long village,String key,String search );

    //searchTextForStaff
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = 'F0' and fullname like %?2% ORDER BY id")
    List<User> findAllTextForStaff(Long village ,String text,Pageable pageable);

    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = 'F0' and fullname like %?2% ORDER BY id")
    List<User> findAllTextForStaff(Long village ,String text);



    //searchTextWithRol
    @Query(value = "SELECT u FROM User u where role_id = ?1 and is_active = 1 and fullname like %?2% ORDER BY id")
    List<User> findAllTextWithRole(Long role_id ,String text,Pageable pageable);

    @Query(value = "SELECT u FROM User u where role_id = ?1 and is_active = 1 and fullname like %?2% ORDER BY id")
    List<User> findAllTextWithRole(Long role_id ,String text);

    //getAllPatientsCuredForStaff
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = '-' and fullname like %?2% ORDER BY id")
    List<User> findAllPatientsCuredForStaff(Long village,String key,String search ,Pageable pageable);

    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = '-' and fullname like %?2% ORDER BY id")
    List<User> findAllPatientsCuredForStaff(Long village,String key,String search );
    //searchAllPatientsCuredForStaff
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = '-' and fullname like %?2% ORDER BY id")
    List<User> searchAllPatientsCuredForStaff(Long village ,String text,Pageable pageable);

    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 4 and result = '-' and fullname like %?2% ORDER BY id")
    List<User> searchAllPatientsCuredForStaff(Long village ,String text);

//notsentreport
    @Query( value = "SELECT * FROM user as u WHERE u.id   NOT IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4 and is_active = 1 and village_id = ?2 and result = 'F0' and fullname like %?3% ORDER BY id", nativeQuery = true)
    List<User> UserNotSentReport(String time,Long village,String text,String key,Pageable pageable);

    @Query( value = "SELECT * FROM user as u WHERE u.id   NOT IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4 and is_active = 1 and village_id = ?2 and result = 'F0' and fullname like %?3% ORDER BY id", nativeQuery = true)
    List<User> UserNotSentReport(String time,Long village,String text,String key);


    //sentreport
    @Query( value = "SELECT * FROM user as u WHERE u.id    IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4 and is_active = 1 and village_id = ?2 and result = 'F0'and fullname like %?3% ORDER BY id", nativeQuery = true)
    List<User> userSentReport(String time,Long village,String text,String key,Pageable pageable);

    @Query( value = "SELECT * FROM user as u WHERE u.id    IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4 and is_active = 1 and village_id = ?2 and result = 'F0' and fullname like %?3% ORDER BY id", nativeQuery = true)
    List<User> userSentReport(String time,Long village,String text,String key);

    //getAllPatientForDoctor
    @Query(value = "SELECT u FROM User u where typeTakeCare = ?1 and is_active = 1 and role_id = 4 and result = 'F0' ORDER BY id")
    List<User> findAllPatientForDoctor(String doctor_id,Pageable pageable);
    @Query(value = "SELECT u FROM User u where typeTakeCare = ?1 and is_active = 1 and role_id = 4 and result = 'F0' ORDER BY id")
    List<User> findAllPatientForDoctor(String doctor_id);

    //getdoctorbyvillaId
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 3 ORDER BY id")
    List<User> findAllDoctorbyvillaId(Long village,Pageable pageable);
    @Query(value = "SELECT u FROM User u where village_id = ?1 and is_active = 1 and role_id = 3 ORDER BY id")
    List<User> findAllDoctorbyvillaId(Long village);

    List<User> findAllUserByRoleId(long roleId);

    @Query(value = "SELECT u FROM User u where role_id = ?1 and is_active = 1 and fullname like %?2% ORDER BY id")
    List<User> findAllUserByRoleId( Long roleId,String text,Pageable pageable);

    @Query(value = "SELECT u FROM User u where role_id = ?1 and is_active = 1 and fullname like %?2% ORDER BY id")
    List<User> findAllUserByRoleId( Long roleId,String text);

    List<User> findByRole(Role role);

    List<User> findByUsernameContaining(String text, Pageable pageable);

    List<User> findByUsernameContaining(String text);

    User findByUsername(String username);

    @Query(value = "SELECT u FROM User u ORDER BY id")
    List<User> findAllUserWithPagination(Pageable pageable);

    User findByPhone(String username);

    List<User> findByFullnameContaining(String text, Pageable pageable);

    List<User> findByFullnameContaining(String text);

    @Query( value = "SELECT * FROM user as u WHERE u.id   NOT IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4", nativeQuery = true)
    List<User> notSentReport(String time);

    @Query( value = "SELECT * FROM user as u WHERE u.id    IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4", nativeQuery = true)
    List<User> sentReport(String time);

    @Query(value = "SELECT count(*) FROM fcdc.user where village_id = ?1 and is_active = 1 and result like 'F0' and role_id =4",nativeQuery = true)
    int totalCurrentF0(Long villageId);
}
