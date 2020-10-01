<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String bgColor = "FFFFFF";
	if (session.getAttribute("pickedBgCol") != null) {
		bgColor = session.getAttribute("pickedBgCol").toString();
	}

	Object a = request.getAttribute("a");
	Object b = request.getAttribute("b");
	Object n = request.getAttribute("n");
%>
<html>
<head>
<style>
body {
	background-color: #<%=bgColor%>;
}
</style>
<title>Error</title>
</head>
<body>
	<h1>Error</h1>
	
	<%	
		if(request.getAttribute("notNumbers") != null){
			out.print("<p>All parameters have to be integers!</p>");
		}
		if (a != null) {
			out.print("<p>Invalid value a = " + a + ". Allowed values for a are [-100,100] and a > b has to be true.</p>");
		}
		if (b != null) {
			out.print(
					"<p>Invalid value b = " + b + ". Allowed values for b are [-100,100].\n</p>");
		}
		if (n != null) {
			out.print("<p>Invalid value n = " + n + ". Allowed values for n are [1, 5].\n</p>");
		}
	%>
</body>
</html>