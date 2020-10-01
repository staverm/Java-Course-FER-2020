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
	<title>Set color</title>
</head>
<body>
	<a href="setcolor?color=FFFFFF">WHITE</a><br>
	<a href="setcolor?color=FF0000">RED</a><br>
	<a href="setcolor?color=00FF00">GREEN</a><br>
	<a href="setcolor?color=00FFFF">CYAN</a><br>
</body>
</html>