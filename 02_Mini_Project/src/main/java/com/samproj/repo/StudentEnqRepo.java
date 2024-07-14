package com.samproj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samproj.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
