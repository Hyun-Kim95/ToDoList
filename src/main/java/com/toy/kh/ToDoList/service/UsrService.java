package com.toy.kh.ToDoList.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.ToDoList.dao.UsrDao;
import com.toy.kh.ToDoList.dto.CycleList;
import com.toy.kh.ToDoList.dto.ToDoList;

@Service
public class UsrService {
	@Autowired
	private UsrDao usrDao;
	
	public void addDoList(@RequestParam Map<String, Object> param) {
		usrDao.addDoList(param);
	}

	public List<ToDoList> getListByDate(String doDate) {
		return usrDao.getListByDate(doDate);
	}

	public void deleteDo(int id) {
		usrDao.deleteDo(id);
	}

	public List<ToDoList> getListByZero(String check) {
		return usrDao.getListByZero(check);
	}

	public List<CycleList> getCycles() {
		return usrDao.getCycles();
	}

	public int getCountCycles() {
		return usrDao.getCountCycles();
	}

	public void addDoCycle(Map<String, Object> param) {
		usrDao.addDoCycle(param);
	}

	public void addNumber(int id) {
		usrDao.addNumber(id);
		
	}

	public void deleteCycle(int id) {
		usrDao.deleteCycle(id);
	}

	public CycleList getCycle(int id) {
		return usrDao.getCycle(id);
	}

	public void subNumber(int id) {
		usrDao.subNumber(id);
	}

	public List<ToDoList> getFailes(String start, String now) {
		return usrDao.getFailes(start, now);
	}

	public List<ToDoList> getCountByClassificationAndPastday(String classification, String start, String now) {
		return usrDao.getCountByClassificationAndPastday(classification, start, now);
	}

	public int getCountAllByPastday(String start, String now) {
		return usrDao.getCountAllByPastday(start, now);
	}

	public void addReason(Map<String, Object> param) {
		usrDao.addReason(param);		
	}

	public void doInvisible(int id) {
		usrDao.doInvisible(id);
	}

	public int getCountByFailes(String start, String now) {
		return getFailes(start, now).size();
	}

	public void doSuccess(int id) {
		usrDao.doSuccess(id);
	}

	public void doSuccessByCycle(int id) {
		// 사이클의 마지막 순서를 성공하면 다시 처음으로 돌아가기 위한 코드
		if(usrDao.getCycles().size() == usrDao.getCycle(id).getNumber()) {
			usrDao.UnSuccessByCycle(id);
		}else {
			usrDao.UnSuccessByCycle(id);
			usrDao.doSuccessByCycle(id);			
		}
	}
	
}
