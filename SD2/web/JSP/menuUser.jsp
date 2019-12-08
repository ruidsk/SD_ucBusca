<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UcBusca</title>
    <link href="../CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="menuUser.jsp">Home</a></li>
            <li class="nav-links"><a href="historico.jsp">Histórico de pesquisas</a></li>
            <li class="nav-links"><a href="listaLigacoes.jsp">Consultar lista de ligações</a></li>
            <li class="nav-links"><a href="palavrasPesquisadas.jsp">Palavras mais pesquisadas</a></li>
            <li id="sign_in"><a href="../index.jsp">Sair</a></li>
        </ul>
    </nav>
</header>

<!-- IMG -->
<div class="ucBusca">
    <a href="#" id="ucBusca_logo"><img src="../Assets/Logo.png"/></a>
</div>

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

