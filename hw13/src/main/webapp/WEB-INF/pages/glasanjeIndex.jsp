<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String bgColor = "FFFFFF";
	if (session.getAttribute("pickedBgCol") != null) {
		bgColor = session.getAttribute("pickedBgCol").toString();
	}
%>
<html>
<head>
<style>
body {
	background-color: #<%=bgColor%>;
}
</style>
<title>Glasanje</title>
</head>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach items="${bands}" var="band" >
			<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>