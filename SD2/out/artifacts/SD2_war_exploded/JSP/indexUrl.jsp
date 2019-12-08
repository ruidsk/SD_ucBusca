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

<!-- IMG -->
<div class="ucBusca">
    <a href="../index.jsp" id="ucBusca_logo"><img src="../Assets/Logo.png"/></a>
</div>

<!-- FORM SEARCH -->
<form action="Indexar">
<div class="form">
    <form>
        <label for="form-search"></label>
        <input type="text" id="form-search" placeholder="Introduzir url a indexar">
    </form>
</div>

<!-- BUTTONS -->
<div class="buttons">
    <input type="submit" value="Indexar">
</div>
</form>

</body>
</html>