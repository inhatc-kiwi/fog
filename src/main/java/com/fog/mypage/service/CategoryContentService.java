package com.fog.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fog.config.auth.PrincipalDetails;
import com.fog.member.entity.Member;
import com.fog.member.repository.MemberRepository;
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
	
}
