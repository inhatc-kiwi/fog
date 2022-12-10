package com.fog.mypage.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fog.config.auth.PrincipalDetails;
import com.fog.hitCount.entity.HitCount;
import com.fog.hitCount.service.HitCountService;
import com.fog.member.entity.Member;
import com.fog.mypage.constant.PrivateYn;
import com.fog.mypage.dto.CategoryWriteDto;
import com.fog.mypage.entity.CategoryContent;
import com.fog.mypage.service.CategoryContentService;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {

	@Autowired
	private CategoryContentService categoryContentService;

	@Autowired
	private HitCountService countService;

	@Value("${contentImgLocation}")
	private String contentImgLocation;

	// 마이페이지 - 메인 (방문자 통계)
	@GetMapping("/main")
	public String mypageMain(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 방문자 통계
		String fogId = principalDetails.getMember().getFogid();
		List<HitCount> lists = countService.countList();
		model.addAttribute("lists", lists);
		model.addAttribute("fogId", fogId);
//		System.out.println(">>>>>>>>>>>>>>>> list.fogId : " + lists.get(0).getFogId());

		String memFogId = principalDetails.getMember().getFogid();
//		System.out.println(">>>>>>>>>>>>>>> : memFogId : " + memFogId);
		model.addAttribute("memFogId", memFogId);

		int counts = 0;
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getFogId().equals(memFogId)) { 
				// 방문자 목록 중 내 포그 아이디 검색
				counts += 1;
			}
		}
		model.addAttribute("total", counts);
		return "/mypage/mypageMain";
	}

	// 마이페이지 - 카테고리 관리
	@GetMapping("/category")
	public String mypageCategory(Model model) {
		return "/mypage/mypageCategory";
	}

	// 마이페이지 - 포그 관리
	@GetMapping("/fogEdit")
	public String mypageFogEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogId", fogId);
		return "/mypage/mypageFogEdit";
	}

	// 마이페이지 - 설정
	@GetMapping("/setting")
	public String mypageSetting(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogId", fogId);
		return "/mypage/mypageSetting";
	}

	// 마이페이지 - 작성하기
	@GetMapping("/write")
	public String mypageWrite(Model model) {
		model.addAttribute("categoryWriteDto", new CategoryWriteDto());
		model.addAttribute("private", PrivateYn.values());
		return "/mypage/mypageWrite";
	}

	// 마이페이지 - 작성하기
	@PostMapping("/write")
	public String mypageWritePost(@AuthenticationPrincipal PrincipalDetails principalDetails,
			CategoryWriteDto categoryWriteDto, Model model,
			@RequestParam(value = "radio", required = false) String radio) {
		categoryWriteDto.setCategoryYn(radio);
		String category_id = categoryWriteDto.getCategory();

		CategoryContent content = CategoryContent.createContent(categoryWriteDto);
		categoryContentService.saveContent(principalDetails, content, category_id);

		return "redirect:/mypage/main";
	}

	// ckeditor 이미지 처리
	@PostMapping(value = "/image/upload")
	public ModelAndView image(MultipartHttpServletRequest request) throws Exception {

		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로 등록해야
		// 함
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서
		// uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");

		// 파일의 오리지널 네임
		String originalFileName = uploadFile.getOriginalFilename();

		// 파일의 확장자
		String ext = originalFileName.substring(originalFileName.indexOf("."));

		// 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
		String newFileName = UUID.randomUUID() + ext;

		// 현재경로/upload/파일명이 저장 경로
		String savePath = contentImgLocation + newFileName;

		// 브라우저에서 이미지 불러올 때 절대 경로로 불러오면 보안의 위험 있어 상대경로를 쓰거나 이미지 불러오는 jsp 또는 클래스 파일을 만들어
		// 가져오는 식으로 우회해야 함
		// 때문에 savePath와 별개로 상대 경로인 uploadPath 만들어줌
		String uploadPath = "/mypage/image/upload/" + newFileName;

		// 저장 경로로 파일 객체 생성
		File file = new File(savePath);

		// 파일 업로드
		uploadFile.transferTo(file);

		// uploaded, url 값을 modelandview를 통해 보냄
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", uploadPath); // 업로드 파일의 경로

		return mav;
	}

}
