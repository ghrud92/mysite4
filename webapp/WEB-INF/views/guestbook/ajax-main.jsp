<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
var formatTime = function( timestamp ) {
	var date = new Date(timestamp);
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDay();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var seconds = date.getSeconds(); 
	var formattedTime = year+'.'+month+'.'+day+' '+hour + ':' + minute + ':' + seconds;
	return formattedTime;
}
var page = 1;
var dialogDelete = null;
var fetchList = function(){
	$.ajax({
		url:"${pageContext.request.contextPath}/guestbook/ajax-list?p=" + page,
		type:"get",
		dataType:"json",
		data:"",
		success:function(response){
			if(response.result != "success"){
				return;
			}
			if(response.data.length == 0){
				console.log("------end------");
				$("#btn-next").attr("disabled", true);
				return;
			}
			
			$.each(response.data, function(index, vo){
				renderHtml(vo);
			})
			
			console.log(response);
			page++;
		},
		error:function(xhr/*XMLHttpRequest*/, status, error){
			console.error(status + ":" + error);
		}
	})
}

var renderHtml = function(vo){
	//대신에 js template Library를 사용한다 ex)EJS, underscore.js
	var html = "<li id='li-"+ vo.no +"'><table><tr>" +
				"<td>"+vo.name+"</td>" +
				"<td>"+formatTime( vo.regDate )+"</td>" +
				"<td><a href='#' class='a-del' data-no='"+vo.no+"'>삭제</a></td>" +
				"</tr><tr>" +
				"<td colspan='3'>"+vo.message.replace(/\r\n/g, "<br>")+"</td>" +
				"</tr></table><br></li>";
	$("#gb-list").append(html);
}

var preRenderHtml = function(vo){
	//대신에 js template Library를 사용한다 ex)EJS, underscore.js
	var html = "<li id='li-"+vo.no+"'><table><tr>" +
				"<td>"+vo.name+"</td>" +
				"<td>"+formatTime( vo.regDate )+"</td>" +
				"<td><a href='#' class='a-del' data-no='"+vo.no+"'>삭제</a></td>" +
				"</tr><tr>" +
				"<td colspan='3'>"+vo.message.replace(/\r\n/g, "<br>")+"</td>" +
				"</tr></table><br></li>";
	$("#gb-list").prepend(html);
}

$(function(){
	$("#form-insert").submit(function(event){
		event.preventDefault();
		console.log("in form submit")
		$(".alertmessage").hide();
		if($("#name").val() == ""){
			$(".alertmessage").text("이름 채워").show();
			return false;
		}
		if($("#pass").val() == ""){
			$(".alertmessage").text("비번 채워").show();
			return false;
		}
		if($("tr #content").val() == ""){
			$(".alertmessage").text("내용 채워").show();
			return false;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/guestbook/ajax-insert",
			type:"post",
			dataType:"json",
			data:"name="+$("#name").val()+"&passwd="+$("#pass").val()+"&message="+$("tr #content").val(),
			success:function(response){
				if(response.result != "success"){
					return;
				}
				
				preRenderHtml(response.data);
				$("#name, #pass, tr #content").val("");
			},
			error:function(xhr, status, error){
				console.log(status + ":" + error);
			}
		})
	});
	$("#btn-next").on("click",function(){
		fetchList();
	})
	
	// 삭제버튼 클릭 이벤트 매핑(Live Event)
	$(document).on("click",".a-del",function(){
		event.preventDefault();
		var no = $(this).attr("data-no");
		$("#del-no").val(no);
		dialogDelete.dialog("open");
	})
	
	$(window).scroll(function(){
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		var scrollTop = $(window).scrollTop();
		//console.log(documentHeight + ":" + windowHeight + ":" + (scrollTop + windowHeight));
	})
	
	//dialogDelete 객체 생성
	dialogDelete = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 200,
      width: 350,
      modal: true,
      buttons: {
        "삭제": function(){
			var no = $("#del-no").val();
        	var password = $("#del-password").val();
        	
        	$.ajax({
    			url:"${pageContext.request.contextPath}/guestbook/ajax-delete",
    			type:"post",
    			dataType:"json",
    			data:"no="+no+"&passwd="+password,
    			success:function(response){
    				if(response.result != "success"){
    					return;
    				}
    				
    				$("#li-" + no).remove();
    			},
    			error:function(xhr, status, error){
    				console.log(status + ":" + error);
    			}
    		})
			dialogDelete.dialog("close");
        },
        "취소": function() {
          dialogDelete.dialog( "close" );
        }
      },
      close: function() {
        //form[ 0 ].reset();
        //allFields.removeClass( "ui-state-error" );
      }
    });
	//최초 가져오기
	fetchList();
})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="guestbook">
				<p class="alertmessage"></p>
				<form id="form-insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" id="name" name="name"></td>
							<td>비밀번호</td><td><input type="password" id="pass" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id = "gb-list">
				</ul>
				<div style="margin-top:20px; text-align:center;">
					<button id="btn-next">다음 가져오기</button>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="location" value="guestbook"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
	<div id="dialog-form" title="메세지 비밀번호 입력">
	  <p class="validateTips">메세지의 비밀번호를 입력해주세요.</p>
	 
	  <form style="margin-top:20px;">
	      <label for="password">비밀번호</label>
	      <input type="hidden" id="del-no" value="">
	      <input type="password" name="password" id="del-password" value="" class="text ui-widget-content ui-corner-all">
	 
	      <!-- Allow form submission with keyboard without duplicating the dialog button -->
	      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
	  </form>
	</div>
</body>
</html>