<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="http://localhost:8080/hey/CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="fb-root"></div>
<script async defer crossorigin="anonymous" src="https://connect.facebook.net/pt_PT/sdk.js#xfbml=1&version=v5.0&appId=574377749961850&autoLogAppEvents=1"></script>
<header><p>Welcome, ${session.username}</p></header>
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
    <a href="#" id="ucBusca_logo"><img src="http://localhost:8080/hey/Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->

<p>
    <c:forTokens items = "${session.checkWords}" delims = "|XXX|" var = "name">
        <c:out value = "${name}"/> <p>
    </c:forTokens>
<div class="fb-share-button" data-href="http://localhost:8080/hey/JSP/mostraSites.jsp" data-layout="button" data-size="large"><a target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Flocalhost%3A8080%2Fhey%2FJSP%2FmostraSites.jsp&amp;src=sdkpreparse" class="fb-xfbml-parse-ignore">Partilhar</a></div>

</p>

</body>
</html>
