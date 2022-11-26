package com.fog.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {

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
	
	
	
}
