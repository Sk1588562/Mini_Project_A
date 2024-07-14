package com.samproj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samproj.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
