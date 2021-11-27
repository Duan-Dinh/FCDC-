package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Daily_Report;
import com.fpt.myweb.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Daily_ReportRepository extends JpaRepository<Daily_Report,Long> {
    List<Daily_Report> findByUserId(Long id);

    //get village
    @Query(value = "SELECT d FROM Daily_Report d where user_id = ?1 ORDER BY id")
    List<Daily_Report> findAllByUserId(Long id,Pageable pageable);
    @Query(value = "SELECT d FROM Daily_Report d where user_id = ?1 ORDER BY id")
    List<Daily_Report> findAllByUserId(Long id);
//AllSentReportOnedate
    @Query(value = "SELECT d FROM Daily_Report d where dateTime like %?1% ORDER BY id")
    List<Daily_Report> findSentReportOnedate(String time,Pageable pageable);

 @Query( value = "SELECT d.* FROM fcdc.daily_report d,user u where u.id = d.user_id and d.date_time like ?1 and u.is_active = 1 and u.village_id = ?2 and u.fullname like %?3%", nativeQuery = true)
    List<Daily_Report> findBySentReport(String time,Long villaId,String key,Pageable pageable);
    @Query( value = "SELECT d.* FROM fcdc.daily_report d,user u where u.id = d.user_id and d.date_time like ?1 and u.is_active = 1 and u.village_id = ?2 and u.fullname like %?3%", nativeQuery = true)
    List<Daily_Report> findBySentReport(String time,Long villaId,String key);
}
