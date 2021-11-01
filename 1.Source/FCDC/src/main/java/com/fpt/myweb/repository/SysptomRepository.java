package com.fpt.myweb.repository;

import com.fpt.myweb.entity.Sysptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysptomRepository extends JpaRepository<Sysptom,Long> {

}
