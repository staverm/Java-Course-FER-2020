<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Author: ${author.nick}</title>
	</head>

	<body>
	<c:choose>
		<c:when test="${sessionScope['current.user.id'] == null}">
		<header>Not loged in</header>
		</c:when>
		<c:otherwise>
			<header>
				<h3>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</h3>
				<a href="../logout">Logout</a>			
			</header>
		</c:otherwise>
	</c:choose>
	<a href="../main">Back</a>
	<br><br>
	<c:choose>
	<c:when test="${entries.size() == 0}">
		<h3>No blog entries found</h3>
	</c:when>
	<c:otherwise>
		<h3>Blog entries from user ${author.nick}:</h3>
		<c:forEach items="${entries}" var="e" >
				<li><a href="${author.nick}/${e.id}">${e.title}</a></li>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${author.nick.equals(sessionScope['current.user.nick'])}">
		<br>
		<a href="${author.nick}/new">Add new blog entry</a>
	</c:when>
	</c:choose>
	</body>
</html>