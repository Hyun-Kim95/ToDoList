<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>daily_list</title>
<%@ include file="part/head.jspf"%>
<script>
    $(function(){
        var today = new Date();
        var date = new Date();           

        $("input[name=preMon]").click(function() { // 이전달
            $("#calendar > tbody > td").remove();
            $("#calendar > tbody > tr").remove();
            today = new Date ( today.getFullYear(), today.getMonth()-1, today.getDate());
            buildCalendar();
        })
        
        $("input[name=nextMon]").click(function(){ //다음달
            $("#calendar > tbody > td").remove();
            $("#calendar > tbody > tr").remove();
            today = new Date ( today.getFullYear(), today.getMonth()+1, today.getDate());
            buildCalendar();
        })
        
        function buildCalendar() {
            
            nowYear = today.getFullYear();
            nowMonth = today.getMonth();
            firstDate = new Date(nowYear,nowMonth,1).getDate();
            firstDay = new Date(nowYear,nowMonth,1).getDay(); //1st의 요일
            lastDate = new Date(nowYear,nowMonth+1,0).getDate();

            if((nowYear%4===0 && nowYear % 100 !==0) || nowYear%400===0) { //윤년 적용
                lastDate[1]=29;
            }

            $(".year_mon").text(nowYear+"년 "+(nowMonth+1)+"월");

            for (i=0; i<firstDay; i++) { //첫번째 줄 빈칸
                $("#calendar tbody:last").append("<td class='shadow rounded container'></td>");
            }
            for (i=1; i <=lastDate; i++){ // 날짜 채우기
                plusDate = new Date(nowYear,nowMonth,i).getDay();
                if (plusDate==0) {
                    $("#calendar tbody:last").append("<tr></tr>");
                }
                $("#calendar tbody:last").append("<td class='date flex-shrink shadow rounded container sm:text-xl md:text-2xl'>"
                		+ "<button name=\""+"day\""+" class=\""+"btn-primary px-3 2xl:px-24 xl:px-20 lg:px-16 md:px-10 sm:px-9 sm:py-4 md:py-5 lg:py-8\""+ "value=\"" + nowYear + "-" + (nowMonth+1) + "-" + i + "\">"+i+"</button>" +"</td>");
            }
            if($("#calendar > tbody > td").length%7!=0) { //마지막 줄 빈칸
                for(i=1; i<= $("#calendar > tbody > td").length%7; i++) {
                    $("#calendar tbody:last").append("<td class='shadow rounded container></td>");
                }
            }
            $(".date").each(function(index){ // 오늘 날짜 표시
                if(nowYear==date.getFullYear() && nowMonth==date.getMonth() && $(".date").eq(index).text()==date.getDate()) {
                    $(".date").eq(index).addClass('colToday font-bold bg-green-100');
                }
            })
        }
        buildCalendar();
    })
    
</script>
<form action="daily_list" method="GET" class="mx-2">
    <table id="calendar" class="bg-white shadow rounded container mx-auto p-8 mt-8">
        <thead>
            <tr class="shadow">
                <th><input class="bg-white sm:text-3xl md:text-5xl rounded" name="preMon" type="button" value="◀"></th>
                <th colspan="5" class="year_mon sm:text-3xl md:text-5xl p-5"></th>
                <th><input class="bg-white sm:text-3xl md:text-5xl rounded" name="nextMon" type="button" value="▶"></th>
            </tr>
            <tr class="md:text-2xl sm:text-xl">
                <th class="shadow flex-grow-0 flex-shrink-0">일</th>
                <th class="shadow">월</th>
                <th class="shadow">화</th>
                <th class="shadow">수</th>
                <th class="shadow">목</th>
                <th class="shadow">금</th>
                <th class="shadow">토</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
   </form>
   <form action="doAdd?day=${selectedDay}" method="POST" class="mx-2">
	<table class="bg-white shadow rounded container mx-auto p-8 mt-8">
		<tr class="shadow">
			<th colspan="7">
				<div class="bg-blue-100">선택된 날짜 : ${selectedDay}</div>
			</th>
		</tr>
 	<tr class="shadow border-b-2">
 		<th colspan="1">
 			<select name="classification">
 				<option value="취미">취미</option>
 				<option value="운동">운동</option>
 				<option value="쇼핑">쇼핑</option>
 				<option value="위생">위생</option>
 				<option value="자기개발">자기개발</option>
 				<option value="건강">건강</option>
 				<option value="봉사">봉사</option>
 				<option value="기타">기타</option>
 			</select>
 		</th>
 		<th colspan="5">
 			<input type="text" name="contents" placeholder="내용을 입력해주세요" class="form-row-input w-full rounded-sm flex-grow">
 		</th>
 		<th colspan="1">
 			<input type="submit" class="btn-primary bg-blue-500 text-white font-bold py-1 px-4 rounded" value="추가">
 		</th>
 	</tr>
 	<c:forEach items="${toDos}" var="toDo">
 		<tr class="border-b-2">
 			<th colspan="1" class="border-r-2">${toDo.classification}</th>
 			<th colspan="5" class="border-r-2">${toDo.contents}</th>
 			<th>
 				<a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;"
					href="doDelete?id=${toDo.id}&day=${toDo.doDate}"
					class="ml-2 text-blue-500 hover:underline">삭제</a>
 			</th>
 		</tr>
 	</c:forEach>
	</table>
</form>
<%@ include file="part/foot.jspf"%>