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
import com.toy.kh.ToDoList.dto.ToDoList;
import com.toy.kh.ToDoList.service.UsrService;
import com.toy.kh.ToDoList.util.Util;

@Controller
public class UsrController {
	@Autowired
	private UsrService usrService;
	
	@RequestMapping("/usr/fail_detail")
	public String showFail(HttpServletRequest req) {
		// 0으로만 이뤄진 everyday list를 제외한 어제까지의 일정 중, success가 0인 항목을 가져옴
		String start = "1111-11-11";
		String now = Util.getNowDateStr();
		List<ToDoList> failes = usrService.getFailes(start, now);
		
		// 분류별 그래프를 만들기 위한 문자열
		String classification = "취미,운동,쇼핑,위생,자기개발,건강,봉사,기타";
		// 실패한 모든 일정의 갯수
		int realTotal = usrService.getCountAllByPastday(start, now);
		// 전체에서 각 분류의 비율들을 담은 리스트
		float[] totalRate = new float[8];
		// 분류 내에서 실패한 일정의 비율
		float[] failRate = new float[8];
		
		for(int i=0;i<8;i++) {
			String classifi = classification.split(",")[i];
			// 각 분류별 리스트
			List<ToDoList> total = usrService.getCountByClassificationAndPastday(classifi,start,now);
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
		// 매일 할 일 구분용
		String check = "0000-00-00";
		
		// 매일 할 일 리스트
		List<ToDoList>toDos = usrService.getListByZero(check);
		// 사이클 할 일 리스트
		List<CycleList> cycles = usrService.getCycles();
		// 사이클의 전체 갯수
		int cycleNum = cycles.size();
		
		req.setAttribute("toDos", toDos);
		req.setAttribute("cycles", cycles);
		req.setAttribute("cycleNum", cycleNum);
		return "usr/repeat_list";
	}

	@RequestMapping("/usr/doAddRepeat")
	@ResponseBody
	public String doAddRepeat(@RequestParam Map<String, Object> param, HttpServletRequest req) {
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
	public String doAddCycle(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		if(param.get("contents") == "") {
			return Util.msgAndBack("내용을 입력해주세요.");
		}
		// 전체 사이클 갯수
		int totalNum = usrService.getCountCycles();
		// 항목을 집어넣을 순서번호
		int selectNum = Integer.parseInt(param.get("number").toString()); 
		
		// 맨뒤가 아니라 중간에 번호를 추가한다면 뒤의 숫자들을 하나씩 미루고 집어넣음
		if(selectNum <= totalNum) {
			List<CycleList>cycles = usrService.getCycles();
			for (CycleList cycle : cycles) {
				if (cycle.getNumber() >= selectNum) {
					usrService.addNumber(cycle.getId());
				}
			}
		}
		usrService.addDoCycle(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "repeat_list");
	}
	
	@RequestMapping("/usr/daily_list")
	public String showDaily(String day, HttpServletRequest req) {
		String selectedDay;
		
		if(day == null) {
			// 선택한 날짜 정보가 없으면 현재 날짜 가져옴
			selectedDay = Util.getNowDateStr();
		}else {
			selectedDay = day;
		}
		
		// 선택한 날짜의 todo 리스트를 가져옴
		List<ToDoList>toDos = usrService.getListByDate(selectedDay);
		
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
		String doDate = param.get("day").toString();
		param.put("doDate", doDate);
		
		usrService.addDoList(param);
		
		return Util.msgAndReplace("일정이 작성되었습니다.", "daily_list?day="+param.get("day"));
	}
	
	@RequestMapping("/usr/doAddReason")
	@ResponseBody
	public String doAddReason(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		// 할 일을 실패한 이유 추가
		usrService.addReason(param);
		
		return Util.msgAndReplace("이유가 작성되었습니다.", "fail_detail");
	}
	
	@RequestMapping("/usr/doInvisible")
	@ResponseBody
	public String doInvisible(Integer id) {

		if(id == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		// 실제 삭제가 아닌 리스트에만 안보이도록 함
		usrService.doInvisible(id);
		
		return Util.msgAndReplace("일정이 삭제되었습니다.", "fail_detail");
	}
	
	@RequestMapping("/usr/doDelete")
	@ResponseBody
	public String doDelete(Integer id, @RequestParam(defaultValue = "1111-11-11")String day) {

		if(id == null) {
			return Util.msgAndBack("id를 입력해주세요.");
		}
		
		String replace;
		// day 값이 디폴트면 사이클 일정에서 삭제
		if(day.equals("1111-11-11")) {
			replace = "repeat_list";
			List<CycleList> cycles = usrService.getCycles();
			CycleList delCycle = usrService.getCycle(id);
			
			for (CycleList cycle : cycles) {
				if(cycle.getNumber() > delCycle.getNumber()) {
					usrService.subNumber(cycle.getId());
				}
			}
			usrService.deleteCycle(id);
		// day 값이 0으로만 되어 있으면 반복 일정에서 삭제
		}else if(day.equals("0000-00-00")) {
			replace = "repeat_list";
			usrService.deleteDo(id);
		// 그 외의 day 값이면 daily에서 삭제
		}else {
			replace = "daily_list?day="+day;
			usrService.deleteDo(id);
		}

		return Util.msgAndReplace("일정이 삭제되었습니다.", replace);
	}
}
