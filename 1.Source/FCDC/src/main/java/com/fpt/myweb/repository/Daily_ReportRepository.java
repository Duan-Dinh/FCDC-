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

// @Query( value = "SELECT * FROM user as u WHERE u.id   NOT IN (SELECT user_id FROM daily_report as d  WHERE date_time LIKE ?1) and u.role_id =4", nativeQuery = true)
    List<Daily_Report> findByDateTime(String time);

}
