<%-- 
    Document   : OrderDetail
    Created on : Oct 29, 2022, 3:22:20 PM
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

<c:set var="orderDetail" value="${sessionScope.orderDetail}"/>
<c:set var="orderDetailList" value="${sessionScope.orderDetailList}"/>
<c:set var="ordererOrderDetail" value="${sessionScope.ordererOrderDetail}"/>
<c:set var="storeOrderDetail" value="${sessionScope.storeOrderDetail}"/>
<c:set var="user" value="${sessionScope.user}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/orderDetail.css">
        <link rel="stylesheet" href="css/pfPaging.css">
        <link rel="stylesheet" href="css/storeProfile.css">
        <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="">Home</a>/<a href="">Order</a></p>
            <h1 class="mapping-heading">Order Detail</h1>
        </div>


        <div class="order-detail-container">
            <div id="status" class="status-block">
                <div id="origin-stat">
                    <p class="stat">Status</p>
                    <c:if test="${orderDetail.getOrderStatus() == 1}">
                        <p class="stat-name">Processing</p>
                    </c:if>
                    <c:if test="${orderDetail.getOrderStatus() == 2}">
                        <p class="stat-name">Delivering</p>
                    </c:if>
                    <c:if test="${orderDetail.getOrderStatus() == 3}">
                        <p class="stat-name">Completed</p>
                    </c:if>
                    <c:if test="${orderDetail.getOrderStatus() == 4}">
                        <p class="stat-name">Canceled</p>
                    </c:if>
                        
                    <c:if test="${user.getUserType() == 'STORE'}">
                        <c:if test="${orderDetail.getOrderStatus() == 1}">
                            <button onclick="change()" class="btn-order-detail">Change status</button>
                        </c:if>
                        <c:if test="${orderDetail.getOrderStatus() == 2}">
                            <button onclick="change()" class="btn-order-detail">Change status</button>
                        </c:if>
                    </c:if>
                </div>
                <div id="change-stat">
                    <p class="stat">Status</p>
                    <form action="ChangeOrderStat" method="post">
                        <select class="select-stat" name="status">
                            <c:if test="${orderDetail.getOrderStatus() == 1}">
                                <option value="processing">Processing</option>
                                <option value="delivering">Delivering</option>
                                <option value="cancel">Cancel</option>
                            </c:if>
                            <c:if test="${orderDetail.getOrderStatus() == 2}">
                                <option value="delivering">Delivering</option>
                                <option value="completed">Completed</option>
                                <option value="cancel">Cancel</option>
                            </c:if>
                        </select>
                        <br>
                        <input type="hidden" name="orderId" value="${orderDetail.getOrderId()}">
                        <button type="submit" class="btn-order-detail cl-gr">Confirm</button>
                        <button onclick="cancel()" class="btn-order-detail btn-cl-red">Cancel</button>
                    </form>
                </div>

                <div class="order-datetime">
                    <p class="stat">Date time</p>
                    <p>Order date: ${orderDetail.getOrderDate()}</p>
                </div>

                <div class="user-container-order-detail">
                    <div class="order-infor">
                        <p class="user-ordered">Orderer</p>
                        <div class="author-container">
                            <c:if test="${empty ordererOrderDetail.getUserImg()}">
                                <c:set var="authorImg" value="img/userdefault.jpg"/>
                            </c:if>
                            <c:if test="${not empty ordererOrderDetail.getUserImg()}">
                                <c:set var="authorImg" value="${ordererOrderDetail.getUserImg()}"/>
                            </c:if>
                            <img class="author-img" src="${authorImg}" alt="">
                            <p class="author-fullname"><a href="GetUserProfile?checkedUserId=${orderDetail.getUserName()}" class="author-redirect">${orderDetail.getUserName()}</a></p>
                        </div>
                    </div>

                    <div class="store-infor">
                        <p class="user-ordered">Store</p>
                        <div class="author-container">
                            <c:if test="${empty ordererOrderDetail.getUserImg()}">
                                <c:set var="authorImg" value="img/userdefault.jpg"/>
                            </c:if>
                            <c:if test="${not empty ordererOrderDetail.getUserImg()}">
                                <c:set var="authorImg" value="${ordererOrderDetail.getUserImg()}"/>
                            </c:if>
                            <img class="author-img" src="${authorImg}" alt="">
                            <p class="author-fullname"><a href="GetUserProfile?checkedUserId=${orderDetail.getStoreId()}" class="author-redirect">${orderDetail.getStoreName()}</a></p>
                        </div>
                    </div>
                </div>

            </div>
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

            let detailList = [
            <c:forEach var="detail" items="${orderDetailList}">
                {img: "${detail.getIngredientImg()}",
                    name: "${detail.getIngredientName()}",
                    category: "${detail.getIngredientCategory()}",
                    price: ${detail.getFinalPrice()},
                    quantity: ${detail.getQuantity()}
                },
            </c:forEach>
            ];
            window.sessionStorage.setItem('DetailArray', JSON.stringify(detailList));
        </script>
        <script src="js/orderDetailPaging.js"></script>
        <script src="js/changeStatus.js"></script>
    </body>
</html>
<c:import url="Footer.jsp"/>

