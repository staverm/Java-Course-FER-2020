<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	String bgColor = "FFFFFF";
	if(session.getAttribute("pickedBgCol") != null){
		bgColor = session.getAttribute("pickedBgCol").toString();
	}
%>
<html>
<head>
	<style>
		body { background-color: #<%=bgColor%>; }
	</style>
	<title>OS usage</title>
</head>
	<body>
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="reportImage" alt="OS usage">
	</body>
</html>