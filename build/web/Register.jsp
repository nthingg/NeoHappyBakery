<%-- 
    Document   : Register
    Created on : Sep 13, 2022, 1:59:17 PM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/x-icon" href="img/websitelogo.jpg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="cute-alert-master/style.css"/>
    </head>
    <body id="register">
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
            <p class="breadcrums"><a href="HomePage.jsp">Home</a>/<a href="">Register</a></p>
            <h1 class="mapping-heading">Recipe</h1>
        </div>
        <div class="register-container">
            <div class="contaner">
                <div class="register-form">
                    <div class="login-header">
                        <h3 class="fnt-30 mg-bot-20 fnt-wei-15">Create new account</h3>
                        <p class="fnt-20">Fill in the form to register a Happy Bakery's account
                        </p>
                    </div>
                    <form action="MainController" method="post" enctype="multipart/form-data">
                        <div class="login-body">
                            <div class="radio-group">
                                <label for="pass" class="fnt-wei-bold">You are<span class="cl-red">*</span>:</label>
                                <div class="radio-opt">
                                    <input type="radio" id="type" name="usertype" value="user" checked>
                                    <label class="mgn-right-15" for="type">Baker</label>
                                    <input type="radio" id="type" name="usertype" value="store">
                                    <label for="type">Store</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="fullname" class="fnt-wei-bold">Your name<span class="cl-red">*</span>:</label>
                                <input id="fullname" name="fullname" type="text" placeholder="Your full name" required>
                            </div>
                            <div class="form-group">
                                <label for="mail" class="fnt-wei-bold">Email<span class="cl-red">*</span>:</label>
                                <input id="mail" name="mail" type="text" placeholder="Your email" required>
                            </div>
                            <div class="form-group">
                                <label for="pass" class="fnt-wei-bold">Password<span class="cl-red">*</span>:</label>
                                <input id="pass" name="pass" type="password" placeholder="Your password" required>
                            </div>
                            <div class="radio-group">
                                <label for="pass" class="fnt-wei-bold">Gender<span class="cl-red">*</span>:</label>
                                <div class="radio-opt">
                                    <input type="radio" id="gend" name="gender" value="male" checked>
                                    <label class="mgn-right-15" for="gend">Male</label>
                                    <input type="radio" id="gend" name="gender" value="female">
                                    <label for="gend">Female</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="fnt-wei-bold">Phone number:</label>
                                <input id="phone" name="phone" type="text" placeholder="Nhập số điện thoại">
                            </div>
                            <div class="form-group">
                                <label for="date" class="fnt-wei-bold">Birth day:</label>
                                <input id="date" name="date" type="date" placeholder="Your birth day">
                            </div>
                            <div class="form-group">
                                <label for="address" class="fnt-wei-bold">Address:</label>
                                <input id="address" name="address" type="text" placeholder="Your address">
                            </div>
                            <div>
                                <label for="img" class="fnt-wei-bold">Image:</label>
                                <input id="img" name="img" type="file" accept="image/*" placeholder="Your avatar">
                            </div>

                            <input class="submit-btn bg-orange mgn-top-15 mgn-bot-20"  type="submit" value="Register" name="action">
                            <p class="line-h"> Already have an account? <a href="Login.jsp">Login</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <<script src="cute-alert-master/cute-alert.js"></script>
        <script>

            <c:choose>
                <c:when test="${requestScope.registerSuccess != null}">
            cuteAlert({
                img: "img/success.svg",
                type: "success-q",
                title: "Register successfull!",
                message: "Now you can use the account to login",
                confirmText: "To login",
                cancelText: "OK"
            }).then((e) => {
               if (e == "confirm") {
                   window.location = 'Login.jsp';
               } else {
                   
               }
            });
                </c:when>

                <c:when test="${requestScope.registerFail != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Register fail!",
                message: "Check your information again",
                buttonText: "Confirm"
            });
                </c:when>

                <c:when test="${requestScope.emailDupli != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Register fail!",
                message: "Your inputted email is already exist!",
                buttonText: "Confirm"
            });
                </c:when>

                <c:when test="${requestScope.emailError != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Register fail!",
                message: "Your inputted email is in wrong format!",
                buttonText: "Confirm"
            });
                </c:when>

                <c:when test="${requestScope.phoneError != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Register fail!",
                message: "Your inputted email is in wrong format!",
                buttonText: "Confirm"
            });
                </c:when>
            </c:choose>

        </script>
        <c:import url="Footer.jsp"/>
    </body>
</html>
