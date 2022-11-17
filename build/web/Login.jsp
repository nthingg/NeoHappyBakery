<%-- 
    Document   : Login.jsp
    Created on : Sep 13, 2022, 1:56:59 PM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/x-icon" href="img/websitelogo.jpg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="cute-alert-master/style.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <c:import url="AuthRedirect.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="AuthLogined.jsp"/>
            </c:otherwise>
        </c:choose>
        <c:import url="Navigation.jsp"/>

        <div class="mapping grad">
            <p class="breadcrums"><a href="HomePage.jsp">Home</a>/<a href="">Login</a></p>
            <h1 class="mapping-heading">Recipe</h1>
        </div>
        <div class="login-container">
            <div class="contaner">
                <div class="login-form">
                    <div class="login-header">
                        <h3 class="fnt-30 mg-bot-20 fnt-wei-15">Welcome to Happy Bakery</h3>
                        <p class="fnt-20">Login with your account</p>
                    </div>
                    <form action="MainController" method="post">
                        <div class="login-body">
                            <div class="form-group">
                                <label for="email" class="fnt-wei-bold">Email:</label>
                                <input id="email" name="email" type="text" placeholder="Your email...">
                            </div>
                            <div class="form-group">
                                <label for="pass" class="fnt-wei-bold">Password:</label>
                                <input id="pass" name="password" type="password" placeholder="Your password">
                            </div>
                            <div class="save-login">
                                <input id="save-login-checkbox" type="checkbox" value="saveLogin" name="saveLogin"> 
                                <label for="save-login-checkbox">Save login</label>
                            </div>
                            <input class="submit-btn bg-orange mgn-top-15 mgn-bot-20" type="submit" value="Login" name="action">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="cute-alert-master/cute-alert.js"></script>

        <script>
            <c:choose>
                <c:when test="${requestScope.errorLogin != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Login fail!",
                message: "Wrong email or password!",
                buttonText: "Confirm"
            });
                </c:when>
                <c:when test="${requestScope.loginSuccess != null}">
            cuteAlert({
                img: "img/success.svg",
                type: "success",
                title: "Login successfull!",
                message: "Enjoy your time at Happy Bakery!",
                buttonText: "OK"
            }).then((e) => {
               if (e == "ok") {
                   window.location = 'HomePage.jsp';
               } else {
                   
               }
            });
                </c:when>
            </c:choose>
        </script>

        <c:import url="Footer.jsp"/>
    </body>
</html>
