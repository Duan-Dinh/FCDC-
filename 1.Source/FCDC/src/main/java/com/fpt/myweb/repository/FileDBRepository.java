package com.fpt.myweb.repository;

import com.fpt.myweb.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
// FileDB findByName(String name);
}
