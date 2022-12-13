package com.fog.fog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fog.config.auth.PrincipalDetails;
import com.fog.hitCount.entity.HitCount;
import com.fog.hitCount.service.HitCountService;
import com.fog.member.dto.MemberFormDto;
import com.fog.member.entity.Member;
import com.fog.member.repository.MemberRepository;
import com.fog.member.service.MemberService;
import com.fog.mypage.entity.Category;
import com.fog.mypage.entity.CategoryContent;
import com.fog.mypage.repository.CategoryContentRepository;
import com.fog.mypage.repository.CategoryRepository;
import com.fog.mypage.service.CategoryContentService;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CategoryController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CategoryContentService categoryContentService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryContentRepository categoryContentRepository;

	private final HitCountService countService;

	// 포그 메인페이지
	@GetMapping("/fog/{fogid}")
	public String fogMain(@PathVariable("fogid") String fogid, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails, HitCount hitcount) {
		model.addAttribute("fogid", fogid);
		System.out.println(">>>>>>>>>>>> fogId : " + fogid);

		// 현재 로그인한 정보
		Long memid = principalDetails.getMember().getId();
		Member memMember = memberRepository.findMemberById(memid);
		model.addAttribute("memMember", memMember);

		// 포그 계정에 대한 정보
		List<Member> list = memberRepository.findAll();
		Long id = (long) 0; // 포그의 공개여부
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFogid().equals(fogid)) {
				id = list.get(i).getId();
			}
		}

		Member member = memberRepository.findMemberById(id);
		model.addAttribute("member", member);

		System.out.println(">>>>>>>>>>>> 현재로그인한 fog ID : " + memMember.getFogid());
		System.out.println(">>>>>>>>>>>> 계정 fog ID : " + fogid);

		System.out.println(">>>>>>>>>>>> 공개여부 : " + member.getAllPublicYn());

		// 조회수 증가
		countService.hitCountSave(fogid, "날짜", hitcount);
		System.out.println("조회수 증가");

		// 카테고리 출력
		List<Category> lists = categoryRepository.findAll();
		List<String> categorys = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<Long> categorysIds = new ArrayList<>(); // 로그인한 사용자의 카테고리 ID 저장 리스트
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMember().getId().equals(principalDetails.getMember().getId())) {
				String categoryName = lists.get(i).getType();				
				categorys.add(categoryName); // 리스트에 카테고리 이름 저장
				
				Long categorysId = lists.get(i).getId();
				categorysIds.add(categorysId); // 리스트에 카테고리 이름 저장
			}
		}
		model.addAttribute("categorys1", categorys.get(0));
		model.addAttribute("categorys2", categorys.get(1));
		model.addAttribute("categorys3", categorys.get(2));
		model.addAttribute("categorys4", categorys.get(3));
		model.addAttribute("categorys5", categorys.get(4));
		
		Long categorysIds1 = categorysIds.get(0);
		Long categorysIds2 = categorysIds.get(1);
		Long categorysIds3 = categorysIds.get(2);
		Long categorysIds4 = categorysIds.get(3);
		Long categorysIds5 = categorysIds.get(4);

		// 내용 출력
		List<CategoryContent> listscon = categoryContentRepository.findAll();
		List<String> titleList = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		
		List<String> conList1 = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<String> conList2 = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<String> conList3 = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<String> conList4 = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<String> conList5 = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언

		for (int i = 0; i < listscon.size(); i++) {
			if (listscon.get(i).getMember().getId().equals(principalDetails.getMember().getId())) {
				if(listscon.get(i).getCategory().getId().equals(categorysIds1)) {
					String conttitle1 = listscon.get(i).getTitle();
					conList1.add(conttitle1); // 리스트에 카테고리 이름 저장
				} else if(listscon.get(i).getCategory().getId().equals(categorysIds2)) {
					String conttitle2 = listscon.get(i).getTitle();
					conList2.add(conttitle2); // 리스트에 카테고리 이름 저장
				} else if(listscon.get(i).getCategory().getId().equals(categorysIds3)) {
					String conttitle3 = listscon.get(i).getTitle();
					conList3.add(conttitle3); // 리스트에 카테고리 이름 저장
				} else if(listscon.get(i).getCategory().getId().equals(categorysIds4)) {
					String conttitle4 = listscon.get(i).getTitle();
					conList4.add(conttitle4); // 리스트에 카테고리 이름 저장
				} else if(listscon.get(i).getCategory().getId().equals(categorysIds5)) {
					String conttitle5 = listscon.get(i).getTitle();
					conList5.add(conttitle5); // 리스트에 카테고리 이름 저장
				}
				String conttitle = listscon.get(i).getTitle();
				titleList.add(conttitle); // 리스트에 카테고리 이름 저장
			}
		}
		model.addAttribute("titleList", titleList);
		model.addAttribute("conList1", conList1);
		model.addAttribute("conList2", conList2);
		model.addAttribute("conList3", conList3);
		model.addAttribute("conList4", conList4);
		model.addAttribute("conList5", conList5);

		return "/fog/fogMain";
	}

	// 마이페이지 - 헤더
	@GetMapping("/fog")
	public String mypageHeader(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogid", fogId);
		System.out.println(">>>>>>>>>>>>>> fogId" + fogId);
		return "/fragments/mypageHeader";
	}
}
