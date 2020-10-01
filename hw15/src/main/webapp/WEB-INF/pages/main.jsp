<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Main</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>

	<body>
	<c:choose>
		<c:when test="${sessionScope['current.user.id'] == null}">
		<header>Not loged in</header>
		<h1>
			Login
		</h1>

		<form action="main" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nickname</span><input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="20">
		 </div>
		 <c:if test="${entry.hasError('nick')}">
		 <div class="greska"><c:out value="${entry.getError('nick')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" size="20">
		 </div>
		 <c:if test="${entry.hasError('password')}">
		 <div class="greska"><c:out value="${entry.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" value="Submit">
		</div>
		
		</form>
		
		<a href="register">Register a new account</a>
		</c:when>
		<c:otherwise>
			<header>
				<h3>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</h3>
				<a href="logout">Logout</a>			
			</header>
		</c:otherwise>
	</c:choose>
	<br><br>
	<h3>Registered authors:</h3>
	<c:forEach items="${users}" var="user" >
			<li><a href="author/${user.nick}">${user.nick}</a></li>
	</c:forEach>
		
	</body>
</html>