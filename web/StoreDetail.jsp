<%-- 
    Document   : StoreDetail
    Created on : Oct 3, 2022, 10:01:22 AM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:choose>
    <c:when test="${sessionScope.user == null}">
        <c:import url="AuthRedirect.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="AuthLogined.jsp"/>
    </c:otherwise>
</c:choose>
<c:import url="Navigation.jsp"/>

<html>
    <head>
        <link rel="icon" type="image/x-icon" href="img/websitelogo.jpg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store Detail</title>
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/mycss.css">
        <link rel="stylesheet" href="css/storeProfile.css">
        <link rel="stylesheet" href="css/pfPaging.css">
        <link rel="stylesheet" href="css/recipePage.css">
        <link rel="stylesheet" href="css/recipePfPaging.css">
        <link rel="stylesheet" href="css/blogPfPaging.css">
        <link rel="stylesheet" href="css/blogPf.css">
        <link rel="stylesheet" href="css/orderPfPaging.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
        <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="cute-alert-master/style.css"/>
    </head>
    <body onload="submit()">

        <c:set var="checkedUser" value="${sessionScope.checkedUser}"/>
        <c:set var="blogPosted" value="${sessionScope.blogPosted}"/>
        <c:set var="recipePosted" value="${sessionScope.recipePosted}"/>
        <c:set var="pfTotalRating" value="${sessionScope.pfTotalRating}"/>
        <c:set var="pfBadgeList" value="${sessionScope.pfBadgeList}"/>
        <c:set var="pfBlogList" value="${sessionScope.pfBlogList}"/>
        <c:set var="pfRecipeList" value="${sessionScope.pfRecipeList}"/>
        <c:set var="pfOrderList" value="${sessionScope.pfOrderList}"/>
        <c:set var="user" value="${sessionScope.user}"/>    
        <c:if test="${checkedUser.getUserType() == 'STORE'}">
            <c:set var="specIngredient" value="${sessionScope.specIngredient}"/>
        </c:if>
        <c:set var="followStat" value="${sessionScope.followStat}"/>

        <c:if test="${requestScope.isRefresh == 'no'}">
            <c:redirect url="GetUserProfile?checkedUserId=${user.getUserId()}"/>  
        </c:if>


        <div class="mapping grad">
            <p class="breadcrums"><a href="HomePage.jsp">Home</a>/<a href="#">Profile</a></p>
            <h1 class="mapping-heading">Profile</h1>
        </div>

        <div class="container">
            <div class="store-profile-container">
                <div class="store-heading">
                    <c:if test="${checkedUser.getUserImg() == ''}">
                        <img class="store-img" src="img/userdefault.jpg" alt="">
                    </c:if>

                    <c:if test="${not empty checkedUser.getUserImg()}">
                        <img class="store-img" src="${checkedUser.getUserImg()}" alt="">
                    </c:if>
                    <h2 class="store-fullname">${checkedUser.getUserName()}</h2>
                    <c:url var="unfollow" value="followUser">
                        <c:param name="action" value="unfollow"/>
                        <c:param name="followed" value="${checkedUser.getUserId()}"/>
                        <c:param name="follower" value="${user.getUserId()}"/>
                    </c:url>

                    <c:url var="follow" value="followUser">
                        <c:param name="action" value="follow"/>
                        <c:param name="followed" value="${checkedUser.getUserId()}"/>
                        <c:param name="follower" value="${user.getUserId()}"/>
                    </c:url>

                </div>

                <div class="store-info-filter">
                    <div class="info-table">
                        <div class="table-choice">
                            <div id="myBtnContainerPf" class="filter-selection">
                                <button class="btn-pf active-filter-pf"
                                        onclick="filterSelectionPfStore('information-table')">Information</button>
                                <c:if test="${checkedUser.getUserType() == 'STORE'}">
                                    <button class="btn-pf " id="click-ingredient"
                                            onclick="filterSelectionPfStore('ingredient-table')">Ingredient</button>
                                </c:if>
                                <button class="btn-pf " id="click-recipe" onclick="filterSelectionPfStore('recipe-table')">Recipe</button>
                                <button class="btn-pf " id="click-blog" onclick="filterSelectionPfStore('blog-table')">Blog</button>
                                <c:if test="${checkedUser.getUserId() == user.getUserId()}">
                                    <button id="click-order" class="btn-pf " onclick="filterSelectionPfStore('order-table')">Order</button>
                                </c:if>

                            </div>

                            <c:if test="${checkedUser.getUserType() == 'STORE'}">
                                <div class="filterDivPf ingredient-table">
                                    <div class="add-section">
                                        <c:url var="createIngre" value="IngredientCreate.jsp">
                                            <c:param name="storeName" value="${checkedUser.getUserName()}"/>
                                            <c:param name="storeId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <c:if test="${checkedUser.getUserId() == user.getUserId()}">
                                            <a href="${createIngre}" class="add-btn"><i class="fa fa-plus"></i> Add More Ingredient</a>
                                        </c:if>
                                    </div>

                                    <a id="check-is-cart"></a>
                                    <form action="MainController" method="post">
                                        <div class="search-section">
                                            <input type="text" name="textSearch" class="search-val" placeholder="Search by name, category...">
                                            <select name="searchby">
                                                <option value="byName">Name</option>
                                                <option value="byCate">Category</option>
                                            </select>
                                            <button type="submit" class="search-btn" id="search-ingre"  value="ProfileSearchIngre" name="action"><i class="fa fa-search"></i></button>
                                        </div>
                                        <input type="hidden" value="${checkedUser.getUserId()}" name="storeId">
                                    </form>

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
                            </c:if>

                            <c:if test="${checkedUser.getUserId() == user.getUserId()}">
                                <div class="filterDivPf order-table">

                                    <div class="search-section">
                                        <input type="text" name="txtsearch" class="search-val" value=""
                                               placeholder="Input search value...">
                                        <select name="searchby">
                                            <option value="byUserName">User Name</option>
                                        </select>
                                        <button type="submit" class="search-btn" value="SEARCH" name="action"><i class="fa fa-search"></i></button>
                                    </div>


                                    <div class="filter-order-status" >
                                        <c:url var="processing" value="FilterOrder">
                                            <c:param value="1" name="orderStatus"/>
                                            <c:param name="userType" value="${checkedUser.getUserType()}"/>
                                            <c:param name="userId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <c:url var="delivering" value="FilterOrder">
                                            <c:param value="2" name="orderStatus"/>
                                            <c:param name="userType" value="${checkedUser.getUserType()}"/>
                                            <c:param name="userId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <c:url var="completed" value="FilterOrder">
                                            <c:param value="3" name="orderStatus"/>
                                            <c:param name="userType" value="${checkedUser.getUserType()}"/>
                                            <c:param name="userId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <c:url var="canceled" value="FilterOrder">
                                            <c:param value="0" name="orderStatus"/>
                                            <c:param name="userType" value="${checkedUser.getUserType()}"/>
                                            <c:param name="userId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <c:url var="all" value="GetUserProfile">
                                            <c:param name="checkedUserId" value="${checkedUser.getUserId()}"/>
                                        </c:url>
                                        <a id="check-is-pf"></a>
                                        <a href="${canceled}" onclick="clickChange()" class="status" id="canceled">Canceled</a>
                                        <a href="${processing}" onclick="clickChange()" class="status" id="processing">Processing</a>
                                        <a href="${delivering}" onclick="clickChange()" class="status" id="delivering">Delivering</a>
                                        <a href="${completed}" onclick="clickChange()" class="status" id="completed">Completed</a>
                                        <a href="${all}" onclick="clickChange()" class="status" id="all">All</a>

                                    </div>

                                    <div class="table-order" id="order-container">
                                    </div>

                                    <div class="content__paging-order">
                                        <div class="page-order">
                                            <ul>
                                                <li class="btn-prev-order btn-active-order fas fa-angle-left"></li>
                                                <div class="number-page-order" id="number-page-order">
                                                    <!-- Page Number -->
                                                </div>
                                                <li class="btn-next-order fas fa-angle-right"></li>
                                            </ul>
                                        </div>
                                        <div class="total-page-order"></div>
                                        <div class="total-item-order"></div>
                                    </div>

                                </div>
                            </c:if>

                            <div class="filterDivPf recipe-table">

                                <div style="display: flex;">

                                </div>
                                <div class="add-section">
                                    <c:if test="${checkedUser.getUserId() == user.getUserId()}">
                                        <a href="ManageRecipe?CreateOrUpdate=1" class="add-btn"><i class="fa fa-plus"></i> Add more recipes</a>
                                    </c:if>
                                </div>
                                <form action="MainController" method="post">
                                    <div class="search-section">
                                        <input type="text" name="textSearch" class="search-val" value="" placeholder="Search by name, category...">
                                        <select name="searchby">
                                            <option value="byName">Name</option>
                                            <option value="byCate">Category</option>
                                            <option value="byCount">Country</option>
                                        </select>
                                        <button type="submit" class="search-btn" value="ProfileSearchRecipe" id="search-recipe" name="action"><i class="fa fa-search"></i></button>
                                    </div>
                                    <input type="hidden" value="${checkedUser.getUserId()}" name="storeId">
                                </form>

                                <div class="table-body-recipe" id="recipe-container">

                                </div>
                                <div class="content__paging-recipe">
                                    <div class="page-recipe">
                                        <ul>
                                            <li class="btn-prev-recipe btn-active-recipe fas fa-angle-left"></li>
                                            <div class="number-page-recipe" id="number-page-recipe">
                                                <!-- Page Number -->
                                            </div>
                                            <li class="btn-next-recipe fas fa-angle-right"></li>
                                        </ul>
                                    </div>
                                    <div class="total-page-recipe"></div>
                                    <div class="total-item-recipe"></div>
                                </div>

                            </div>

                            <div class="filterDivPf blog-table">

                                <div class="add-section">
                                    <c:if test="${checkedUser.getUserId() == user.getUserId()}">
                                        <a href="ManageBlog?actiontype=create" class="add-btn"><i class="fa fa-plus"></i> Add more blogs</a>
                                    </c:if>
                                </div>

                                <form action="FilterBlog" method="post">
                                    <div class="search-section">
                                        <input type="text" name="txtsearch" class="search-val" value=""
                                               placeholder="Input search value...">
                                        <select name="searchby">
                                            <option value="byName">Name</option>
                                            <option value="byCate">Category</option>
                                        </select>
                                        <input type="hidden" value="${checkedUser.getUserId()}" name="storeId">
                                        <button type="submit" class="search-btn" id="search-blog" value="SEARCH" name="action"><i class="fa fa-search"></i></button>
                                    </div>
                                </form>

                                <div class="blog-container" id="blog-container">

                                </div>
                                <div class="content__paging-blog">
                                    <div class="page-blog">
                                        <ul>
                                            <li class="btn-prev-blog btn-active-blog fas fa-angle-left"></li>
                                            <div class="number-page-blog" id="number-page-blog">
                                                <!-- Page Number -->
                                            </div>
                                            <li class="btn-next-blog fas fa-angle-right"></li>
                                        </ul>
                                    </div>
                                    <div class="total-page-blog"></div>
                                    <div class="total-item-blog"></div>
                                </div>

                            </div>



                            <div class="filterDivPf information-table">
                                <div class="table-body-info">

                                    <div class="infor-badge">
                                        <div class="badge-heading">
                                            <h2>Badge Achieved</h2>
                                        </div>
                                        <div class="badge-body">
                                            <c:forEach var="badge" items="${pfBadgeList}">
                                                <p class="badge-title">${badge.getBadgeName()}</p>
                                            </c:forEach>
                                            <c:if test="${pfBadgeList.size() == 0}">
                                                <p class="badge-title">None</p>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class="recipe-blog-posted">
                                        <div class="posted">
                                            <div class="post-heading">
                                                <h3>Recipe posted</h3>
                                            </div>
                                            <div class="post-body">
                                                <p>${recipePosted}</p>
                                            </div>
                                        </div>
                                        <div class="posted">
                                            <div class="post-heading">
                                                <h3>Blog posted</h3>
                                            </div>
                                            <div class="post-body">
                                                <p>${blogPosted}</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="recipe-blog-posted">
                                        <div class="posted">
                                            <div class="post-heading">
                                                <h3>Followers</h3>
                                            </div>
                                            <div class="post-body">
                                                <p>${followers} <i class="fas fa-user"></i></p>
                                            </div>
                                        </div>
                                        <div class="posted">
                                            <div class="post-heading">
                                                <h3>Total Rating</h3>
                                            </div>
                                            <div class="post-body">
                                                <c:forEach begin="1" end="${pfTotalRating}">
                                                    <span class="fa fa-star checked"></span>
                                                </c:forEach>
                                                <c:forEach begin="1" end="${5 - pfTotalRating}">
                                                    <span class="fa fa-star"></span>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="user-detail">
                                        <div class="detail-heading">
                                            <h2>Detail</h2>
                                        </div>
                                        <div class="detail-body">
                                            <div class="detail-item">
                                                <p class="detail-title">Gender:</p>
                                                <p class="detail-descript">${checkedUser.getUserGender()}</p>
                                            </div>
                                            <div class="detail-item">
                                                <p class="detail-title">Birth Day:</p>
                                                <p class="detail-descript">${checkedUser.getUserBDay()}</p>
                                            </div>
                                            <div class="detail-item">
                                                <p class="detail-title">Address:</p>
                                                <p class="detail-descript">${checkedUser.getUserAddress()}</p>
                                            </div>
                                            <div class="detail-item">
                                                <p class="detail-title">Phone:</p>
                                                <p class="detail-descript">${checkedUser.getUserPhone()}</p>
                                            </div>
                                            <div class="detail-item">
                                                <p class="detail-title">Email:</p>
                                                <p class="detail-descript">${checkedUser.getUserMail()}</p>
                                            </div>
                                        </div>
                                        <div class="update-user">
                                            <a class="update-user-info" href="UpdateProfile">UPDATE</a>
                                        </div>
                                    </div>




                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>

            let recipeList = [
            <c:forEach items="${pfRecipeList}" var="recipe">
            { loginedid: '${user.getUserId()}',
                    id: '${recipe.getRecipeId()}',
                    img: '${recipe.getRecipeImg()}',
                    name: '${recipe.getRecipeName()}',
                    type: '${recipe.getCategoryName()}',
                    postday: '${recipe.getRecipeAddedDate()}',
                    userid: '${recipe.getUserId()}',
                    username: '${recipe.getUserName()}',
                    rating: '${recipe.getRecipeRating()}',
                    numberrating: '${recipe.getNumberOfRatings()}'},
            </c:forEach>
            ];
            <c:if test="${checkedUser.getUserType() == 'STORE'}">
            let ingredientList = [
                <c:forEach var="ingredient" items="${specIngredient}">
            {loginedid: '${user.getUserId()}',
                    availid: '${ingredient.getAvailId()}',
                    pfid: '${checkedUser.getUserId()}',
                    id: '${ingredient.getIngredientId()}',
                    img: "${ingredient.getIngredientImg()}",
                    name: "${ingredient.getIngredientName()}",
                    category: "${ingredient.getIngredientCategory()}",
                    price: ${ingredient.getPrice()},
                    sale: ${ingredient.getSalePercent()},
                    storeid: ${ingredient.getStoreId()},
                    quantity: ${ingredient.quantity}
            },
                </c:forEach>
            ];
            window.sessionStorage.setItem('IngredientArray', JSON.stringify(ingredientList));
            </c:if>

            let blogList = [
            <c:forEach items="${pfBlogList}" var="blog">
            { loginedid: '${user.getUserId()}',
                    id: ${blog.getBlogId()},
                    img: '${blog.getBlogImg()}',
                    title: '${blog.getBlogTitle()}',
                    type: '${blog.getBlogCategory()}',
                    postday: '${blog.getBlogAddedDay()}',
                    userid: '${blog.getUserId()}',
                    username: '${user.getUserName()}',
                    rating: '${blog.getBlogRatingPoint()}',
            },
            </c:forEach>
            ];
            <c:if test="${checkedUser.getUserId() == user.getUserId()}">
            let orderList = [
                <c:forEach items="${pfOrderList}" var="order">
            { img: '${order.getOrderImg()}',
                    id: '${order.getOrderId()}',
                    orderdate: '${order.getOrderDate()}',
                    shipdate: '${order.getShipDate()}',
                    status: '${order.getOrderStatus()}',
                    userid: '${order.getUserId()}',
                    username: '${order.getUserName()}',
                    checkstore: '${checkedUser.getUserType()}',
                    storeid: '${order.getStoreId()}',
                    storename: '${order.getStoreName()}',
            },
                </c:forEach>
            ];
            window.sessionStorage.setItem('orderArray', JSON.stringify(orderList));
            </c:if>

            window.sessionStorage.setItem('recipeArray', JSON.stringify(recipeList));
            window.sessionStorage.setItem('BlogArray', JSON.stringify(blogList));
        </script>   
        <script src="js/filterDataProfile.js"></script>
        <c:if test="${checkedUser.getUserType() == 'STORE'}">
            <script src="js/storePfPaging.js"></script>
        </c:if>
        <script src="js/recipePfPaging.js"></script>
        <script src="js/blogPfPaging.js"></script>
        <c:if test="${checkedUser.getUserId() == user.getUserId()}">
            <script src="js/orderPfPaging.js"></script>
        </c:if>

        <script src="js/onload.js"></script>

        <script src="cute-alert-master/cute-alert.js"></script>
        <script>
            function deleteIngredient(redirect) {
            cuteAlert({
            img: "img/error.svg",
                    type: "error-q",
                    title: "Delete ingredient!",
                    message: "Are you sure want to delete this ingredient?",
                    confirmText: "Yes",
                    cancelText: "No"
            }).then((e) => {
            if (e == "confirm") {
            window.location = "DeleteIngredient?availId=" + redirect;
            } else {

            }
            });
            }

            function deleteRecipe(redirect) {
            cuteAlert({
            img: "img/error.svg",
                    type: "error-q",
                    title: "Delete recipe!",
                    message: "Are you sure want to delete this recipe?",
                    confirmText: "Yes",
                    cancelText: "No"
            }).then((e) => {
            if (e == "confirm") {
            window.location = "DeleteRecipe?recipeId=" + redirect;
            } else {

            }
            });
            }

            function deleteBlog(redirect) {
            cuteAlert({
            img: "img/error.svg",
                    type: "error-q",
                    title: "Delete blog!",
                    message: "Are you sure want to delete this blog?",
                    confirmText: "Yes",
                    cancelText: "No"
            }).then((e) => {
            if (e == "confirm") {
            window.location = "DeleteBlog?blogId=" + redirect;
            } else {

            }
            });
            }

            <c:choose>
                <c:when test="${requestScope.addSuccessfull != null}">
            cuteAlert({
            img: "img/success.svg",
                    type: "success-q",
                    title: "Add to cart successfull!",
                    message: "Happy to serve you!",
                    confirmText: "To Cart",
                    cancelText: "Continue"
            }).then((e) => {
            if (e == "confirm") {
            window.location = 'ManageCart';
            } else {

            }
            });
                </c:when>
            </c:choose>
        </script>

    </body>
</html>
<c:import url="Footer.jsp"/>
