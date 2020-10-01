<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Entry</title>
		
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
			Blog entry
		</h1>
	
		<%-- form gets sent to the same URL used for it's request --%>
		<form method="post"> 
		
		<div>
		 <div>
		  <span class="formLabel">Title</span>
		  <textarea name="title"  rows="2" cols="30" maxlength="200"><c:out value="${entryForm.title}"/></textarea>
		 </div>
		 <c:if test="${entryForm.hasError('title')}">
		 <div class="greska"><c:out value="${entryForm.getError('title')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Text</span>
		  <textarea name="text"  rows="20" cols="100" maxlength="4096"><c:out value="${entryForm.text}"/></textarea>
		 </div>
		 <c:if test="${entryForm.hasError('text')}">
		 <div class="greska"><c:out value="${entryForm.getError('text')}"/></div>
		 </c:if>
		</div>



		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="hidden" name="form" value="blogEntryForm"/>
		  <input type="submit" name="metoda" value="Store">
		  <input type="submit" name="metoda" value="Cancel">
		</div>
		
		</form>

	</body>
</html>