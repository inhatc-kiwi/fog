package com.fog.fog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class FogController {
	
	// 마이페이지 - 카테고리 관리
	@GetMapping("/fog")
	public String fogMain(Model model) {
		return "/fog/fogMain";
	}
	
}
