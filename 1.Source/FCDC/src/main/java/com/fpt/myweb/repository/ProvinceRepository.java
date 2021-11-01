package com.fpt.myweb.repository;

import com.fpt.myweb.entity.District;
import com.fpt.myweb.entity.Province;
import com.fpt.myweb.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long> {

    List<Province> findTop20ByNameContainingOrderById(String name);
}
