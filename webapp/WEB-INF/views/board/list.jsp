<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${pageMap.kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${pageMap.total - pageMap.CountPage * (pageMap.page - 1) }"></c:set>
					<c:forEach items="${pageMap.list }" var="vo" varStatus="status">
						<tr>
							<td>${count - status.index }</td>
							<td style="text-align:left; padding-left:${20*vo.depth}px">
								<c:if test="${vo.depth > 0 }">
									<img src="${pageContext.request.contextPath}/assets/images/reply.jpg" width="15px">
								</c:if>
								<a href="${pageContext.request.contextPath }/board/view?no=${vo.no}&page=${pageMap.page}&kwd=${pageMap.kwd}">${vo.title }</a>
							</td>
							<td>${vo.user.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${vo.user.no == sessionScope.authUser.no }">
									<td>
										<a href="${pageContext.request.contextPath }/board/delete?no=${vo.no}" class="del">삭제</a>
									</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test="${pageMap.left == 1 }">
							<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.startPage - pageMap.CountList}&kwd=${pageMap.kwd}">◀</a></li>
						</c:if>
						<c:forEach begin="${pageMap.startPage }" end="${pageMap.lastPage }" var="i">
							<c:choose>
								<c:when test="${i == pageMap.page }">
									<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${i}&kwd=${pageMap.kwd}">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath }/board?page=${i}&kwd=${pageMap.kwd}">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pageMap.right == 1 }">
							<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.lastPage + 1}&kwd=${pageMap.kwd}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a href="${pageContext.request.contextPath }/board/writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="location" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
</body>
</html>