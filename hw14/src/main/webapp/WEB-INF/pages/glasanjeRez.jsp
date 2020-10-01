<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
table.reztd {
	text-align: center;
}
</style>
<title>Glasanje rezultati</title>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>

	<table border="1" class="rez">
		<thead>
			<tr>
				<th>${poll.title}</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${options}" var="option">
				<tr>
					<td>${option.optionTitle}</td>
					<td>${option.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach items="${winners}" var="option">
			<li><a href="${option.optionLink}" target="_blank">${option.optionTitle}</a></li>
		</c:forEach>
	</ul>
</body>
</html>