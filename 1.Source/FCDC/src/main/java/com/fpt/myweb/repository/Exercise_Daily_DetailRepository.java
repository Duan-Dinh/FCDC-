package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Exercise_Daily_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Exercise_Daily_DetailRepository extends JpaRepository<Exercise_Daily_Detail,Long> {
}
