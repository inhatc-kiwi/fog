package com.fog.fog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fog.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FogController {

//	// 포그 메인페이지
//	@GetMapping("/fog")
//	public String fogMain(Model model) {
//		return "/fog/fogMain";
//	}

	// 포그 메인페이지
	@GetMapping("/fog/{fogid}")
	public String fogMain(@PathVariable("fogid") String fogid, Model model) {
		model.addAttribute("fogid", fogid);
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
