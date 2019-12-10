<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="../CSS/Login.css" rel="stylesheet" type="text/css">
</head>
<body>

<div id="fb-root"></div>
<script async defer crossorigin="anonymous"
        src="https://connect.facebook.net/pt_PT/sdk.js#xfbml=1&version=v5.0"></script>

<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="../index.jsp">Home</a></li>
           </ul>
    </nav>
</header>

    <c:when test="${session.ERROR_LOG != null}">
        <p>${session.ERROR_LOG}</p>
    </c:when>

<form action="login" class="login-form" method="post">
    <h1>Login</h1>

    <div class="txtb">
        <input name="username" type="text">
        <span data-placeholder="Username"></span>
    </div>

    <div class="txtb">
        <input name="password" type="password">
        <span data-placeholder="Password"></span>
    </div>

    <input type="submit" class="logbtn" value="Login">


    <div class="bottom-text">
        Não tens conta? <a href="Registo.jsp">Registo</a>
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
