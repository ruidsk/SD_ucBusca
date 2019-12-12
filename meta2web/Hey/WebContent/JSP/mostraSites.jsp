<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="http://localhost:8080/hey/CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<header><p>Welcome, ${session.username}</p></header>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuUser.jsp">Home</a></li>
            <li id="sign_in"><a href="http://localhost:8080/hey/index.jsp">Sair</a></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="http://localhost:8080/hey/Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->

<p>
    <c:forTokens items = "${session.checkWords}" delims = "|XXX|" var = "name">
         <c:out value = "${name}"/> <br>
    </c:forTokens>

</p>

</body>
</html>

