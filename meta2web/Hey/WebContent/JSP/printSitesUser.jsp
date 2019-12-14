<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="../CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuUser.jsp">Home</a></li>
            <li class="nav-links"><a href="printSitesUser.jsp">Sites</a></li>
            <li class="nav-links"><a href="historicoUser.jsp">Histórico de pesquisas</a></li>
            <li class="nav-links"><a href="listaLigacoesUser.jsp">Consultar lista de ligações</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href="palavrasPesquisadasUser.jsp">Palavras mais pesquisadas</a></li>
            <li id="sign_in"><form action="logout" method="post" ><button type="submit">Sair</button></form></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="../Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->

<p style="text-align: center;white-space: pre-wrap;">
    <c:forTokens items = "${session.pesquisarSite}" delims = "" var = "name">
        <c:out value = "${name}"/> <p style="text-align: center;white-space: pre-wrap;">
    </c:forTokens>

</p>

</body>
</html>