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
<h1>Answer question:</h1>
<form:form modelAttribute="answer">
    <label>${question}</label>
    <div class="form-group">
        <form:input id="text" path="answer" type="password" htmlEscape="true"/>
    </div>
    <div class="form-group">
        <input type="submit" id="answer_button" value="Answer"/>
    </div>
</form:form>
<hr>
<ul>
    <li><a href="/home">login</a></li>
</ul>
</body>
</html>
