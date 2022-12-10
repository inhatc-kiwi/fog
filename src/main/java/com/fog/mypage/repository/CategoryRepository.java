package com.fog.mypage.repository;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fog.mypage.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	Category findCategoryById(Long id);

}
