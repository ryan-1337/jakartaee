<%--
  Created by IntelliJ IDEA.
  User: ryan
  Date: 23/06/2023
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form class="login" action="/authServlet" method="post">
    <input type="hidden" name="action" value="login">
    <div class="login__field">
        <i class="login__icon fas fa-user"></i>
        <input type="text" class="login__input" placeholder="User name / Email" id="username"
               name="username" required>
    </div>
    <div class="login__field">
        <i class="login__icon fas fa-lock"></i>
        <input type="password" class="login__input" placeholder="Password" id="password" name="password"
               required>
    </div>
    <button class="button login__submit">
        <span class="button__text">Log In Now</span>
        <i class="button__icon fas fa-chevron-right"></i>
    </button>
</form>
<form class="ca" action="/connectedServlet" method="GET">
    <button class="button loginsubmit" style="margin-top: 0px;">
        <span class="buttontext">Create an Account</span>
        <i class="button__icon fas fa-chevron-right"></i>
    </button>
    <input type="hidden" name="action" value="registerAccount">
</form>
</body>
</html>
