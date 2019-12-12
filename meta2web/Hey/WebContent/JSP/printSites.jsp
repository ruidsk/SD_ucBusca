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
            <li class="nav-links"><a href="menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="pesquisaSite.jsp">Sites</a></li>
            <li class="nav-links"><a href="addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="indexUrlRec.jsp">Index iterativo</a></li>
            <li class="nav-links"><a href="historico.jsp">Histórico</a></li>
            <li class="nav-links"><a href="palavrasPesquisadas.jsp">Mais pesquisadas</a></li>
            <li class="nav-links"><a href="listaLigacoes.jsp">Lista de ligações</a></li>
            <li id="sign_in">
                <form action="logout" method="post"><button type="submit">Sair</button></form>
            </li>
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