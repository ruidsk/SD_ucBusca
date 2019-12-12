<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>busca.UcBusca</title>
    <link href="../CSS/historico.css" rel="stylesheet" type="text/css">
</head>

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


    <div class="mostraCentrado" style="text-align: center;">

        <h2>Histórico de pesquisas</h2>

        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Procurar no histórico.."
            title="Type in a name">

        <p style="text-align: center;white-space: pre-wrap;">
            <c:forTokens items="${heyBean.mostraConsultas()}" delims="" var="name">
                <c:out value="${name}" />
                <p style="text-align: center;white-space: pre-wrap;">
            </c:forTokens>
        </p>

</body>

</html>