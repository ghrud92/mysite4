<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#join-form").submit(function(){
		/* // 1. 이름 유효성 채크(validation)
		if($("#name").val() == ""){
			alert("이름을 입력해주세요");
			$("#name").focus();
			return false;
		}
		
		// 2. 이메일
		if($("#email").val() == ""){
			alert("이메일을 입력해주세요");
			$("#email").focus();
			return false;
		} */
		/* if($("#img-checkemail").is(":visible") == false){
			alert("중복 확인을 해주세요");
			return false;
		} */
		
		// 3. 약관 채크 유무
		//alert("여기가 보이면 폼을 서밋하세요");
		
		return true;
	});
	$("#email").change(function(){
		$("#btn-checkemail").show();
		$("#img-checkemail").hide();
	});
	$("#btn-checkemail").click(function(){
		var email = $("#email").val();
		if(email == ""){
			return;
		}
		console.log(email);
		$.ajax({
			url:"${pageContext.request.contextPath }/user/checkemail?email=" + email,	//요청 url
			type:"get",							// 통신 방식
			dataType:"json",					// 수신 데이터 타입
			data:"",							// post 방식인 경우 서버에 전달할 파라미터 데이터
												// ex) a=checkemail&email=ghrud92@gmail.com
//			contentType:"application/json",		// 보내는 데이터가 json 형식인 경우. 반드시 post 방식으로 보내야한다.
												// ex) "{ "a":"checkemail", "email":"ghrud92@gmail.com" }"
			success:function(response){			// 성공시 callback
				if(response.result != "success"){
					return;
				}
				if(response.data == false){
					alert("이미 존재하는 이메일입니다");
					$("#email").val("").focus();
					return;
				}
				//alert("사용 가능한 이메일입니다");
				$("#btn-checkemail").hide();
				$("#img-checkemail").css("height","15px").show();
			},
			error:function(jqXHR, status, error) {	// 실패시 callback
				console.error(status + ":" + error);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('name') }">
							<c:set var="errorName1" value="${errors.getFieldError('name').codes[0] }"/>
							<strong style="color:red;">
								<spring:message code="${errorName1}" text= "${errors.getFieldError( 'name' ).defaultMessage }" />
							</strong>
						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input id="btn-checkemail" type="button" value="id 중복체크">
					<img id="img-checkemail" style="display:none;" src="${pageContext.request.contextPath }/assets/images/check.png" alt="">

					<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('email') }">
							<br>
							<strong style="color:red;">
							<c:set var="errorName2" value="${errors.getFieldError( 'email' ).codes[0] }"/>
								<spring:message code="${errorName2 }" text = "${errors.getFieldError( 'email' ).defaultMessage }"/>
							</strong>
						</c:if>
					</spring:hasBindErrors>
					
					<label class="block-label">패스워드</label>
					<input name="passwd" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="F" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="M">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
</body>
</html>