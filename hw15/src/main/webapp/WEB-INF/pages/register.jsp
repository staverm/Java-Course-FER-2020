<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
		
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
		</c:when>
		<c:otherwise>
			<header>
				<h3>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</h3>
				<a href="/blog/servleti/logout">Logout</a>			
			</header>
		</c:otherwise>
		</c:choose>
		<h1>
			Registration
		</h1>

		<form action="register" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">First Name</span><input type="text" name="firstName" value='<c:out value="${entry.firstName}"/>' size="20">
		 </div>
		 <c:if test="${entry.hasError('firstName')}">
		 <div class="greska"><c:out value="${entry.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last Name</span><input type="text" name="lastName" value='<c:out value="${entry.lastName}"/>' size="20">
		 </div>
		 <c:if test="${entry.hasError('lastName')}">
		 <div class="greska"><c:out value="${entry.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${entry.email}"/>' size="50">
		 </div>
		 <c:if test="${entry.hasError('email')}">
		 <div class="greska"><c:out value="${entry.getError('email')}"/></div>
		 </c:if>
		</div>
		
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
		  <input type="submit" name="metoda" value="Store">
		  <input type="submit" name="metoda" value="Cancel">
		</div>
		
		</form>

	</body>
</html>