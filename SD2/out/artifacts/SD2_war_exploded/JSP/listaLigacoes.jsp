<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UcBusca</title>
    <link href="../CSS/palavrasPesquisadas.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="usersOnline.jsp">Users online</a></li>
            <li class="nav-links"><a href="historico.jsp">Histórico de pesquisas</a></li>
            <li class="nav-links"><a href="palavrasPesquisadas.jsp">Palavras mais pesquisadas</a></li>
            <li class="nav-links"><a href="listaLigacoes.jsp">Consultar lista de ligações</a></li>
            <li id="sign_in"><a href="../index.jsp">Sair</a></li>
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