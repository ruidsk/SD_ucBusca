<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JS/traduzir.js"></script>

<head>
    <title>UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">

</head>

<body>

<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
            <li id="sign_in"><a href="${pageContext.request.contextPath}/JSP/Registo.jsp">Registo</a></li>
            <li id="login"><a href="${pageContext.request.contextPath}/JSP/Login.jsp">Login</a></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="${pageContext.request.contextPath}/Assets/Logo.png" /></a>
</div>

<c:choose>
    <c:when test="${session.get(\" ERROR_LOG\") !=null}">
        <p style="color:red;text-align:center;">${session.ERROR_LOG}</p>
        <c:set target="${session}" property="ERROR_LOG" value="${null}" />
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

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

</body>
</html>

