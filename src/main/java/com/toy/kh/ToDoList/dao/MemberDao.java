package com.toy.kh.ToDoList.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.toy.kh.ToDoList.dto.Member;

@Mapper
public interface MemberDao {

	Member getMemberByLoginId(@Param("loginId")String loginId);

	void join(Map<String, Object> param);

	Member getMember(@Param("id")int id);

}
