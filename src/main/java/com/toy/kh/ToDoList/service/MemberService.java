package com.toy.kh.ToDoList.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.kh.ToDoList.dao.MemberDao;
import com.toy.kh.ToDoList.dto.Member;
import com.toy.kh.ToDoList.dto.ResultData;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
	
	public ResultData join(Map<String, Object> param) {
		memberDao.join(param);

		return new ResultData("S-1", String.format("%s님 환영합니다.", param.get("nickname")));
	}
	
	public Member getMember(int id) {
		return memberDao.getMember(id);
	}
}
