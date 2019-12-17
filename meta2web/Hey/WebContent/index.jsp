<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
	<title>UcBusca</title>
	<link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">

</head>

<body>
	<header>
		<nav>
			<ul id="nav_bar">
				<li class="nav-links"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
				<li id="sign_in"><a href="${pageContext.request.contextPath}/JSP/Registo.jsp">Registo</a></li>
				<li id="login"><a href="${pageContext.request.contextPath}/JSP/Login.jsp">Login</a></li>
			</ul>
		</nav>
	</header>

	<!-- IMG -->
	<div class="ucBusca">
		<a href="#" id="ucBusca_logo"><img src="${pageContext.request.contextPath}/Assets/Logo.png" /></a>
	</div>

	<c:choose>
		<c:when test="${session.get(\" ERROR_LOG\") !=null}">
			<p style="color:red;text-align:center;">${session.ERROR_LOG}</p>
			<c:set target="${session}" property="ERROR_LOG" value="${null}" />
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<!-- FORM SEARCH -->
	<form action="checkWordsGuest" method="post">
		<div class="form">

			<label for="form-search"></label>
			<input type="text" name="palavras" id="form-search" placeholder="Introduzir pesquisa">

		</div>

		<!-- BUTTONS -->
		<div class="buttons">
			<input type="submit" value="Procura palavras" id="uc_search">
		</div>
	</form>

</body>

</html>