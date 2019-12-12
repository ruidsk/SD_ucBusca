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


<h2 style="text-align: center">Palavras mais pesquisadas</h2>

<p style="text-align: center;white-space: pre-wrap;">
    ${heyBean.tabelaPalavras()}
</p>


</body>
</html>