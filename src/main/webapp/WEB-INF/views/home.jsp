<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/styles/list.css" />">
</head>
<body>
    <h3>Welcome ${login}, what do you want to do?</h3>
    <hr>
    <ul>
        <li><a href="/addCode">add new code</a></li>
        <li><a href="/snippets">see snippets</a></li>
        <li><a href="/change">change password</a></li>
        <li><a href="/question">define question</a></li>
        <li><a href="/logout">logout</a></li>
    </ul>
</body>
</html>
