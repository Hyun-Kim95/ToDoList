package com.toy.kh.ToDoList.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.ToDoList.dao.UsrDao;
import com.toy.kh.ToDoList.dto.CycleList;
import com.toy.kh.ToDoList.dto.ToDoList;
import com.toy.kh.ToDoList.util.Util;

@Service
public class UsrService {
	@Autowired
	private UsrDao usrDao;
	
	public void addDoList(@RequestParam Map<String, Object> param) {
		usrDao.addDoList(param);
	}

	public List<ToDoList> getListByDate(String doDate, String user) {
		return usrDao.getListByDate(doDate, user);
	}

	public void deleteDo(int id, String user) {
		usrDao.deleteDo(id, user);
	}

	public List<ToDoList> getListByZero(String check, String user) {
		return usrDao.getListByZero(check, user);
	}

	public List<CycleList> getCycles(String user) {
		return usrDao.getCycles(user);
	}

	public int getCountCycles(String user) {
		return usrDao.getCountCycles(user);
	}

	public void addDoCycle(Map<String, Object> param) {
		usrDao.addDoCycle(param);
	}

	public void addNumber(int id, String user) {
		usrDao.addNumber(id, user);
		
	}

	public void deleteCycle(int id, String user) {
		usrDao.deleteCycle(id, user);
	}

	public CycleList getCycle(int id, String user) {
		return usrDao.getCycle(id, user);
	}

	public void subNumber(int id, String user) {
		usrDao.subNumber(id, user);
	}

	public List<ToDoList> getFailes(String start, String now, String user) {
		return usrDao.getFailes(start, now, user);
	}

	public List<ToDoList> getCountByClassificationAndPastday(String classification, String start, String now, String user) {
		return usrDao.getCountByClassificationAndPastday(classification, start, now, user);
	}

	public int getCountAllByPastday(String start, String now, String user) {
		return usrDao.getCountAllByPastday(start, now, user);
	}

	public void addReason(Map<String, Object> param) {
		usrDao.addReason(param);		
	}

	public void doInvisible(int id, String user) {
		usrDao.doInvisible(id, user);
	}

	public int getCountByFailes(String start, String now, String user) {
		return getFailes(start, now, user).size();
	}

	public void doSuccess(int id, String user) {
		usrDao.doSuccess(id, user);
	}

	public void doSuccessByCycle(int id, String user) {
		// 사이클의 마지막 순서를 성공하면 다시 처음으로 돌아가기 위한 코드
		if(usrDao.getCycles(user).size() == usrDao.getCycle(id, user).getNumber()) {
			usrDao.UnSuccessByCycle(id, user);
		}else {
			usrDao.UnSuccessByCycle(id, user);
			usrDao.doSuccessByCycle(id, user);			
		}
	}

	public List<ToDoList> getListByMonth(String month, String user) {
		// 해당 월의 마지막 일자를 구함
		int lastDay = Util.lastday(Integer.parseInt(month.split("-")[0]), Integer.parseInt(month.split("-")[1]));
		// 해당 월의 할일들을 리턴
		return usrDao.getListByMonth(month+"-01", month+"-"+lastDay, user);
	}
	
}
