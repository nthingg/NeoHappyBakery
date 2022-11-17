<%-- 
    Document   : CheckOut
    Created on : Nov 1, 2022, 11:39:14 AM
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

<c:set var="specIngredient" value="${sessionScope.listSpecCheckOut}"/>
<c:set var="user" value="${sessionScope.user}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/checkOut.css">
        <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
        <link rel="stylesheet" href="css/checkOutPaging.css">
    </head>
    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="">Home</a>/<a href="">Checkout</a></p>
            <h1 class="mapping-heading">CHECKOUT</h1>
        </div>
        <div class="checkout-container">
            <h2>All ${sessionScope.specName} availables</h2>
            <div class="table-body" id="ingredient-table-body">

            </div>
            <div class="content__paging">
                <div class="page">
                    <ul>
                        <li class="btn-prev btn-active fas fa-angle-left"></li>
                        <div class="number-page" id="number-page">
                            <!-- Page Number -->
                        </div>
                        <li class="btn-next fas fa-angle-right"></li>
                    </ul>
                </div>
                <div class="total-page"></div>
                <div class="total-item"></div>
            </div>
        </div> 
        <script>
            let ingredientList = [
            <c:forEach var="ingredient" items="${specIngredient}">
                {
                    availid: '${ingredient.getAvailId()}',
                    usertype: '${user.getUserType()}',
                    id: '${ingredient.getIngredientId()}',
                    img: "${ingredient.getIngredientImg()}",
                    name: "${ingredient.getIngredientName()}",
                    category: "${ingredient.getIngredientCategory()}",
                    price: '${ingredient.getPrice()}',
                    sale: '${ingredient.getSalePercent()}',
                    storeid: '${ingredient.getStoreId()}',
                    storename: '${ingredient.getStoreName()}'
                },
            </c:forEach>
            ];
            window.sessionStorage.setItem('SpecArray', JSON.stringify(ingredientList));
        </script>
        <script src="js/checkOutPaging.js"></script>
    </body>
</html>

<c:import url="Footer.jsp"/>

