<%-- 
    Document   : CommentManagement
    Created on : Oct 28, 2022, 1:50:26 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        <link href="cssAdmin/adminpage.css" rel="stylesheet">
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}"></c:set>
            <div class="container-fluid position-relative d-flex p-0">
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
                        <a href="MainController?action=AdminLogin" class="nav-item nav-link "><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Account</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="MainController?action=manageAccount" class="dropdown-item nav-item ">Block/UnBlock</a>

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
                                    <a href="MainController?action=getAllComments" class="<%= (request.getParameter("type").equals("view"))? "dropdown-item nav-link active":"dropdown-item"  %>">View all</a>
                                    <a href="MainController?action=getReportedComment" class="<%= (request.getParameter("type").equals("reported"))? "dropdown-item nav-link active":"dropdown-item"  %>">Reported</a>

                                </div>
                            </div>
                        </div>

                    </div>
                </nav>
            </div>


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
                                        <img class="rounded-circle" src="img/userdefault.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/userdefault.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/userdefault.jpg" alt="" style="width: 40px; height: 40px;">
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
                                <span class="d-none d-lg-inline-flex">Notification</span>
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
                <div class="row g-4 bg-cl-grey">
                    <div class="col-sm-12 col-xl-12">
                        <div class="col-12">
                            <div class="bg-secondary rounded h-100 p-4">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th scope="col">Comment ID</th>
                                                <th scope="col">Recipe ID</th>
                                                <th scope="col">User Name</th>
                                                <th scope="col">Rating</th>
                                                <th scope="col">Description</th>
                                                <th scope="col">Action</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                if ("view".equals(request.getParameter("type"))) { %>
                                            <c:forEach var="commentRecipe" items="${sessionScope.listCommentRecipe}">
                                                <tr>
                                                    <th scope="col"><c:out value="${commentRecipe.getCommentId() }"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getRatedRecipeId()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getUserName()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getRatingPoint()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getCmtDescription()}"></c:out></th>
                                                        <th scope="col"><a href="#">View Detail</a></th>  
                                                        <th></th>
                                                    </tr>
                                            </c:forEach>
                                            <c:forEach var="commentBlog" items="${sessionScope.listCommentBlog}">
                                                <tr>
                                                    <th scope="col"><c:out value="${commentBlog.getCommentId() }"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getRatedBlogId()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getUserName()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getRatingPoint()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getCmtDescription()}"></c:out></th>
                                                        <th scope="col"><a href="#">View Detail</a></th> 
                                                        <th></th>
                                                    </tr>
                                            </c:forEach>
                                            <%  } else if ("reported".equals(request.getParameter("type"))) {
                                            %>
                                            <c:forEach var="commentRecipe" items="${sessionScope.ListReportedCommentRecipe}">
                                                <tr>
                                                    <th scope="col"><c:out value="${commentRecipe.getCommentId() }"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getRatedRecipeId()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getUserName()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getRatingPoint()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentRecipe.getCmtDescription()}"></c:out></th>
                                                        <th scope="col">
                                                        <c:url var="mylink" value="MainController">
                                                            <c:param name="CommentRecipeID" value="${commentRecipe.getCommentId()}"></c:param>
                                                            <c:param name="action" value="ViewDetailCommentRecipe"></c:param>
                                                        </c:url>
                                                        <a href="${mylink}">View Detail</a></th>    
                                                    <th scope="col"><c:url var="mylink" value="MainController">
                                                            <c:param name="CommentRecipeID" value="${commentRecipe.getCommentId()}"></c:param>
                                                            <c:param name="typeChange" value="delete"></c:param>
                                                            <c:param name="action" value="ChangeStatusCommentRecipe"></c:param>
                                                        </c:url>
                                                        <a href="${mylink}" >Delete</a>
                                                    </th>
                                                </tr>
                                            </c:forEach>
                                            <c:forEach var="commentBlog" items="${sessionScope.ListReportedCommentBlog}">
                                                <tr>
                                                    <th scope="col"><c:out value="${commentBlog.getCommentId() }"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getRatedBlogId()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getUserName()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getRatingPoint()}"></c:out></th>
                                                    <th scope="col"><c:out value="${commentBlog.getCmtDescription()}"></c:out></th>
                                                        <th scope="col">
                                                        <c:url var="mylink" value="MainController">
                                                            <c:param name="CommentBlogID" value="${commentBlog.getCommentId()}"></c:param>
                                                            <c:param name="action" value="ViewDetailCommentBlog"></c:param>
                                                        </c:url>
                                                        <a href="${mylink}">View Detail</a></th>    
                                                    <th scope="col"><c:url var="mylink" value="MainController">
                                                            <c:param name="CommentBlogID" value="${commentBlog.getCommentId()}"></c:param>
                                                            <c:param name="typeChange" value="delete"></c:param>
                                                            <c:param name="action" value="ChangeStatusCommentBlog"></c:param>
                                                        </c:url>
                                                        <a href="${mylink}" >Delete</a>
                                                    </th>
                                                </tr>
                                            </c:forEach>
                                            <%
                                                }
                                            %>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Table End -->


                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-secondary rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#">Your Site Name</a>, All Right Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                                Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                                <br>Distributed By: <a href="https://themewagon.com" target="_blank">ThemeWagon</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
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
