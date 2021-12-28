package com.toy.kh.ToDoList.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toy.kh.ToDoList.dto.CycleList;
import com.toy.kh.ToDoList.dto.Member;
import com.toy.kh.ToDoList.dto.ToDoList;
import com.toy.kh.ToDoList.service.UsrService;
import com.toy.kh.ToDoList.util.Util;

@Controller
public class UsrController {
	@Autowired
	private UsrService usrService;
	
	@RequestMapping("/usr/main")
	public String showMain(HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		String start = "1111-11-11";
		String now = Util.getNowDateStr();
		
		List<ToDoList> todays = usrService.getListByDate(now,user);
		List<ToDoList> tomorrows = usrService.getListByDate(Util.getTomorrowDateStr(), user);
		List<ToDoList> everydays = usrService.getListByDate("0000-00-00", user);
		List<CycleList> cycles = usrService.getCycles(user);
		int chk = 0;
		for (CycleList cycle : cycles) {
			if(chk == 1) {
				req.setAttribute("num", cycle.getNumber());
				break;
			}
			if(cycle.getSuccess() == 1) {
				chk++;
			}
		}
		if(chk == 0) {
			req.setAttribute("cycle1", 0);
		}
		
		// 어제까지의 전체 목록의 갯수(반복제외)
		float totalCount = usrService.getCountAllByPastday(start, now, user);
		
		float success_rate = 0;
		if(totalCount != 0) {
			// 실패갯수 구하는 함수를 이용하여 성공갯수 구함
			float successCount = totalCount - usrService.getCountByFailes(start, now, user);
			// 성공률 
			success_rate = (successCount / totalCount) * 100;
		}


		// daily graph 를 위한 날짜형식 변경
		String today = now.split("-")[1] + "-" + now.split("-")[2];
		
		// main페이지로 보내줄 일자목록
		String[] day = new String[8];
		// 일자별 성공률
		float[] dayRate = new float[8];
		
		// 최근 8일을 보내줄 거임
		for(int i=7;i>=0;i--) {
			day[i] = today;
			// 해당 일자의 일정들
			List<ToDoList> todolist = usrService.getListByDate(now.split("-")[0] + "-" + today, user);
			int cnt = 0;
			for (ToDoList todo : todolist) {
				if(todo.getSuccess() == 1) {
					cnt++;
				}
			}
			// 성공한 갯수를 구해서 일자별 성공률을 구함
			dayRate[i] = todolist.size()!=0 ? ((float)cnt / (float)todolist.size() * 100) : 0;
			
			if(today.split("-")[1].equals("1")) {
				if(today.split("-")[0].equals("1")) {
					today = "12-31";
					now = (Integer.parseInt(now.split("-")[0])-1) + "-12-31";
				}else {
					today = (Integer.parseInt(today.split("-")[0])-1) + "-" + Util.lastday(Integer.parseInt(now.split("-")[0]), (Integer.parseInt(today.split("-")[0])-1));
				}
			}else {				
				today = today.split("-")[0] + "-" + (Integer.parseInt(today.split("-")[1])-1);
			}
		}
		
		req.setAttribute("cycles", cycles);
		req.setAttribute("tomorrows", tomorrows);
		req.setAttribute("everydays", everydays);
		req.setAttribute("todays", todays);
		req.setAttribute("dayRate", dayRate);
		req.setAttribute("day", day);
		req.setAttribute("success_rate", success_rate);
		return "usr/main";
	}
	
