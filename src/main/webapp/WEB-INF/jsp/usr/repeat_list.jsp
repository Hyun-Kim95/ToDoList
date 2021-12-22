<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="UTF-8">
<title>repeat_list</title>
<%@ include file="part/head.jspf"%>
<form action="doAddRepeat" method="POST" class="mx-2">
	<table class="bg-white shadow rounded container mx-auto p-8 mt-8">
		<tr class="shadow">
			<th colspan="7">
				<div class="bg-blue-100 text-xl">everyday list</div>
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
<form action="doAddCycle" method="POST" class="mx-2">
	<table class="bg-white shadow rounded container mx-auto p-8 mt-8">
		<tr class="shadow">
			<th colspan="7">
				<div class="bg-blue-100 text-xl">cycle list</div>
			</th>
		</tr>
	 	<tr class="shadow border-b-2">
	 		<th colspan="1">
	 			<select name="number">
	 				<c:forEach begin="1" end="${cycleNum+1}" var="num">
	 					<option value="${num}">${num}</option>
	 				</c:forEach>
	 			</select>
	 		</th>
	 		<th colspan="5">
	 			<input type="text" name="contents" placeholder="내용을 입력해주세요" class="form-row-input w-full rounded-sm flex-grow">
	 		</th>
	 		<th colspan="1">
	 			<input type="submit" class="btn-primary bg-blue-500 text-white font-bold py-1 px-4 rounded" value="추가">
	 		</th>
	 	</tr>
	 	<c:forEach items="${cycles}" var="cycle">
	 		<tr class="border-b-2">
	 			<th colspan="1" class="border-r-2">${cycle.number}</th>
	 			<th colspan="5" class="border-r-2">${cycle.contents}</th>
	 			<th>
	 				<a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;"
						href="doDelete?id=${cycle.id}"
						class="ml-2 text-blue-500 hover:underline">삭제</a>
	 			</th>
	 		</tr>
	 	</c:forEach>
	</table>
</form>
<%@ include file="part/foot.jspf"%>