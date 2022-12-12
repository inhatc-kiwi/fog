package com.fog.mypage.controller;

import java.io.File;

import java.util.ArrayList;

import java.io.IOException;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fog.config.auth.PrincipalDetails;
import com.fog.hitCount.entity.HitCount;
import com.fog.hitCount.service.HitCountService;
import com.fog.member.constant.Area;
import com.fog.member.entity.Member;
import com.fog.member.repository.MemberRepository;
import com.fog.member.service.MemberService;
import com.fog.mypage.constant.PrivateYn;
import com.fog.mypage.dto.CategoryUpdateDto;
import com.fog.mypage.dto.CategoryWriteDto;
import com.fog.mypage.dto.MemberUpdateDto;
import com.fog.mypage.entity.Category;
import com.fog.mypage.entity.CategoryContent;
import com.fog.mypage.repository.CategoryRepository;
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
	private CategoryRepository categoryRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private HitCountService countService;
	
	@Autowired
	private MemberService memberService;

	@Value("${contentImgLocation}")
	private String contentImgLocation;
	
	@Value("${profileImgLocation}")
	private String profileImgLocation;

	// 마이페이지 - 메인 (방문자 통계)
	@GetMapping("/main")
	public String mypageMain(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 방문자 통계
		String fogId = principalDetails.getMember().getFogid();
		List<HitCount> lists = countService.countList();
		model.addAttribute("lists", lists);
		model.addAttribute("fogId", fogId);

		String memFogId = principalDetails.getMember().getFogid();
		model.addAttribute("memFogId", memFogId);

		String name = principalDetails.getMember().getName();
		model.addAttribute("name", name);

		int counts = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getFogId().equals(memFogId)) {
				// 방문자 목록 중 내 포그 아이디 검색
				counts += 1;
			}
		}
		model.addAttribute("total", counts);
		return "/mypage/mypageMain";
	}

	// 마이페이지 - 카테고리 관리
	@GetMapping("/category")
	public String mypageCategory(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 포그 이름
		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogId", fogId);

		// 로그인한 사용자 ID
		Long memId = principalDetails.getMember().getId();
		model.addAttribute("memId", memId);

		// 사용자 이름
		String name = principalDetails.getMember().getName();
		model.addAttribute("name", name);

		List<Category> lists = categoryRepository.findAll();
		model.addAttribute("lists", lists);

		return "/mypage/mypageCategory";
	}

	// 마이페이지 - 카테고리 수정
	@GetMapping("/category/update")
	public String mypageCategoryUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 포그 이름
		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogId", fogId);

		// 로그인한 사용자 ID
		Long memId = principalDetails.getMember().getId();
		model.addAttribute("memId", memId);

		List<Category> lists = categoryRepository.findAll();
		List<String> categorys = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언

		List<Long> categorysId = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언

		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMember().getId().equals(memId)) {
				String categoryName = lists.get(i).getType();

				categorys.add(categoryName); // 리스트에 카테고리 이름 저장

				Long categoryId = lists.get(i).getId();
				categorysId.add(categoryId); // 리스트에 카테고리 이름 저장
			}
		}
		model.addAttribute("categorys", categorys);
		model.addAttribute("categorysId", categorysId);

//		List<Category> lists = categoryRepository.findAll();
//		model.addAttribute("lists", lists);

