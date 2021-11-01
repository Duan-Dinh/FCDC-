package com.fpt.myweb.repository;

import com.fpt.myweb.entity.District;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village,Long> {

    List<Village> findTop20ByNameContainingAndDistrictOrderById(String name, District district);

    Village findByName(String name);
}
