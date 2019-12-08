<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UcBusca</title>
    <link href="../CSS/addAdmin.css" rel="stylesheet" type="text/css">
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

<!-- FORM -->
<form action="admin" class="login-form">
    <h1>Selecione o utilizador a atribuir título de administrador:</h1>

    <div class="txtb">
    <input list="users" id="user" name="user" />

    <datalist id="users">
        <option value="Rui">
        <option value="David">
        <option value="Jesus">
    </datalist>
    </div>

    <div class="txtb">
    <input type="submit" value="Alterar">
    </div>
</form>

<script type="text/javascript">
    $(".txtb input").on("focus", function () {
        $(this).addClass("focus");
    });

    $(".txtb input").on("blur", function () {
        if ($(this).val() == "")
            $(this).removeClass("focus");
    });

</script>

</body>
</html>
