<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UcBusca</title>
    <link href="../CSS/historico.css" rel="stylesheet" type="text/css">
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


<div class="mostraCentrado" style="text-align: center;">

<h2>Histórico de pesquisas</h2>

<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Procurar no histórico.." title="Type in a name">

<ul id="myUL">
    <li><a href="#">Site1</a></li>
    <li><a href="#">Palavras</a></li>

    <li><a href="#">Palavras2</a></li>
    <li><a href="#">Site2</a></li>
</ul>

</div>

<script>
    function myFunction() {
        var input, filter, ul, li, a, i, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        ul = document.getElementById("myUL");
        li = ul.getElementsByTagName("li");
        for (i = 0; i < li.length; i++) {
            a = li[i].getElementsByTagName("a")[0];
            txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                li[i].style.display = "";
            } else {
                li[i].style.display = "none";
            }
        }
    }
</script>

</body>
</html>
