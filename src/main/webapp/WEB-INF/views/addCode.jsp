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
    <h1>Add snippet:</h1>
    <form:form modelAttribute="code">
        <div class="form-group">
            <form:input id="text" path="text" type="text" htmlEscape="true"/>
        </div>
        <div class="form-group">
            <input type="submit" id="ok_button" value="OK"/>
        </div>
    </form:form>
    <hr>
    <ul>
        <li><a href="/home">home</a></li>
        <li><a href="/snippets">snippets</a></li>
        <li><a href="/logout">logout</a></li>
    </ul>
</body>
</html>
