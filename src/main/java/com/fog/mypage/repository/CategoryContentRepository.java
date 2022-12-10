package com.fog.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fog.mypage.entity.CategoryContent;

public interface CategoryContentRepository extends JpaRepository<CategoryContent, Long> {
	
}
