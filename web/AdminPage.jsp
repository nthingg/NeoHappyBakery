<%-- 
    Document   : AdminPage
    Created on : Sep 15, 2022, 3:51:05 PM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin Page</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="cssAdmin/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="cssAdmin/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid position-relative d-flex p-0">
            <c:set var="user" value="${sessionScope.user}"></c:set>

                <!-- Sidebar Start -->
                <div class="sidebar pe-4 pb-3">
                    <nav class="navbar bg-secondary navbar-dark">
                        <a href="AdminPage.jsp" class="navbar-brand mx-4 mb-3">
                            <h3 class="text-primary"><i class="fa fa-user-edit me-2"></i>Happy Bakery</h3>
                        </a>
                        <div class="d-flex align-items-center ms-4 mb-4">
                            <div class="position-relative">
                                <img class="rounded-circle" src="${user.getUserImg()}" alt="" style="width: 40px; height: 40px;">
                            <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">${user.getUserName()}</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="MainController?action=AdminLogin" class="nav-item nav-link active"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Account</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="MainController?action=manageAccount" class="dropdown-item">Block/UnBlock</a>
                            </div>
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Blog</a>
                                <div class="dropdown-menu bg-transparent border-0">
                                    <a href="MainController?action=manageBlog" class="dropdown-item ">View all</a>
                                    <a href="MainController?action=getReportedBlog" class="dropdown-item ">Reported</a>
                                </div>
                            </div>
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Recipe</a>
                                <div class="dropdown-menu bg-transparent border-0">
                                    <a href="MainController?action=manageRecipe" class="dropdown-item">View all</a>
                                    <a href="MainController?action=getReportedRecipe" class="dropdown-item">Reported</a>
                                </div>
                            </div>
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Comment</a>
                                <div class="dropdown-menu bg-transparent border-0">
                                    <a href="MainController?action=getAllComments" class="dropdown-item">View all</a>
                                    <a href="MainController?action=getReportedComment" class="dropdown-item">Reported</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand bg-secondary navbar-dark sticky-top px-4 py-0">
                    <a href="AccountManagement.jsp" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-user-edit"></i></h2>
                    </a>
                    <a href="#" class="sidebar-toggler flex-shrink-0">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form class="d-none d-md-flex ms-4">
                        <input class="form-control bg-dark border-0" type="search" placeholder="Search">
                    </form>
                    <div class="navbar-nav align-items-center ms-auto">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-envelope me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Message</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all message</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-bell me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Notificatin</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Profile updated</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">New user added</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Password changed</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all notifications</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img class="rounded-circle me-lg-2" src="${user.getUserImg()}" alt="" style="width: 40px; height: 40px;">
                                <span class="d-none d-lg-inline-flex">${user.getUserName()}</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">My Profile</a>
                                <a href="#" class="dropdown-item">Settings</a>
                                <c:url var="logoutLink" value="MainController">
                                    <c:param name="action" value="logout"/>
                                </c:url>
                                <a href="${logoutLink}" class="dropdown-item">Log Out</a>

                            </div>
                        </div>
                    </div>
                </nav>

                <!-- Navbar End -->
                <div class="container-fluid pt-4 px-4">


                    <div class="row g-4">
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-line fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Total Account</p>
                                    <h6 class="mb-0"><%= session.getAttribute("sizeAccount")%>  </h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-line fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Total Active Account</p>
                                    <h6 class="mb-0"><%= session.getAttribute("sizeActiveAccount")%>  </h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-bar fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Total Blog</p>
                                    <h6 class="mb-0"><%= session.getAttribute("sizeBlog")%></h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-area fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Total Recipe</p>
                                    <h6 class="mb-0"><%= session.getAttribute("sizeRecipe")%></h6>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <!-- Recent BLOG -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-secondary text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Recent Blogs</h6>
                            <a href="">Show All</a>
                        </div>
                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">User ID</th>
                                        <th scope="col">Title</th>
                                        <th scope="col">Category</th>
                                        <th scope="col">Rating</th>
                                        <th scope="col">Action</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="blog" items="${sessionScope.listRecentBlog}">
                                        <tr>
                                            <th scope="col"><c:out value="${blog.getBlogId() }"></c:out></th>
                                            <th scope="col"><c:out value="${blog.getUserId()}"></c:out></th>
                                            <th scope="col"><c:out value="${blog.getBlogTitle()}"></c:out></th>
                                            <th scope="col"><c:out value="${blog.getBlogCategory()}"></c:out></th>
                                            <th scope="col"><c:out value="${blog.getBlogRatingPoint()}"></c:out></th>
                                            <th scope="col"><c:url var="mylink" value="MainController">
                                                    <c:param name="BlogID" value="${blog.getBlogId()}"></c:param>
                                                    <c:param name="action" value="ViewDetailBlog"></c:param>
                                                </c:url>
                                                <a href="${mylink}">View Detail</a></th>    

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>         
                <!-- Recent BLOG End -->    

                <!-- Recent RECIPE -->   
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-secondary text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Recent Recipes</h6>
                            <a href="">Show All</a>
                        </div>
                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr>
                                        <th scope="col">Recipe ID</th>
                                        <th scope="col">User ID</th>
                                        <th scope="col">Recipe Name</th>
                                        <th scope="col">Rating</th>
                                        <th scope="col">Category</th>
                                        <th scope="col">Action</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="recipe" items="${sessionScope.listRecentRecipe}">
                                        <tr>
                                            <th scope="col"><c:out value="${recipe.getRecipeId() }"></c:out></th>
                                            <th scope="col"><c:out value="${recipe.getUserId()}"></c:out></th>
                                            <th scope="col"><c:out value="${recipe.getRecipeName()}"></c:out></th>
                                            <th scope="col"><c:out value="${recipe.getRecipeRating()}"></c:out></th>
                                            <th scope="col"><c:out value="${recipe.getCategoryName()}"></c:out></th>
                                            <th scope="col"><c:url var="mylink" value="MainController">
                                                    <c:param name="RecipeID" value="${recipe.getRecipeId()}"></c:param>
                                                    <c:param name="action" value="ViewDetailRecipe" ></c:param>
                                                </c:url>
                                                <a href="${mylink}">View Detail</a>
                                            </th>


                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- Recent RECIPE end-->  

                <!-- Todo List -->  
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <div class="col-sm-12 col-md-6 col-xl-4">


                            <div class="h-100 bg-secondary rounded p-4">
                                <div class="d-flex align-items-center justify-content-between mb-4">
                                    <h6 class="mb-0">To Do List</h6>
                                    <a href="">Show All</a>
                                </div>
                                <div class="d-flex mb-2">
                                    <input class="form-control bg-dark border-0" type="text" placeholder="Enter task">
                                    <button type="button" class="btn btn-primary ms-2">Add</button>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox" checked>
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span><del>Short task goes here...</del></span>
                                            <button class="btn btn-sm text-primary"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center pt-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Content End -->


                <!-- Back to Top -->
                <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
            </div>

            <!-- JavaScript Libraries -->
            <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
            <script src="lib/chart/chart.min.js"></script>
            <script src="lib/easing/easing.min.js"></script>
            <script src="lib/waypoints/waypoints.min.js"></script>
            <script src="lib/owlcarousel/owl.carousel.min.js"></script>
            <script src="lib/tempusdominus/js/moment.min.js"></script>
            <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
            <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

            <!-- Template Javascript -->
            <script src="jsAdmin/main.js"></script>
    </body>
</html>
