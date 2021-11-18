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

    @Query(value = "SELECT u FROM New u ORDER BY id")
    List<New> findAllNewsWithPagination(Pageable pageable);
    List<New> findByTitleContaining(String title);


}
