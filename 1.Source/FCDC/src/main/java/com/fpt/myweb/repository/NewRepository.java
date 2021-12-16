package com.fpt.myweb.repository;

import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.entity.District;
import com.fpt.myweb.entity.New;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewRepository extends JpaRepository<New,Long> {

    List<New> findTop20ByTitleContainingOrderById(String title);

    @Query(value = "SELECT n FROM New n ORDER BY id desc ")
    List<New> findAllNewsWithPagination(Pageable pageable);
    @Query(value = "SELECT n FROM New n ORDER BY id desc ")
    List<New> findAllNewsWithPagination();

    @Query(value = "SELECT n FROM New n where title like %?1% ORDER BY id DESC ")
    List<New> findAllNewsByTitle(String text,Pageable pageable);
    @Query(value = "SELECT n FROM New n where title like %?1% ORDER BY id DESC ")
    List<New> findAllNewsByTitle(String text);

    List<New> findByTitleContaining(String title);



}
