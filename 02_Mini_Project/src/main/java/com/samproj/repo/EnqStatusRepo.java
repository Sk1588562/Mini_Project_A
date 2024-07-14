package com.samproj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samproj.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>{

}
