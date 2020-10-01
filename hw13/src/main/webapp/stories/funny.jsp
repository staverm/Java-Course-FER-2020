<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8"%>
<%
	String bgColor = "FFFFFF";
	if (session.getAttribute("pickedBgCol") != null) {
		bgColor = session.getAttribute("pickedBgCol").toString();
	}
	String[] colors = {"85c1e9", "8e44ad", "d35400", "138d75"}; //hex values
	int colorIndex = (int)(Math.random() * colors.length);
%>
<html>
<head>
<style>
body {
	background-color: #<%=bgColor%>;
}

p {
	color: #<%=colors[colorIndex] %>;
}
</style>
</head>
<body>
	<p>The best method for accelerating a computer is the one that
		boosts it by 9.8 m/s2.</p>
	<p>There are two ways to write error-free programs; only the third
		one works.</p>
	<p>A good programmer is someone who always looks both ways before
		crossing a one-way street.</p>
</body>
</html>