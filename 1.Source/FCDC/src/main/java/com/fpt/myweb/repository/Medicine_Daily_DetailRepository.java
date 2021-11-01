package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Medicine_Daily_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Medicine_Daily_DetailRepository extends JpaRepository<Medicine_Daily_Detail,Long> {
}