	@RequestMapping("/usr/doSuccess")
	@ResponseBody
	public String doSuccess(@RequestParam Map<String, Object> param) {
		String user = param.get("user").toString();
		
		if(param.get("id") == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		
		int id = Integer.parseInt(param.get("id").toString());
		
		if(param.get("success") != null && param.get("success").toString().equals("1")) {
			usrService.doSuccessByCycle(id, user);
		}else {
			usrService.doSuccess(id, user);
		}
		
		return Util.msgAndReplace("일정이 성공처리 되었습니다.", "main");
	}
	
	@RequestMapping("/usr/fail_detail")
	public String showFail(HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		// 0으로만 이뤄진 everyday list를 제외한 어제까지의 일정 중, success가 0인 항목을 가져옴
		String start = "1111-11-11";
		String now = Util.getNowDateStr();
		List<ToDoList> failes = usrService.getFailes(start, now, user);
		
		// 분류별 그래프를 만들기 위한 문자열
		String classification = "취미,운동,쇼핑,위생,자기개발,건강,봉사,기타";
		// 실패한 모든 일정의 갯수
		int realTotal = usrService.getCountAllByPastday(start, now, user);
		// 전체에서 각 분류의 비율들을 담은 리스트
		float[] totalRate = new float[8];
		// 분류 내에서 실패한 일정의 비율
		float[] failRate = new float[8];
		
		for(int i=0;i<8;i++) {
			String classifi = classification.split(",")[i];
			// 각 분류별 리스트
			List<ToDoList> total = usrService.getCountByClassificationAndPastday(classifi,start,now, user);
			// 전체에서 각 분류의 비율 구함
			totalRate[i] = (float)total.size() / (float)realTotal * 100;
			int cnt = 0;
			
			for (ToDoList one : total) {
				if(one.getSuccess() == 0) {
					cnt++;
				}
				// 각 분류별 실패비율 구함
				failRate[i] = ((float)cnt/(float)total.size()*100);
			}
		}
		req.setAttribute("failes", failes);
		req.setAttribute("classification", classification.split(","));
		req.setAttribute("failRate", failRate);
		req.setAttribute("totalRate", totalRate);
		
		return "usr/fail_detail";
	}
	
	@RequestMapping("/usr/repeat_list")
	public String showRepeat(HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		// 매일 할 일 구분용
		String check = "0000-00-00";
		
		// 매일 할 일 리스트
		List<ToDoList>toDos = usrService.getListByZero(check, user);
		// 사이클 할 일 리스트
		List<CycleList> cycles = usrService.getCycles(user);
		// 사이클의 전체 갯수
		int cycleNum = cycles.size();
		
		req.setAttribute("toDos", toDos);
		req.setAttribute("cycles", cycles);
		req.setAttribute("cycleNum", cycleNum);
		return "usr/repeat_list";
	}

	@RequestMapping("/usr/doAddRepeat")
	@ResponseBody
	public String doAddRepeat(@RequestParam Map<String, Object> param) {

		if(param.get("contents") == "") {
			return Util.msgAndBack("내용을 입력해주세요.");
		}
		
		// 시간정보 임의로 추가(매일 반복 일정은 전부 0으로 통일)
		String doDate = "0000-00-00";
		param.put("doDate", doDate);
		
		usrService.addDoList(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "repeat_list");
	}
	
	@RequestMapping("/usr/doAddCycle")
	@ResponseBody
	public String doAddCycle(@RequestParam Map<String, Object> param) {
		String user = param.get("user").toString();
		
		if(param.get("contents") == "") {
			return Util.msgAndBack("내용을 입력해주세요.");
		}
		// 전체 사이클 갯수
		int totalNum = usrService.getCountCycles(user);
		// 항목을 집어넣을 순서번호
		int selectNum = Integer.parseInt(param.get("number").toString()); 
		
		// 맨뒤가 아니라 중간에 번호를 추가한다면 뒤의 숫자들을 하나씩 미루고 집어넣음
		if(selectNum <= totalNum) {
			List<CycleList>cycles = usrService.getCycles(user);
			for (CycleList cycle : cycles) {
				if (cycle.getNumber() >= selectNum) {
					usrService.addNumber(cycle.getId(), user);
				}
			}
		}
		usrService.addDoCycle(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "repeat_list");
	}
	
	@RequestMapping("/usr/daily_list")
	public String showDaily(String day, HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		String selectedDay;
		
		if(day == null) {
			// 선택한 날짜 정보가 없으면 현재 날짜 가져옴
			selectedDay = Util.getNowDateStr();
		}else {
			selectedDay = day;
		}
		
		// 선택한 날짜의 todo 리스트를 가져옴
		List<ToDoList>toDos = usrService.getListByDate(selectedDay, user);
		// 선택된 달의 전체 일정을 가져오기 위해서 선택된 일자의 연,월을 전달함
		List<ToDoList>MonthToDo = usrService.getListByMonth(selectedDay.split("-")[0]+"-"+selectedDay.split("-")[1], user);
		
		req.setAttribute("selectedDay", selectedDay);
		req.setAttribute("toDos", toDos);
		req.setAttribute("MonthToDo", MonthToDo);
		
		return "usr/daily_list";
	}
	
	@RequestMapping("/usr/doAdd")
	@ResponseBody
	public String doAdd(@RequestParam Map<String, Object> param) {
		if(param.get("contents") == "") {
			return Util.msgAndBack("내용을 입력해주세요.");
		}
		
		// 선택한 날짜에 시간정보 임의로 추가
		String doDate = param.get("day").toString();
		param.put("doDate", doDate);
		
		usrService.addDoList(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "daily_list?day="+doDate);
	}
	
	@RequestMapping("/usr/doAddReason")
	@ResponseBody
	public String doAddReason(@RequestParam Map<String, Object> param) {
		// 할 일을 실패한 이유 추가
		usrService.addReason(param);
		
		return Util.msgAndReplace("이유가 작성되었습니다.", "fail_detail");
	}
	
	@RequestMapping("/usr/doInvisible")
	@ResponseBody
	public String doInvisible(Integer id, HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		if(id == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		// 실제 삭제가 아닌 리스트에만 안보이도록 함
		usrService.doInvisible(id, user);
		
		return Util.msgAndReplace("일정이 삭제되었습니다.", "fail_detail");
	}
	
	@RequestMapping("/usr/doDelete")
	@ResponseBody
	public String doDelete(Integer id, @RequestParam(defaultValue = "1111-11-11")String day, HttpServletRequest req) {
		String user = ((Member) req.getAttribute("loginedMember")).getLoginId();
		if(id == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		day = day.split(" ")[0];
		
		String replace;
		// day 값이 디폴트면 사이클 일정에서 삭제
		if(day.equals("1111-11-11")) {
			replace = "repeat_list";
			List<CycleList> cycles = usrService.getCycles(user);
			CycleList delCycle = usrService.getCycle(id, user);
			
			for (CycleList cycle : cycles) {
				if(cycle.getNumber() > delCycle.getNumber()) {
					usrService.subNumber(cycle.getId(), user);
				}
			}
			usrService.deleteCycle(id, user);
		// day 값이 0으로만 되어 있으면 반복 일정에서 삭제
		}else if(day.equals("0000-00-00")) {
			replace = "repeat_list";
			usrService.deleteDo(id, user);
		// 그 외의 day 값이면 daily에서 삭제
		}else {
			replace = "daily_list?day="+day;
			usrService.deleteDo(id, user);
		}

		return Util.msgAndReplace("일정이 삭제되었습니다.", replace);
	}
}
