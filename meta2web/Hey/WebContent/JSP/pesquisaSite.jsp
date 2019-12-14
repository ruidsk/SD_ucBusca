<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>busca.UcBusca</title>
    <link href="http://localhost:8080/hey/CSS/index.css" rel="stylesheet" type="text/css">
</head>

<body>
<c:choose>
    <c:when test="${session.loggedin == true}">


<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/pesquisaSite.jsp">Sites</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/indexUrlRec.jsp">Index iterativo</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/historico.jsp">Histórico</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/palavrasPesquisadas.jsp">Mais pesquisadas</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href=<s:url action="showOnline"/>>Mostar user online</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/listaLigacoes.jsp">Lista de ligações</a></li>
            <li id="sign_in">
                <form action="logout" method="post"><button type="submit">Sair</button></form>
            </li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="../Assets/Logo.png" /></a>
</div>

<h2 style="text-align: center">Pesquisar sites que dão acesso a site</h2>

<!-- FORM SEARCH -->
<form action="pesquisarSite" method="post">
    <div class="form">

        <label for="form-search"></label>
        <input type="text" name="site" id="form-search" placeholder="Introduzir url a pesquisar">

    </div>

    <!-- BUTTONS -->
    <div class="buttons">
        <input type="submit" value="Procura urls" id="uc_search">
    </div>
</form>


</c:when>
<c:otherwise>
    <p>Login necess�rio.</p>
</c:otherwise>
</c:choose>
</body>

</html>