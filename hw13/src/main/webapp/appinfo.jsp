<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	String bgColor = "FFFFFF";
	if(session.getAttribute("pickedBgCol") != null){
		bgColor = session.getAttribute("pickedBgCol").toString();
	}
	
	String creationTime = application.getAttribute("CreationTime").toString();
	Long time = System.currentTimeMillis()- Long.parseLong(creationTime);
	Long ms = time % 1000; //milliseconds
	time /= 1000;
	Long s = time % 60; //seconds
	time /= 60;
	Long m = time % 60; //minutes
	time /= 60;
	Long h = time%24; //hours
	Long d = time/24; //days
%>
<html>
<head>
<style>
body {
	background-color: #<%=bgColor%>;
}
</style>
<title>App info</title>
</head>
<body>
	<p>
		Web app running for:
		<%=d+" days "+h+" hours "+m+" minutes "+s+" seconds " + ms +" milliseconds"%></p>
</body>
</html>