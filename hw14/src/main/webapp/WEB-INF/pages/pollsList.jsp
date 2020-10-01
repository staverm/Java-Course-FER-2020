<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Glasanje</title>
</head>
<body>
	<h1>Dostupne ankete:</h1>
	<p>Kliknite na naslov ankete u kojoj želite sudjelovati</p>
	<ol>
		<c:forEach items="${polls}" var="poll">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>