package com.toy.kh.ToDoList.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.toy.kh.ToDoList.dto.Member;
import com.toy.kh.ToDoList.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
@Slf4j
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestUrl = request.getRequestURI();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			requestUrl += "?" + queryString;
		}

		String[] pathBits = request.getRequestURI().split("/");

		String controllerTypeCode = "usr";
		String controllerActName = "main";

		if (pathBits.length > 1) {
			controllerTypeCode = pathBits[1];
		}

		if (pathBits.length > 2) {
			controllerActName = pathBits[2];
		}

		request.setAttribute("controllerTypeCode", controllerTypeCode);
		request.setAttribute("controllerActName", controllerActName);

		log.debug("controllerTypeCode : " + controllerTypeCode);
		log.debug("controllerActName : " + controllerActName);

		request.setAttribute("requestUrl", requestUrl);

		int loginedMemberId = 0;
		Member loginedMember = null;

		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMember(loginedMemberId);
		}
		
		// 로그인 여부에 관련된 정보를 request에 담는다.
		boolean isLogined = false;

		if (loginedMember != null) {
			isLogined = true;
		}

		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMember", loginedMember);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}