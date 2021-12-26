<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<title>fail_detail</title>
<%@ include file="part/head.jspf"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
Add__submit = false;
function Add__reasonAndSubmit(form) {
	if ( Add__submit ) {
		alert('처리중입니다.');
		return;
	}
	
	form.reason.value = form.reason.value.trim();
	if ( form.reason.value.length == 0 ) {
		alert('이유는 공백이 올 수 없습니다.');
		form.reason.focus();
		return false;
	}
	
	Add__submit = true;
	form.submit();
}
</script>

<!-- 실패한 이유를 작성한 후에 삭제 버튼이 생기도록 만듬 -->
<table class="bg-white shadow rounded container mx-auto p-8 mt-8">
	<tr class="shadow">
		<td colspan="13">
			<div class="bg-blue-100 text-center text-xl">fail list</div>
		</td>
	</tr>
	<tr class="border-b-2 bg-green-100">
		<th colspan="1" class="border-r-2">날짜</th>
		<th colspan="1" class="border-r-2">분류</th>
		<th colspan="5" class="border-r-2">내용</th>
		<th colspan="5" class="border-r-2">이유</th>
		<th colspan="1" class="border-r-2">작성/삭제<th>
	</tr>
 	<c:forEach items="${failes}" var="fail">
 		<c:if test="${fail.visible == 1}">
	 		<tr class="border-b-2">
		 		<fmt:parseDate var="parseDate" value="${fail.doDate}" pattern="yyyy-MM-dd"/>
				<fmt:formatDate var="resultDt" value="${parseDate}" pattern="yyyy-MM-dd"/>
	 			<th colspan="1" class="border-r-2">${resultDt}</th>
	 			<th colspan="1" class="border-r-2">${fail.classification}</th>
	 			<th colspan="5" class="border-r-2">${fail.contents}</th>
	 			<c:if test="${fail.reason == null}">
	 				<form onsubmit="Add__reasonAndSubmit(this); return false;" action="doAddReason" method="POST">
	 					<!-- id값으로 해당 정보의 이유를 추가해야 해서 -->
	 					<input type="hidden" name="id" value="${fail.id}"/>
	 					<input type="hidden" name="user" value="${loginedMember}"/>
		 				<th colspan="5" class="border-r-2">
		 					<input type="text" name="reason" class="form-row-input w-full rounded-sm" placeholder="이유를 입력해주세요."/>
		 				</th>
		 				<th>
							<input type="submit" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded" value="작성">
		 				</th>
	 				</form>
	 			</c:if>
 				<c:if test="${fail.reason != null}">
 					<th colspan="5" class="border-r-2">
	 					${fail.reason}
	 				</th>
 					<th>
	 					<a onclick="if ( !confirm('삭제 후에도 기록이 남아 있습니다. 정말 삭제하시겠습니까?') ) return false;"
							href="doInvisible?id=${fail.id}"
							class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded">삭제</a>
	 				</th>
 				</c:if>
	 		</tr>
 		</c:if>
 	</c:forEach>
</table>
<!-- 분류별 실패한 확률 -->
<div class="shadow rounded overflow-hidden container bg-white mx-auto mt-8">
  <div class="py-1 bg-blue-100 text-center text-xl">fail chart</div>
  <canvas class="p-5" id="chartBar"></canvas>
</div>
<!-- Chart bar -->
<script>
	const labelsBarChart = [
		"${classification[0]}",
		"${classification[1]}",
		"${classification[2]}",
		"${classification[3]}",
		"${classification[4]}",
		"${classification[5]}",
		"${classification[6]}",
		"${classification[7]}",
	];
 	const rate = [
	  
  	];
  	const dataBarChart = {
    	labels: labelsBarChart,
    	datasets: [
      	{
        	label: ["분류별 실패율"],
        	backgroundColor: "red",
        	borderColor: "red",
        	data: ["${failRate[0]}", "${failRate[1]}", "${failRate[2]}", "${failRate[3]}", "${failRate[4]}", "${failRate[5]}", "${failRate[6]}", "${failRate[7]}"],
      	},
      	{
        	label: ["전체에서 각 분류가 차지하는 비율"],
        	backgroundColor: "violet",
        	borderColor: "violet",
        	data: ["${totalRate[0]}", "${totalRate[1]}", "${totalRate[2]}", "${totalRate[3]}", "${totalRate[4]}", "${totalRate[5]}", "${totalRate[6]}", "${totalRate[7]}"],
      	},
    	],
  	};

	const configBarChart = {
   		type: "bar",
    	data: dataBarChart,
  	};

  	var chartBar = new Chart(
    	document.getElementById("chartBar"),
    	configBarChart
  	);
</script>
<%@ include file="part/foot.jspf"%>