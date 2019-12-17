<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    var websocket = null;
    window.onload = function() {
        connect('ws://' + window.location.host + '/hey/ws');

    }
    function connect(host) { // connect to the host websocket
        if ('WebSocket' in window)
            websocket = new WebSocket(host);
        else if ('MozWebSocket' in window)
            websocket = new MozWebSocket(host);
        else {
            writeToHistory('Get a real browser which supports WebSocket.');
            return;
        }
        websocket.onopen    = onOpen; // set the event listeners below
        websocket.onclose   = onClose;
        websocket.onmessage = onMessage;
        websocket.onerror   = onError;
    }
    function onOpen(event) {
        writeToHistory('Connected to ' + window.location.host + '.');
        writeToHistory('Notificações instantâneas');


    }

    function onClose(event) {
        writeToHistory('WebSocket closed.');

    }

    function onMessage(message) { // print the received message
        writeToHistory(message.data);
    }

    function onError(event) {
        writeToHistory('WebSocket error (' + event.data + ').');

    }

    function doSend() {

    }
    function writeToHistory(text) {
        var history = document.getElementById('history');
        var line = document.createElement('p');
        line.style.wordWrap = 'break-word';
        line.innerHTML = text;
        history.appendChild(line);
        history.scrollTop = history.scrollHeight;
    }
</script>
<html>
<head>
    <title>busca.UcBusca</title>
    <link href="${pageContext.request.contextPath}/CSS/index.css" rel="stylesheet" type="text/css">
</head>

<body>
<c:choose>
    <c:when test="${session.loggedin == true}">


<div id="container"><div id="history"></div></div>
<header>
    <nav>
        <ul id="nav_bar">
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/menuAdmin.jsp">Home</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/pesquisaSite.jsp">Sites</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/addAdmin.jsp">Add admin</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/indexUrl.jsp">Index urls</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/indexUrlRec.jsp">Index iterativo</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/historico.jsp">Histórico</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/palavrasPesquisadas.jsp">Mais pesquisadas</a></li>
            <li class="nav-links"><a href=<s:url action="facelogin"/>>Ligar ao facebook</a></li>
            <li class="nav-links"><a href=<s:url action="showOnline"/>>Mostar user online</a></li>
            <li class="nav-links"><a href="${pageContext.request.contextPath}/JSP/listaLigacoes.jsp">Lista de ligações</a></li>
            <li id="sign_in">
                <form action="logout" method="post"><button type="submit">Sair</button></form>
            </li>
        </ul>
    </nav>
</header>


<h1>Utilizadores Online</h1>
        <div style="align-items: center;">

            <table>

                <c:forEach var = "i" items="${heyBean.showOnline}">

                    <tr>
                        <c:out value = "${i.key}" /> <br>
                    </tr>

                </c:forEach>

            </table>
        </div>




</c:when>
<c:otherwise>
    <p>Login necess�rio.</p>
</c:otherwise>
</c:choose>
</body>
</html>