<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
    <script type="text/javascript" src="<c:url value="/resources/js/strength.js" />"></script>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/styles/list.css" />">
</head>
<body>
    <h1>Insert data for registration purposes:</h1>
    <h5>Login must be between 5 and 8 characters (only non-capital letters).</h5>
    <h5>Password must be between 8 and 20 characters (at least 1 capital letter, special character e.g "@", number and non-capital letter).</h5>
    <form:form modelAttribute="userToRegister">
        <label>Login:</label>
        <div class="form-group">
            <form:input id="login" path="login" type="text" htmlEscape="true"/>
        </div>
        <label>Password:</label>
        <div>
            <form:input id="password" path="password" type="password" onkeyup="counter();"/>
        </div>
        <label>Repeat password:</label>
        <div>
            <form:input id="repeatedPassword" path="repeatedPassword" type="password" htmlEscape="true"/>
        </div>
        <label>Define question:</label>
        <div>
            <form:input id="question" path="question" type="text" htmlEscape="true"/>
        </div>
        <label>Define answer:</label>
        <div>
            <form:input id="answer" path="answer" type="password" htmlEscape="true"/>
        </div>
        <div class="form-group">
            <input type="submit" id="register_button" value="Register"/>
        </div>
    </form:form>
    <hr>
    <ul>
        <a href="/login">login</a>
    </ul>
</body>
</html>
