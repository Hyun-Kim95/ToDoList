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

	List<ToDoList> getListByDate(@Param("doDate") String doDate);

	void deleteDo(@Param("id") int id);

	List<ToDoList> getListByZero(@Param("doDate") String doDate);

	List<CycleList> getCycles();

	int getCountCycles();

	void addDoCycle(Map<String, Object> param);

	void addNumber(@Param("id") int id);

	void deleteCycle(@Param("id") int id);

	CycleList getCycle(@Param("id") int id);

	void subNumber(@Param("id") int id);

	List<ToDoList> getFailes(@Param("start") String start, @Param("now") String now);

	List<ToDoList> getCountByClassificationAndPastday(@Param("classification") String classification,
			@Param("start") String start, @Param("now") String now);

	int getCountAllByPastday(@Param("start") String start, @Param("now") String now);

	void addReason(Map<String, Object> param);

	void doInvisible(@Param("id")int id);

	void doSuccess(@Param("id")int id);

	void UnSuccess();

	void UnSuccessByCycle(@Param("id")int id);
	
	void doSuccessByCycle(@Param("id")int id);

}
