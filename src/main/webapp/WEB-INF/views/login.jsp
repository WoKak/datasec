<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
</head>
<body>
    <h1>Welcome, please login in order to get access to application:</h1>
    <form:form modelAttribute="userToLogin">
        <label>Login:</label>
        <div class="form-group">
            <form:input id="login" path="login" type="text" htmlEscape="true"/>
        </div>
        <label>Password:</label>
        <div>
            <form:input id="password" path="password" type="password" htmlEscape="true"/>
        </div>
        <div class="form-group">
            <input type="submit" id="login_button" value="Login"/>
        </div>
    </form:form>
    <hr>
    <ul>
        <a href="/register">register</a>
        <a href="/reset">reset</a>
    </ul>
</body>
</html>
