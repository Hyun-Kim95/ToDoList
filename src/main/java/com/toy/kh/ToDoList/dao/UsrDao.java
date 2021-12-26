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

	List<ToDoList> getListByDate(@Param("doDate") String doDate,@Param("user") String user);

	void deleteDo(@Param("id") int id,@Param("user") String user);

	List<ToDoList> getListByZero(@Param("doDate") String doDate,@Param("user") String user);

	List<CycleList> getCycles(@Param("user")String user);

	int getCountCycles(@Param("user")String user);

	void addDoCycle(Map<String, Object> param);

	void addNumber(@Param("id") int id,@Param("user") String user);

	void deleteCycle(@Param("id") int id,@Param("user") String user);

	CycleList getCycle(@Param("id") int id,@Param("user") String user);

	void subNumber(@Param("id") int id,@Param("user") String user);

	List<ToDoList> getFailes(@Param("start") String start, @Param("now") String now,@Param("user") String user);

	List<ToDoList> getCountByClassificationAndPastday(@Param("classification") String classification,
			@Param("start") String start, @Param("now") String now,@Param("user") String user);

	int getCountAllByPastday(@Param("start") String start, @Param("now") String now,@Param("user") String user);

	void addReason(Map<String, Object> param);

	void doInvisible(@Param("id")int id, @Param("user")String user);

	void doSuccess(@Param("id")int id,@Param("user") String user);

	void UnSuccess();

	void UnSuccessByCycle(@Param("id")int id, @Param("user")String user);
	
	void doSuccessByCycle(@Param("id")int id, @Param("user")String user);

}
