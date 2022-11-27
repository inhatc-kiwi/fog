package com.fog.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fog.config.auth.PrincipalDetails;
import com.fog.member.constant.Area;
import com.fog.member.service.MemberService;
/**
 * 로그인 성공 핸들러 
 * @author Jongha
 *
 */
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
    private MemberService memberService;
	
	/**
	 * 로그인 성공 메서드
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			String user = principalDetails.getUsername();
			Area area = principalDetails.getMember().getArea();
			String email = principalDetails.getMember().getEmail();
			
			if(area != null) {
				response.sendRedirect("/fog");
				System.out.println("=========================1");
			} else {
				response.sendRedirect("/members/login/addInfo");
				System.out.println("=========================2");
			}
		
	}
 
}
