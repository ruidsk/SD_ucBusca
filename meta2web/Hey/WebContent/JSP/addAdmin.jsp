<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="http://localhost:8080/hey/CSS/addAdmin.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:choose>
    <c:when test="${session.loggedin == true}">


<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="pesquisaSite.jsp">Sites</a></li>
            <li class="nav-links"><a href="addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="indexUrlRec.jsp">Index iterativo</a></li>
            <li class="nav-links"><a href="historico.jsp">Histórico</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href="palavrasPesquisadas.jsp">Mais pesquisadas</a></li>
            <li class="nav-links"><a href="listaLigacoes.jsp">Lista de ligações</a></li>
            <li id="sign_in">
                <form action="logout" method="post"><button type="submit">Sair</button></form>
            </li>
        </ul>
    </nav>
</header>

<!-- FORM -->
<h2 style="text-align: center">Adicionar um novo administrador</h2>

<form action="adicionarAdmin" method="post">
    <div class="form">

        <label for="form-search"></label>
        <input type="text" name="palavras" id="form-search" placeholder="Introduzir username">

    </div>

    <!-- BUTTONS -->
    <div class="buttons" style="text-align: center">
        <input type="submit" value="Atribuir" id="uc_search">
    </div>
</form>


<!-- FORM SEARCH -->

<p style="text-align: center">
    ${session.adicionarAdmin}
</p>
</c:when>
<c:otherwise>
    <p>Login necess�rio.</p>
</c:otherwise>
</c:choose>
</body>
</html>
