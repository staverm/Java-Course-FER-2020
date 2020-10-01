<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Author: ${author.nick}</title>
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.small{
			font-size: 0.7em;
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
				<a href="../../logout">Logout</a>			
			</header>
		</c:otherwise>
	</c:choose>
	<a href="../${entry.creator.nick}">Back</a>
	<br><br>
	
	<h2>${entry.title }</h2>
	<p>${entry.text }</p>
	<h6>Created at: ${entry.createdAt }<br> Last modified at: ${entry.lastModifiedAt }<br>Author: ${entry.creator.nick} </h6>
	<c:choose>
	<c:when test="${author.nick.equals(sessionScope['current.user.nick'])}">
		<br>
		<a href="edit/${entry.id}">Edit blog entry</a>
	</c:when>
	</c:choose>
	<br>
	<c:choose>
	<c:when test="${entry.comments.size() == 0}">
		<h3>No comments </h3>
	</c:when>
	<c:otherwise>
		<h3>Comments:</h3>
		<c:forEach items="${entry.comments}" var="e" >
				<div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div>
		<br>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	<br>
	<form method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Message</span>
		  <textarea name="message"  rows="2" cols="30" maxlength="200"></textarea>
		 </div>
		 <c:if test="${entryForm.hasError('message')}">
		 <div class="greska"><c:out value="${entryForm.getError('message')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="hidden" name="form" value="addComment"/>
		   <input type="hidden" name="entryID" value="${entry.id }"/>
		    <input type="hidden" name="userNick" value="${sessionScope['current.user.nick'] }"/>
		  <input type="submit" name="addComment" value="Submit">
		</div>
		
		</form>
	
	</body>
</html>