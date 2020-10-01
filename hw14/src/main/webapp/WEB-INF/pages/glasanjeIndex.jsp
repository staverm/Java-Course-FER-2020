<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Glasanje</title>
</head>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach items="${poll.options}" var="option" >
			<li><a href="glasanje-glasaj?pollID=${poll.id}&optionID=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>