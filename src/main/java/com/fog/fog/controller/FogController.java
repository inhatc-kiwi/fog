package com.fog.fog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class FogController {
	
	// 포그메인페이지
	@GetMapping("/fog")
	public String fogMain(Model model) {
		return "/fog/fogMain";
	}
	
}
