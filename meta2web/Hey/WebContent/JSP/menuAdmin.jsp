<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>busca.UcBusca</title>
    <link href="../CSS/index.css" rel="stylesheet" type="text/css">
</head>
<c:choose>
    <c:when test="${session.admin == false}">
        <s:action name="fail"></s:action>
    </c:when>
    <c:otherwise>
        <p>Welcome to admin menu, ${session.username}</p>
    </c:otherwise>
</c:choose>

<body>
    <header>
        <nav>
            <ul id="nav_bar">
                <li class="nav-links"><a href="menuAdmin.jsp">Home</a></li>
                <li class="nav-links"><a href="addAdmin.jsp">Add admin</a></li>
                <li class="nav-links"><a href="indexUrl.jsp">Index urls</a></li>
                <li class="nav-links"><a href="historico.jsp">Histórico de pesquisas</a></li>
                <li class="nav-links"><a href="palavrasPesquisadas.jsp">Palavras mais pesquisadas</a></li>
                <li class="nav-links"><a href="listaLigacoes.jsp">Consultar lista de ligações</a></li>
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


    <c:when test="${session.checkWords != null}">
        <p>Welcome, ${session.checkWords}. Say HEY to someone.</p>
    </c:when>

    <!-- FORM SEARCH -->
    <form action="Pesquisa">
        <div class="form">

            <label for="form-search"></label>
            <input type="text" id="form-search" placeholder="Introduzir pesquisa">

        </div>

        <!-- BUTTONS -->
        <div class="buttons">
            <input type="submit" value="Procura palavras" id="uc_search">
            <input type="submit" value="Procura link">
        </div>
    </form>

</body>

</html>