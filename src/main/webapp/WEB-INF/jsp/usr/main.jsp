<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<title>main</title>
<%@ include file="part/head.jspf"%>
<div class="container mx-auto p-1">

	<div class="flex-col md:flex-row lg:flex xl:flex">
		<!-- 성공률 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">success rate</div>
			<div class="border-4 border-black p-2 mx-10 sm:mt-10 mb-5 text-3xl font-bold">${success_rate}%</div>
		</div>
		<!-- 일자별 성공률 그래프 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">daily graph</div>
			<canvas class="p-5" id="chartBar"></canvas>
		</div>
	</div>
	
	<div class="flex-col sm:flex-row md:flex lg:flex xl:flex">
		<!-- 오늘 할 일 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">today</div>
			<div class="border-4 border-black p-2 mx-10 sm:mt-10 mb-5">
				<ul>
					<c:forEach items="${todays}" var="today">
						<li class="border-b-2">
							<span class="border-2 bg-green-50">${today.classification}</span>
							<span>${today.contents}</span>
							<span class="flex-grow"></span>
							<c:if test="${today.success == 0}">
								<a onclick="if ( !confirm('성공처리 하시겠습니까?') ) return false;"
									href="doSuccess?id=${today.id}"
									class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded">성공</a>
							</c:if>
							<c:if test="${today.success == 1}">
								성공√
							</c:if>
						</li>	
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 매일 할 일 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">everyday</div>
			<div class="border-4 border-black p-2 mx-10 sm:mt-10 mb-5">
				<ul>
					<c:forEach items="${everydays}" var="everyday">
						<li class="border-b-2">
							<span class="border-2 bg-green-50">${everyday.classification}</span>
							<span>${everyday.contents}</span>
							<span class="flex-grow"></span>
							<c:if test="${everyday.success == 0}">
								<a onclick="if ( !confirm('성공처리 하시겠습니까?') ) return false;"
									href="doSuccess?id=${everyday.id}"
									class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded">성공</a>
							</c:if>
							<c:if test="${everyday.success == 1}">
								성공√
							</c:if>
						</li>	
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="flex-col sm:flex-row md:flex lg:flex xl:flex">
		<!-- 내일 할 일 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">tomorrow</div>
			<div class="border-4 border-black p-2 mx-10 sm:mt-10 mb-5">
				<ul>
					<c:forEach items="${tomorrows}" var="tomorrow">
						<li class="border-b-2">
							<span class="border-2 bg-green-50">${tomorrow.classification}</span>
							<span>${tomorrow.contents}</span>
						</li>	
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 사이클 할 일 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">cycle</div>
			<div class="border-4 border-black p-2 mx-10 sm:mt-10 mb-5">
				<ul>
					<c:forEach items="${cycles}" var="cycle">
						<li class="border-b-2">
							<span class="border-2 bg-green-50 rounded-3xl text-xl">${cycle.number}</span>
							<span>${cycle.contents}</span>
							<c:if test="${cycle.number == 1 && cycle1 == 0 || cycle1 != 0 && cycle.number == num}">
								<a onclick="if ( !confirm('성공처리 하시겠습니까?') ) return false;"
									href="doSuccess?id=${cycle.id}&success=1"
									class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded">성공</a>
							</c:if>
						</li>	
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- Chart bar -->
<script>
	const labelsBarChart = [
		"${day[0]}",
		"${day[1]}",
		"${day[2]}",
		"${day[3]}",
		"${day[4]}",
		"${day[5]}",
		"${day[6]}",
		"${day[7]}",
	];
  	const dataBarChart = {
    	labels: labelsBarChart,
    	datasets: [
      	{
        	label: ["일자별 성공률"],
        	backgroundColor: "blue",
        	borderColor: "blue",
        	data: ["${dayRate[0]}", "${dayRate[1]}", "${dayRate[2]}", "${dayRate[3]}", "${dayRate[4]}", "${dayRate[5]}", "${dayRate[6]}", "${dayRate[7]}"],
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