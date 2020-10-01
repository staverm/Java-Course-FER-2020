<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String bgColor = "FFFFFF";
	if (session.getAttribute("pickedBgCol") != null) {
		bgColor = session.getAttribute("pickedBgCol").toString();
	}

	Double a = Math.toRadians(((Integer) request.getAttribute("a")).doubleValue());
	Double b = Math.toRadians(((Integer) request.getAttribute("b")).doubleValue());
%>
<html>
<head>
<style>
body {
	background-color: #<%=bgColor%>;
}

table, th, td {
	border: 1px solid black;
}
</style>
<title>Trigonometry</title>
</head>
<body>
	<table>
		<tr>
			<th>value</th>
			<th>sin(x)</th>
			<th>cos(x)</th>
		</tr>		
		<c:forEach var = "i" begin = "${a}" end = "${b}">
         <tr>
			<td><c:out value = "${i}"/></td>
			<td><c:out value = "${String.format(\"%.3f\",Math.sin(Math.toRadians(i)))}"/></td>
			<td><c:out value = "${String.format(\"%.3f\",Math.cos(Math.toRadians(i)))}"/></td>
		</tr>
      </c:forEach>
	</table>
</body>
</html>