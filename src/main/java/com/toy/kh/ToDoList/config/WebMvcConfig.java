package com.toy.kh.ToDoList.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// CORS 허용 : 다른 출처의 자원을 공류할 수 있도록 설정하는 권한 체제
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("beforeActionInterceptor")
	HandlerInterceptor beforeActionInterceptor;

	// needLoginInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("needLoginInterceptor")
	HandlerInterceptor needLoginInterceptor;

	// needLogoutInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("needLogoutInterceptor")
	HandlerInterceptor needLogoutInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**");

		// 로그인 필요한 경우를 전체로 설정 후 필요 없는 경우 설정
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/")
				.excludePathPatterns("/resource/**").excludePathPatterns("/usr/member/login")
				.excludePathPatterns("/usr/member/doLogin").excludePathPatterns("/usr/member/join")
				.excludePathPatterns("/usr/member/doJoin").excludePathPatterns("/common/**")
				.excludePathPatterns("/test/**").excludePathPatterns("/error")
				.excludePathPatterns("/usr/member/getLoginIdDup");

		// 로그인 상태에서 접속할 수 없는 URI 전부 기술
		registry.addInterceptor(needLogoutInterceptor).addPathPatterns("/usr/member/login")
				.addPathPatterns("/usr/member/doLogin").addPathPatterns("/usr/member/join")
				.addPathPatterns("/usr/member/doJoin");
	}
}