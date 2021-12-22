package com.toy.kh.ToDoList.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.ToDoList.dto.CycleList;
import com.toy.kh.ToDoList.dto.ToDoList;

@Mapper
public interface UsrDao {

	void addDoList(@RequestParam Map<String, Object> param);

	List<ToDoList> getListByDate(@Param("startDay") String startDay,@Param("endDay") String endDay);

	void deleteDo(@Param("id")int id);

	List<ToDoList> getListByZero(@Param("doDate")String doDate);

	List<CycleList> getCycles();

	int getCountCycles();

	void addDoCycle(Map<String, Object> param);

	void addNumber(@Param("id")int id);

	void deleteCycle(@Param("id")int id);

	CycleList getCycle(@Param("id")int id);

	void subNumber(@Param("id")int id);

}
