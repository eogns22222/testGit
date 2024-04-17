<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="resources/common.css" type="text/css">
<style>

</style>
</head>
<body>
	${loginBox}
	<button onclick="location.href='writeForm'">글쓰기</button>
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>등록일</th>
			<th>삭제</th>
		</tr>
		<c:if test="${list.size() < 1}">
			<tr><td colspan="6">작성된 게시글이 없습니다.</td></tr>
		</c:if>
		<c:forEach items="${list}" var="bbs">
			<tr>
				<td>${bbs.idx}</td>
				<td><a href="detail?idx=${bbs.idx}">${bbs.subject}</a></td>
				<td>${bbs.user_name}</td>
				<td>${bbs.bHit}</td>
				<td>${bbs.reg_date}</td>
				<td><a href="del?idx=${bbs.idx}">삭제</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
<script>
	var msg = '${msg}';
	if(msg != ''){
		alert(msg);
	}
</script>
</html>





















