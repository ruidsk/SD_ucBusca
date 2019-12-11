<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>UcBusca</title>
	<link href="CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
	<nav>
		<ul id="nav_bar">
			<li class="nav-links"><a href="index.jsp">Home</a></li>
			<li class="nav-links"><a href="JSP/menuAdmin.jsp">Para testes: entrar Admin</a></li>
			<li class="nav-links"><a href="JSP/menuUser.jsp">Entrar User</a></li>
			<li id="sign_in"><a href="JSP/Registo.jsp">Registo</a></li>
			<li id="login"><a href="JSP/Login.jsp">Login</a></li>
		</ul>
	</nav>
</header>

<!-- IMG -->
<div class="ucBusca">
	<a href="#" id="ucBusca_logo"><img src="Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->
<form action="Pesquisa">
	<div class="form">

		<label for="form-search"></label>
		<input type="text" id="form-search" placeholder="Introduzir pesquisa">

	</div>

	<!-- BUTTONS -->
	<div class="buttons">
		<input type="submit" value="Search" id="uc_search">
	</div>
</form>

</body>
</html>