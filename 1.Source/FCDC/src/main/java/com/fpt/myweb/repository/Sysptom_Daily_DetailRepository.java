package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Sysptom_Daily_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface Sysptom_Daily_DetailRepository extends JpaRepository<Sysptom_Daily_Detail,Long> {
    /*
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `fcdc2`.`sysptom_daily_detail` ( `daily_report_id`, `sysptom_id`) VALUES (?1, ?2);",nativeQuery = true)
    void addSysptom(Long daily_report_id,Long sysptom_id);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `fcdc_duannd`.`medicine_daily_detail` (`id`, `daily_report_id`, `medicine_id`) VALUES (?1, ?2, ?3);",nativeQuery = true)
    void addMedicine(Long id,Long daily_report_id,Long medicine_id);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `fcdc_duannd`.`exercise_daily_detail` (`id`, `daily_report_id`, `exercise_id`) VALUES (?1, ?2, ?3);",nativeQuery = true)
    void addExercise(Long id,Long daily_report_id,Long exercise_id);*/
}