//		for (Long i = Long.valueOf(1); i < 6; i++) {
//			Category category = categoryRepository.findCategoryById(i);
//			model.addAttribute("category"+i, category);
//		}

		model.addAttribute("categoryUpdateDto", new CategoryUpdateDto());
		return "/mypage/mypageCategoryUpdate";
	}

	// 마이페이지 - 카테고리 수정
	@PostMapping("/category/update")
	public String mypageCategoryPost(CategoryUpdateDto categoryUpdateDto, Model model) {
//		System.out.println("-=============== 카테고리 관리 POST : " + categoryUpdateDto);
//		System.out.println("-=============== 카테고리 Id : " + categoryUpdateDto.getId());
//		System.out.println("-=============== 카테고리 Type : " + categoryUpdateDto.getType());

		Long id = categoryUpdateDto.getId();
		String type = categoryUpdateDto.getType();
		categoryContentService.updateCtegory(id, type);

		return "redirect:/mypage/category";
	}

	// 마이페이지 - 포그 관리
	@GetMapping("/fogEdit")
	public String mypageFogEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		String fogId = principalDetails.getMember().getFogid();
		model.addAttribute("fogId", fogId);

		String name = principalDetails.getMember().getName();
		model.addAttribute("name", name);

		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		model.addAttribute("member", member);

		return "/mypage/mypageFogEdit";
	}

	// 마이페이지 - 설정
	@GetMapping("/setting")
	public String mypageSetting(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		model.addAttribute("member", member);
		model.addAttribute("local", Area.values());
		model.addAttribute("memberUpdateDto", new MemberUpdateDto());
		
		return "/mypage/mypageSetting";
	}
	
	// 마이페이지 - 설정
	@PostMapping("/setting/insert_image")
	public String image_insert(@AuthenticationPrincipal PrincipalDetails principalDetails,MemberUpdateDto memberUpdateDto, HttpServletRequest request, Model model
			,@RequestParam(value = "radio", required = false) String radio) throws Exception {
		
//		System.out.println("===============>name : " + memberUpdateDto.getName());
//		System.out.println("===============>getAllPublicYn : " + memberUpdateDto.getAllPublicYn());
//		System.out.println("===============>Area : " + memberUpdateDto.getArea());
//		System.out.println("===============>Filename : " + memberUpdateDto.getFilename());
		
		
		memberUpdateDto.setAllPublicYn(radio);
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		
		// 파일의 오리지널 네임
		//String originalFileName = mFile.getOriginalFilename();
		//String ext = originalFileName.substring(originalFileName.indexOf("."));
		String originalFileName = memberUpdateDto.getFilename().getOriginalFilename();
		
		
		// 이미지 수정 안 하는 경우
		if(originalFileName.equals("")) {
			memberService.updateProfile(member, memberUpdateDto);
		} 
		// 이미지 수정 하는 경우
		else {
			String ext = originalFileName.substring(originalFileName.indexOf("."));
			String newFileName = UUID.randomUUID() + ext;
			String uploadPath = "/mypage/setting/" + newFileName;

			try {
				if (member.getImage() != null) { // 이미 프로필 사진이 있을경우
					File file = new File(profileImgLocation + member.getImage()); // 경로 + 유저 프로필사진 이름을 가져와서
					file.delete(); // 원래파일 삭제
				}
				//mFile.transferTo(new File(profileImgLocation + newFileName)); // 경로에 업로드
				memberUpdateDto.getFilename().transferTo(new File(profileImgLocation + newFileName)); // 경로에 업로드
			} catch (IOException e) {
				e.printStackTrace();
			}
			memberService.updateImage(member, uploadPath,memberUpdateDto);
		}
		
		
		return "redirect:/mypage/setting";
	}
	
	

	// 마이페이지 - 작성하기
	@GetMapping("/write")
	public String mypageWrite(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		//String fogId = principalDetails.getMember().getFogid();
		//model.addAttribute("fogId", fogId);
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		model.addAttribute("member", member);
		
		
		model.addAttribute("categoryWriteDto", new CategoryWriteDto());
		model.addAttribute("private", PrivateYn.values());

		// 카테고리 출력
		List<Category> lists = categoryRepository.findAll();
		List<String> categorys = new ArrayList<>(); // 로그인한 사용자의 카테고리 이름을 저장할 리스트 선언
		List<Long> categorysIds = new ArrayList<>(); // 로그인한 사용자의 카테고리 ID 저장 리스트
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMember().getId().equals(principalDetails.getMember().getId())) {
				String categoryName = lists.get(i).getType();
				Long categorysId = lists.get(i).getId();
				categorys.add(categoryName); // 리스트에 카테고리 이름 저장
				categorysIds.add(categorysId); // 리스트에 카테고리 이름 저장
			}
		}
		model.addAttribute("categorys1", categorys.get(0));
		model.addAttribute("categorys2", categorys.get(1));
		model.addAttribute("categorys3", categorys.get(2));
		model.addAttribute("categorys4", categorys.get(3));
		model.addAttribute("categorys5", categorys.get(4));
		
		model.addAttribute("categorysID1", categorysIds.get(0));
		model.addAttribute("categorysID2", categorysIds.get(1));
		model.addAttribute("categorysID3", categorysIds.get(2));
		model.addAttribute("categorysID4", categorysIds.get(3));
		model.addAttribute("categorysID5", categorysIds.get(4));
		
		System.out.println(">>>>>>>>>> categorysID1 : " + categorysIds.get(0));
		System.out.println(">>>>>>>>>> categorysID2 : " + categorysIds.get(1));
		System.out.println(">>>>>>>>>> categorysID3 : " + categorysIds.get(2));
		System.out.println(">>>>>>>>>> categorysID4 : " + categorysIds.get(3));
		System.out.println(">>>>>>>>>> categorysID5 : " + categorysIds.get(4));
		return "/mypage/mypageWrite";
	}

	// 마이페이지 - 작성하기
	@PostMapping("/write")
	public String mypageWritePost(@AuthenticationPrincipal PrincipalDetails principalDetails, CategoryWriteDto categoryWriteDto, Model model,
			@RequestParam(value = "radio", required = false) String radio) {
		categoryWriteDto.setCategoryYn(radio);
		String category_id = categoryWriteDto.getCategory();
		System.out.println(">>>>>>>>>>>>>>> id" + category_id);
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
