<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JS/traduzir.js"></script>

<html>
<head>
    <title>busca.UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:choose>
    <c:when test="${session.loggedin == true}">


<header><p>Welcome, ${session.username}</p></header>
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
    <a href="#" id="ucBusca_logo"><img src="../Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->

<table id="tbNames" border="1" width="100%" style="text-align: center;">
    <tr>
        <th>Site</th>
        <th>Acessos</th>
        <th>Titulo</th>
        <th>Descrição</th>
        <th>Língua</th>
        <th>Traduzir</th>
    </tr>
    <!-- <tr>
           <td>
               put value here
           </td>
       </tr>-->
</table>

<textarea id ="text" style="color:white;white-space: pre-wrap;border-top: 400px;" disabled>
    ${session.checkWords}
</textarea>

<script>
    load();
    load2();
</script>
</c:when>
<c:otherwise>
    <p>Login necess�rio.</p>
</c:otherwise>
</c:choose>
</body>
</html>

