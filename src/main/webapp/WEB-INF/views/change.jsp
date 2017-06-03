<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>datasec</title>
    <script type="text/javascript" src="<c:url value="/resources/js/strength.js" />"></script>
</head>
<body>
    <h2>Type required data in order to change password (remember about requirements):</h2>
    <form:form modelAttribute="passwordToChange">
        <label>Old:</label>
        <div class="form-group">
            <form:input id="old" path="old" type="password" htmlEscape="true"/>
        </div>
        <label>New:</label>
        <div>
            <form:input id="password" path="password" type="password" onkeyup="counter();"/>
        </div>
        <label>Repeat:</label>
        <div>
            <form:input id="repeated" path="repeated" type="password" htmlEscape="true"/>
        </div>
        <div class="form-group">
            <input type="submit" id="change_button" value="Change"/>
        </div>
    </form:form>
    <hr>
    <ul>
        <a href="/home">home</a>
    </ul>
</body>
</html>
