package com.fog.mypage.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fog.config.auth.PrincipalDetails;
import com.fog.member.entity.Member;
import com.fog.member.repository.MemberRepository;
import com.fog.mypage.dto.CategoryUpdateDto;
import com.fog.mypage.entity.Category;
import com.fog.mypage.entity.CategoryContent;
import com.fog.mypage.repository.CategoryContentRepository;
import com.fog.mypage.repository.CategoryRepository;

@Service
@Transactional
public class CategoryContentService {
	
	@Autowired
	private CategoryContentRepository categoryContentRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	// 마이페이지 작성하기 
	public CategoryContent saveContent(@AuthenticationPrincipal PrincipalDetails principalDetails,CategoryContent categoryContent,String Category_id) {
		Long member_id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(member_id);
		
		Category category = categoryRepository.findCategoryById(Long.parseLong(Category_id));
		
		categoryContent.setMember(member);
		categoryContent.setCategory(category);
		
		return categoryContentRepository.save(categoryContent);
	}
	 
	public void allCatgory() {
		
		List<Category> categoryAll = categoryRepository.findAll();
	}
	
	// 카테고리 수정
//	public void updateContent(CategoryUpdateDto dto) {
//		 String[] id = dto.getId().split(",");
//		 String[] type = dto.getType().split(",");
//			
//		 for (int i = 0; i < id.length; i++) {
//			 long categoryId = Long.parseLong(id[i]);
//			 Category category = categoryRepository.findCategoryById(categoryId);
//			 category.setType(type[i]);
//		}
//	}
	
	public void updateCtegory(Long id, String type) {
		Category c = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		c.setType(type);
		categoryRepository.save(c);
	}
	
	// 기본 카테고리 생성
	public void createCategory(List<Category> categorys, Member member) {
		for (Category category : categorys) {
			category.setMember(member);
			categoryRepository.save(category);
		}
	}
	
}
