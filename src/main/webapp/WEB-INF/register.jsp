<%--
  Created by IntelliJ IDEA.
  User: ryan
  Date: 23/06/2023
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form class="register" action="/connectedServlet" method="post">
    <input type="hidden" name="action" value="register">
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
        <span class="button__text">Register Now</span>
        <i class="button__icon fas fa-chevron-right"></i>
    </button>
</form>
</body>
</html>
