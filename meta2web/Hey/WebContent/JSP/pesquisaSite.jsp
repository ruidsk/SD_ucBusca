<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>busca.UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">
</head>

<body>
<c:choose>
    <c:when test="${session.loggedin == true}">


<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/pesquisaSite.jsp">Sites</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/indexUrlRec.jsp">Index iterativo</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/historico.jsp">Histórico</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/palavrasPesquisadas.jsp">Mais pesquisadas</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href=<s:url action="showOnline"/>>Mostar user online</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/listaLigacoes.jsp">Lista de ligações</a></li>
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