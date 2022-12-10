package com.fog.fog.controller;

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

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CategoryController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	private final HitCountService countService;

	// 포그 메인페이지
	@GetMapping("/fog/{fogid}")
	public String fogMain(@PathVariable("fogid") String fogid,Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, HitCount hitcount) {
		model.addAttribute("fogid", fogid);
		System.out.println(">>>>>>>>>>>> fogId : " + fogid);
		
		// 포그 이름
		String name = principalDetails.getMember().getName();
		model.addAttribute("name", name);
		
		// 조회수 증가
		countService.hitCountSave(fogid, "날짜", hitcount);
		System.out.println("조회수 증가");
		
//		Long memId = principalDetails.getMember().getId();
//		Optional<Member> dto = memberRepository.findById(memId);
//		memberService.updateView(memId); // views ++
//        model.addAttribute("view", dto); // 조회수
//        System.out.println(">>>>>>>>>> 조회수 : " + dto);
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
