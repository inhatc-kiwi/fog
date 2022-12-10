package com.fog.hitCount.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fog.hitCount.entity.HitCount;
import com.fog.hitCount.repository.HitCountRepository;
import com.fog.member.entity.Member;
import com.fog.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class HitCountService {
	private final HitCountRepository hitCountRepository;
	private final MemberRepository memberRepository;
	private HttpServletRequest request;
	
	// 조회수 증가
		@Transactional
		public void hitCountSave(String fogId, String date, HitCount hitcount) { // Member member
			// 현재 날짜 구하기
	        LocalDate now = LocalDate.now();
	 
	        // 포맷 정의
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
	 
	        // 포맷 적용
	        String formatedNow = now.format(formatter);
	 
	        // 결과 출력
	        System.out.println(">>>>>>>>>>>> 오늘 날짜 : " + formatedNow);  // 22-12-09
	        
			hitcount.setDate(date); 
			hitcount.setFogId(fogId); // 내가 본 사용자의 아이디를 넣어야함
			hitcount.save(fogId, formatedNow); // , member
			hitCountRepository.save(hitcount);
		}

		// 방문자 수 리스트 조회
		public List<HitCount> countList() {
			List<HitCount> countList = hitCountRepository.findAllByOrderByIdDesc();
			return countList;
		}
}
