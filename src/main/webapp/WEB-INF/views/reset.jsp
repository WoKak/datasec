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
<h1>Type your login:</h1>
<form:form modelAttribute="userToReset">
    <div class="form-group">
        <form:input id="text" path="login" type="text" htmlEscape="true"/>
    </div>
    <div class="form-group">
        <input type="submit" id="reset_button" value="Reset"/>
    </div>
</form:form>
<hr>
<ul>
    <li><a href="/login">login</a></li>
</ul>
</body>
</html>
