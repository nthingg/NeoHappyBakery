<%-- 
    Document   : Cart
    Created on : Oct 29, 2022, 3:02:27 PM
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

<c:set var="user" value="${sessionScope.user}"/>
<c:set var="listStore" value="${sessionScope.listStoreCart}"/>
<c:set var="totalCart" value="${sessionScope.totalCart}"/>
<c:set var="countCart" value="${sessionScope.countCart}"/>
<c:set var="fee" value="${sessionScope.fee}"/>
<c:set var="deliver" value="${sessionScope.deliver}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/cart.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
        <link rel="stylesheet" href="cute-alert-master/style.css"/>
    </head>

    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="">Home</a>/<a href="">Cart</a></p>
            <h1 class="mapping-heading">CART</h1>
        </div>
        <div class="cart-container">
            <div class="cart-section">
                <div class="item-section">

                    <div class="total-item flex-bl">
                        <c:if test="${countCart == null}">
                            <p>0 ITEMS</p>
                        </c:if>
                        <c:if test="${countCart != null}">
                            <p>${countCart} ITEMS</p>
                        </c:if>
                        <a href="DeleteCart" class="hover-change">DELETE</a>
                    </div>

                    <c:forEach items="${listStore}" var="store">
                        <div class="total-item border-bt">
                            <div class="store-name">
                                <a href="GetUserProfile?checkedUserId=${store.getUserId()}" class="hover-change">${store.getUserName()}</a>
                            </div>
                            <c:forEach var="item" items="${sessionScope.cart}">
                                <c:if test="${item.key == store.getUserId() }">
                                    <c:set var="list" value="${item.value}"/>
                                </c:if>
                            </c:forEach>

                            <c:forEach var="buy" items="${list}">
                                <div class="cart-item">
                                    <div class="cart-item-left">
                                        <img src="${buy.getItem().getIngredientImg()}">
                                        <p class="ingrename">${buy.getItem().getIngredientName()}</p>
                                        <c:if test="${buy.getItem().getSalePercent() != 0}">
                                            <div class="price-container">
                                                <p style="text-decoration: line-through;">${buy.getPrice()} VND</p>
                                                <p style="color: red;">${buy.getSalePrice()} VND</p>
                                            </div>
                                        </c:if>
                                        <c:if test="${buy.getItem().getSalePercent() == 0}">
                                            <div class="price-container">
                                                <p>${buy.getPrice()} VND</p> 
                                            </div>
                                        </c:if>    

                                    </div>
                                    <div class="cart-item-right">
                                        <div class="quantity-control">
                                            <form action="UpdateCart" method="post">
                                                <div class="number-input">
                                                    <button class="increment" id="minus"
                                                            onclick="this.parentNode.querySelector('input[type=number]').stepDown()"></button>

                                                    <input class="quantity" min="0" max="1000" name="quantity" value="${buy.getQuantity()}" 
                                                           type="number" onkeydown="redirect(this)">

                                                    <button class="increment" onclick="this.parentNode.querySelector('input[type=number]').stepUp()"
                                                            class="plus"></button>  

                                                    <input type="hidden" name="storeId" value="${store.getUserId()}">
                                                    <input type="hidden" name="availId" value="${buy.getItem().getAvailId()}">
                                                </div> 
                                            </form>
                                        </div>

                                        <c:url var="remove" value="RemoveItem">
                                            <c:param name="availId" value="${buy.getItem().getAvailId()}"/>
                                            <c:param name="storeId" value="${store.getUserId()}"/>
                                        </c:url>
                                        <div class="remove-item">
                                            <a href="${remove}" id="remove-item"><i class="fas fa-trash"></i></a>
                                        </div>
                                    </div>
                                </div>   
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>


                <div class="payment-section sticky-payment" >
                    <div class="price-detail">
                        <p>Payment Method <i class="fas fa-money-check"></i></i></p>
                        <div class="info-payment">
                            <input type="radio" checked>
                            <p>Cash On Delivery</p>
                            <img class="in-cash-icon"
                                 src="https://lzd-img-global.slatic.net/g/tps/tfs/TB1ZP8kM1T2gK0jSZFvXXXnFXXa-96-96.png_2200x2200q80.jpg_.webp"
                                 alt="" srcset="">
                        </div>
                    </div>
                    <div class="line-sep"></div>
                    <div class="price-detail">
                        <div id="origin-address">
                            <p>Delivery Informations <i class="fas fa-truck"></i></p>
                                <c:if test="${deliver != null}">
                                <div class="delivery">
                                    <p class="address-payment"><i class="fas fa-user"></i> ${user.getUserName()}</p>
                                    <p class="address-payment"><i class="fas fa-phone"></i> ${user.getUserPhone()}</p>
                                    <p class="address-payment"><i class="fas fa-map-marker-alt"></i> ${user.getUserAddress()}</p>
                                </div>
                                <a href="#" class="hover-change" onclick="change()">EDIT</a>
                            </c:if>
                            <c:if test="${deliver == null && user != null}">
                                <div class="delivery">
                                    <p class="address-payment"><i class="fas fa-user"></i> ${user.getUserName()}</p>
                                    <p class="address-payment"><i class="fas fa-phone"></i> ${user.getUserPhone()}</p>
                                    <p class="address-payment"><i class="fas fa-map-marker-alt"></i> ${user.getUserAddress()}</p>
                                </div>
                            </c:if>
                            <c:if test="${user == null}">
                                <div class="delivery">
                                    <p class="address-payment"><i class="fas fa-user"></i> None</p>
                                </div>
                                <a class="hover-change" onclick="">EDIT</a>
                            </c:if>
                        </div>

                        <div id="change-address">
                            <p>Delivery Informations <i class="fas fa-truck"></i></p>
                            <form action="CreateDeliveryInfor" method="post">
                                <div class="form-group-cart">
                                    <label for="fullname" class="fnt-wei-bold">Receiver Name<span class="cl-red">*</span>:</label>
                                    <input id="fullname" name="fullname" type="text" placeholder="Full name..." required>
                                </div>
                                <div class="form-group-cart">
                                    <label for="phone" class="fnt-wei-bold">Receiver Phone<span class="cl-red">*</span>:</label>
                                    <input id="phone" name="phone" type="text" placeholder="Phone number..." required>
                                </div>
                                <div class="form-group-cart">
                                    <label for="address" class="fnt-wei-bold">Receiver Address<span class="cl-red">*</span>:</label>
                                    <input id="address" name="address" type="text" placeholder="Detail address..." required>
                                </div>
                                <div class="btn-group">
                                    <button class="cart-deliver-btn cl-myred" onclick="cancel()">Cancel</button>
                                    <button type="submit" class="cart-deliver-btn cl-gr">Save</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="line-sep"></div>
                    <div class="price-detail">
                        <p>Order Summary <i class="fas fa-receipt"></i></p>
                        <div class="info-price">
                            <p>Sub total (${countCart} items)</p>
                            <p>${totalCart}</p>
                        </div>
                        <div class="info-price">
                            <p>Shipping fee</p>
                            <p>${fee}</p>
                        </div>
                        <div class="info-price">
                            <p>Total</p>
                            <p class="total-price">${totalCart + fee}</p>
                        </div>

                        <c:if test="${user == null}">
                            <button type="submit" class="cart-btn" style="background-color: black;">Confirm cart</button>
                        </c:if>
                        <c:if test="${user != null}">
                            <form action="ConfirmCart" method="post">
                                <button type="submit" class="cart-btn">Confirm cart</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function redirect(ele) {
                document.querySelectorAll(".increment").forEach(e => e.disabled = true)

                if (event.key === 'Enter') {
                    if (ele.value != '') {
                        var form = ele.closest('form');
                        form.submit();
                    }
                }
            }
        </script>
        <script src="cute-alert-master/cute-alert.js"></script>
        <script>
            

            function warning() {
                var enable = false;
                cuteAlert({
                    img: "img/success.svg",
                    type: "question",
                    title: "Delete warning!",
                    message: "You sure want to delete the ingredient?",
                    confirmText: "Confirm",
                    cancelText: "No"
                }).then((e) => {
                    if (e == "confirm") {
                        enable = true;
                    } else {
                        enable = false;
                    }
                });
                if (enable == true) {
                    return true;
                } else {
                    return  false;
                }
            }

            <c:choose>
                <c:when test="${requestScope.addSuccess != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "success",
                title: "Buy successfull!",
                message: "The ingredients will deliver to you asap!",
                buttonText: "Confirm"
            });
                </c:when>
                <c:when test="${requestScope.addFail != null}">
            cuteAlert({
                img: "img/error.svg",
                type: "error",
                title: "Buy fail!",
                message: "${requestScope.failMsg}",
                buttonText: "Confirm"
            });
                </c:when>
            </c:choose>
        </script>


        <script src="js/cart.js"></script>

    </body>
</html>
<c:import url="Footer.jsp"/>

