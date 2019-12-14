<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<html>
<head>
    <title>Login</title>
    <link href="http://localhost:8080/hey/CSS/Login.css" rel="stylesheet" type="text/css">
</head>
<body>

<div id="fb-root"></div>
<script async defer crossorigin="anonymous"
        src="https://connect.facebook.net/pt_PT/sdk.js#xfbml=1&version=v5.0"></script>

<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="http://localhost:8080/hey/index.jsp">Home</a></li>
           </ul>
    </nav>
</header>
    <c:choose>
        <c:when test="${session.get(\"ERROR_LOG\") != null}">
            <p style="color:red;text-align:center;" >${session.ERROR_LOG}</p>
            <c:set target="${session}" property="ERROR_LOG" value="${null}"/>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>


<div class="login-form">
    <form action="login" method="post">
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

    </form>

    <div class="txtb" style="align-items: center;">
        <s:form action="facelogin" method="post">
            <input type="image" src="http://localhost:8080/hey/Assets/buton.png" alt="Submit">
        </s:form>
    </div>

    <div class="bottom-text">
        Não tens conta? <a href="http://localhost:8080/hey/JSP/Registo.jsp">Registo</a>
    </div>
</div>








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
