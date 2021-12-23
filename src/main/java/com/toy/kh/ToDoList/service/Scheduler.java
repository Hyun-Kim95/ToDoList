package com.toy.kh.ToDoList.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toy.kh.ToDoList.dao.UsrDao;

//하루가 지나면 매일목록의 성공한 것들을 리셋시킴
@Component
public class Scheduler {
	@Autowired
	private UsrDao usrDao;
	
	// 매일 오전 12시에 실행
	@Scheduled(cron = "0 0 0 * * ?")
	public void cronJobSch() throws ParseException {
		usrDao.UnSuccess();
	}
}