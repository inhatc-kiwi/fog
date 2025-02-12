package com.fog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정할 수 있도록 미리 메모리에 올려두기
public class WebMvcConfig implements WebMvcConfigurer {

	// properties에 설정한 uploadPath값을 읽어온다
	@Value("${contentuploadPath}")
	String contentuploadPath;
	
	@Value("${profileuploadPath}")
	String profileuploadPath;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/mypage/image/upload/**").addResourceLocations(contentuploadPath);
		registry.addResourceHandler("/mypage/setting/**").addResourceLocations(profileuploadPath);
	}

}
