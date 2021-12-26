<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<!-- 제이쿼리 불러오기 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- 테일윈드 불러오기 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.0.3/tailwind.min.css">
<!-- 모바일에서 사이트가 PC에서의 픽셀크기 기준으로 작동하게 하기(반응형 하려면 필요) -->
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

</head>
<body class="bg-gray-100">

<script>
	const LoginForm__checkAndSubmitDone = false;
	function LoginForm__checkAndSubmit(form) {
		if (LoginForm__checkAndSubmitDone) {
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}
		if (form.loginPw.value.length == 0) {
			alert('로그인비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		LoginForm__checkAndSubmitDone = true;
		form.submit();
	}
</script>
<section class="section-login">
	<div
		class="container mx-auto min-h-screen flex items-center justify-center">
		<div class="w-full">
			<form class="bg-white shadow-md rounded px-8 pt-6 pb-8 mt-4"
				action="doLogin" method="POST"
				onsubmit="LoginForm__checkAndSubmit(this); return false;">
				<input type="hidden" name="redirectUrl" value="${param.redirectUrl}" />
				<div class="flex flex-col mb-4 md:flex-row">
					<div class="p-1 md:w-36 md:flex md:items-center">
						<span>로그인아이디</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
							autofocus="autofocus" type="text" placeholder="로그인 아이디를 입력해주세요."
							name="loginId" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col mb-4 md:flex-row">
					<div class="p-1 md:w-36 md:flex md:items-center">
						<span>로그인비번</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker"
							autofocus="autofocus" type="password"
							placeholder="로그인 비밀번호를 입력해주세요." name="loginPw" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col mb-4 md:flex-row">
					<div class="p-1 md:w-36 md:flex md:items-center">
					</div>
					<div class="p-1">
						<input
							class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
							type="submit" value="로그인" />
						<a href="join" class="btn-info bg-green-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded inline-block">회원가입</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</section>
</body>
</html>