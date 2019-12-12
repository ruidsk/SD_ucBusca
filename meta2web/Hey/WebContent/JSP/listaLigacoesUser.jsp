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


<h1>Lista de ligações</h1>

<table align="center">
    <tr>
        <th>Site</th>
        <th>Ligações</th>
    </tr>
    <tr>
        <td>uc.pt</td>
        <td>80</td>
    </tr>
    <tr>
        <td>sapo.pt</td>
        <td>60</td>
    </tr>
</table>


</body>
</html>