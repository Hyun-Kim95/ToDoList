package com.toy.kh.ToDoList.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toy.kh.ToDoList.dto.ToDoList;
import com.toy.kh.ToDoList.service.UsrService;
import com.toy.kh.ToDoList.util.Util;

@Controller
public class UsrController {
	@Autowired
	private UsrService usrService;
	
	@RequestMapping("/usr/daily_list")
	public String showDaily(String day, HttpServletRequest req) {
		String selectedDay;
		
		if(day == null) {
			// 선택한 날짜 정보가 없으면 현재 날짜 가져옴
			selectedDay = Util.getNowDateStr();
		}else {
			selectedDay = day;
		}
		String startDay = selectedDay+" 00:00:00";
		String endDay = selectedDay+" 23:59:59";
		
		// 선택한 날짜의 todo 리스트를 가져옴
		List<ToDoList>toDos = usrService.getListByDate(startDay, endDay);
		
		req.setAttribute("selectedDay", selectedDay);
		req.setAttribute("toDos", toDos);
		
		return "usr/daily_list";
	}
	
	@RequestMapping("/usr/doAdd")
	@ResponseBody
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		if(param.get("contents") == "") {
			return Util.msgAndBack("내용을 입력해주세요.");
		}
		
		// 선택한 날짜에 시간정보 임의로 추가
		String doDate = param.get("day").toString()+" 00:00:00";
		param.put("doDate", doDate);
		
		usrService.addDoList(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "daily_list?day="+param.get("day"));
	}
	
	@RequestMapping("/usr/doDelete")
	@ResponseBody
	public String doDelete(Integer id, String day) {

		// 삭제 후에 선택한 날짜로 돌아가기 위해서
		day = day.split(" ")[0];
		
		if(id == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		usrService.deleteDo(id);

		return Util.msgAndReplace("일정이 삭제되었습니다.", "daily_list?day="+day);
	}
}
