<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>busca.UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">
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
        <a href="#" id="ucBusca_logo"><img src="${pageContext.request.contextPath}/Assets/Logo.png" /></a>
    </div>


    <!-- FORM SEARCH -->
    <form action="checkWords" method="post">
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
