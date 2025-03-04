<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>

<header><p>Welcome, ${session.username}

</p></header>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/menuUser.jsp">Home</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/printSitesUser.jsp">Sites</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/historicoUser.jsp">Histórico de pesquisas</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/listaLigacoesUser.jsp">Consultar lista de ligações</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/palavrasPesquisadasUser.jsp">Palavras mais pesquisadas</a></li>
            <li id="sign_in"><form action="logout" method="post" ><button type="submit">Sair</button></form></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="${pageContext.request.contextPath}/Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->
<form action="checkWordsUser" method="post">
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
