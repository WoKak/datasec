<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/styles/snippets.css" />">
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/styles/list.css" />">
</head>
<body>
    <h1>Snippets:</h1>
    <c:forEach var="code" items="${codes}" >
        <h3>code:</h3>
        <p>${code.text}</p>
        <br>
        <br>
    </c:forEach>
    <hr>
    <ul>
        <li><a href="/home">home</a></li>
        <li><a href="/addCode">add new code</a></li>
        <li><a href="/logout">logout</a></li>
    </ul>
</body>
</html>
