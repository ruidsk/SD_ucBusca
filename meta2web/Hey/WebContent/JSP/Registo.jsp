<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title></title>
    <link href="${pageContext.request.contextPath}/CSS/Login2.css" rel="stylesheet" type="text/css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
</head>
<body>

<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="../index.jsp">Home</a></li>
        </ul>
    </nav>
</header>
<div class="login-form">
    <form action="Registo" method="post">
        <h1>Registo</h1>

        <div class="txtb">
            USERNAME:
            <input name="username" type="text">
        </div>

        <div class="txtb">
            PASSWORD:
            <input name="password" type="password">
        </div>


        <input type="submit" class="logbtn" value="Registo">
    </form>
    <div class="txtb" style="align-items: center;">
        <s:form action="associateface" method="post">
            <input type="image" src="${pageContext.request.contextPath}/Assets/buton.png" alt="Submit">
        </s:form>
    </div>
    <div class="bottom-text">
        Já tens conta? <a href="Login.jsp">Login</a>
    </div>

</div>




<script type="text/javascript">
    $(".txtb input").on("focus",function(){
        $(this).addClass("focus");
    });

    $(".txtb input").on("blur",function(){
        if($(this).val() == "")
            $(this).removeClass("focus");
    });

</script>


</body>
</html>

