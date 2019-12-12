<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="../CSS/palavrasPesquisadas.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuUser.jsp">Home</a></li>
            <li class="nav-links"><a href="historicoUser.jsp">Histórico de pesquisas</a></li>
            <li class="nav-links"><a href="listaLigacoesUser.jsp">Consultar lista de ligações</a></li>
            <li class="nav-links"><a href="palavrasPesquisadasUser.jsp">Palavras mais pesquisadas</a></li>
            <li id="sign_in"><form action="logout" method="post" ><button type="submit">Sair</button></form></li>
        </ul>
    </nav>
</header>


<h1>Palavras mais pesquisadas</h1>

<table align="center">
    <tr>
        <th>Palavra</th>
        <th>Número de pesquisas</th>
    </tr>
    /*ciclos a fazer estas tabelas é simples tá facil é pro 20*/
    <tr>
        <td>David</td>
        <td>10</td>
    </tr>
    <tr>
        <td>Rui</td>
        <td>8</td>
    </tr>
</table>


</body>
</html>