package com.toy.kh.ToDoList.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.ToDoList.dao.UsrDao;
import com.toy.kh.ToDoList.dto.ToDoList;

@Service
public class UsrService {
	@Autowired
	private UsrDao usrDao;
	
	public void addDoList(@RequestParam Map<String, Object> param) {
		usrDao.addDoList(param);
	}

	public List<ToDoList> getListByDate(String startDay, String endDay) {
		return usrDao.getListByDate(startDay, endDay);
	}

	public void deleteDo(int id) {
		usrDao.deleteDo(id);
	}
	
}
