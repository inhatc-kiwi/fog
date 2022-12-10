package com.fog.hitCount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fog.hitCount.entity.HitCount;

public interface HitCountRepository extends JpaRepository<HitCount,Long>{
	
	List<HitCount> findAllByOrderByIdDesc();
}
