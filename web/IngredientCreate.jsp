<%-- 
    Document   : IngredientCreate
    Created on : Oct 12, 2022, 3:23:32 PM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${sessionScope.user == null}">
        <c:import url="AuthRedirect.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="AuthLogined.jsp"/>
    </c:otherwise>
</c:choose>
<c:import url="Navigation.jsp"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/mycss.css">
        
    </head>
    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="">Home</a>/<a href="">Profile</a></p>
            <h1 class="mapping-heading">Create Ingredient</h1>
        </div>
        <div class="create-blog-container">
            <div class="create-recipe-form">
                <div class="login-header">
                    <h3 class="fnt-30 mg-bot-20 fnt-wei-15">Create Ingredient</h3>
                    <p class="fnt-20">Fill in all the information to create new ingredient</p>
                </div>
                <form action="MainController" method="post" enctype="multipart/form-data">
                    <div class="create-blog-body">
                        <div class="cate-recipe">
                            <label for="phone" class="fnt-wei-bold">Store:</label>
                            <p class="blog-detail-p">${param.storeName}</p>
                            <input type="hidden" value="${param.storeId}" name="storeId"> 
                        </div>
                        <div class="form-group-blog">
                            <label for="ingrename" class="fnt-wei-bold">Ingredient name<span class="cl-red">*</span>:</label>
                            <input id="ingrename" name="name" type="text" placeholder="Input ingredient name" required>
                        </div>
                        <div class="form-group-blog">
                            <label for="img" class="fnt-wei-bold">Image<span class="cl-red">*</span>:</label>
                            <input id="img" name="img" type="file" accept="image/*" placeholder="Choose image">
                        </div>
                        <div class="form-group-blog">
                            <label for="ingredientcate" class="fnt-wei-bold">Category<span class="cl-red">*</span>:</label>
                            <input id="ingredientcate" name="category" type="text" placeholder="Input ingredient category" required>
                        </div>
                        <div class="form-group-blog">
                            <label for="quantity" class="fnt-wei-bold">Quantity<span class="cl-red">*</span>:</label>
                            <input id="quantity" name="quantity" type="text" placeholder="Input ingredient quantity" required>
                        </div>
                        <div class="form-group-blog">
                            <label for="price" class="fnt-wei-bold">Price<span class="cl-red">*</span>:</label>
                            <input id="price" name="price" type="text" placeholder="Input ingredient price" required>
                        </div>
                        <div class="form-group-blog">
                            <label for="sale" class="fnt-wei-bold">Sale Percent<span class="cl-red">*</span>:</label>
                            <input id="sale" name="sale" type="text" placeholder="Input sale" required="">
                        </div>
                    </div>
                    <button class="create-blog" type="submit" value="CreateIngredient" name="action">Create Ingredient</button>
                </form>
            </div>
        </div>
    </body>
</html>
<c:import url="Footer.jsp"/>
