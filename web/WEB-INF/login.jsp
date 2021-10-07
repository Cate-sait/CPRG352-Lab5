
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="POST" action="login">
            <label>Username: </label>
            <input type="text" name="username" value="${user.username}">
            <br>
            <lable>Password: </lable>
            <input type="password" name="password" value="${user.password}">
            <br>
            <input type="submit" value="Log in">
        </form>
        <c:choose>
            <c:when test="${Entry=='empty'}">
                <p> Invalid login. Please enter username and password.</p>
            </c:when>
            <c:when test="${Entry=='invalidLogin'}">
                <p> Invalid login.</p>
            </c:when>
            <c:when test="${Entry=='logout'}">
                <p>You have successfully logged out.</p>
            </c:when>
        </c:choose>     
    </body>
</html>
