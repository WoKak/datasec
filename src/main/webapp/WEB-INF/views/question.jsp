<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
</head>
<body>
<h2>Define new question for password reset:</h2>
<form:form modelAttribute="question">
    <label>Question:</label>
    <div class="form-group">
        <form:input id="question" path="question" type="text" htmlEscape="true"/>
    </div>
    <label>Answer:</label>
    <div>
        <form:input id="answer" path="answer" type="password"/>
    </div>
    <label>Previous answer:</label>
    <div>
        <form:input id="prev" path="previous" type="password"/>
    </div>
    <div class="form-group">
        <input type="submit" id="define_button" value="Define"/>
    </div>
</form:form>
<hr>
<ul>
    <a href="/home">home</a>
</ul>
</body>
</html>
