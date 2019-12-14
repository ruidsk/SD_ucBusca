<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/hey/JS/traduzir.js"></script>

<head>
    <title>UcBusca</title>
    <link href="http://localhost:8080/hey/CSS/index.css" rel="stylesheet" type="text/css">

</head>

<body>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="http://localhost:8080/hey/index.jsp">Home</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/menuAdmin.jsp">Para testes: entrar Admin</a></li>
            <li class="nav-links"><a href="http://localhost:8080/hey/JSP/menuUser.jsp">Entrar User</a></li>
            <li id="sign_in"><a href="http://localhost:8080/hey/JSP/Registo.jsp">Registo</a></li>
            <li id="login"><a href="http://localhost:8080/hey/JSP/Login.jsp">Login</a></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="http://localhost:8080/hey/Assets/Logo.png" /></a>
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

