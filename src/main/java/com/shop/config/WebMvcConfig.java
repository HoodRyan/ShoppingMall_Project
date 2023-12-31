package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}") // properties 에 설정된 uploadPath 값을 읽어 옴
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")   // 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 폴더를 기준으로 파일을 읽어 옴
                .addResourceLocations(uploadPath);  // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정
    }
}
