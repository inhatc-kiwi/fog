package com.fog.mypage.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fog.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {

	// 마이페이지 - 메인
	@GetMapping("/main")
	public String mypageMain(Model model) {
		return "/mypage/mypageMain";
	}

	// 마이페이지 - 카테고리 관리
	@GetMapping("/category")
	public String mypageCategory(Model model) {
		return "/mypage/mypageCategory";
	}

	// 마이페이지 - 포그 관리
	@GetMapping("/fogEdit")
	public String mypageFogEdit(Model model) {
		return "/mypage/mypageFogEdit";
	}

	// 마이페이지 - 설정
	@GetMapping("/setting")
	public String mypageSetting(Model model) {
		return "/mypage/mypageSetting";
	}

	// 마이페이지 - 작성하기
	@GetMapping("/write")
	public String mypageWrite(Model model) {
		return "/mypage/mypageWrite";
	}
	
	// 마이페이지 - 작성하기
	@PostMapping("/write")
	public String mypageWritePost(Model model) {
		
		return "redirect:/mypage/mypageMain";
	}

}
