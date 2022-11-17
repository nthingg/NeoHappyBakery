<%-- 
    Document   : BlogCreate
    Created on : Oct 12, 2022, 12:45:36 PM
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="tinymce/js/tinymce/tinymce.min.js"></script>
        <script type="text/javascript">
            tinymce.init({
                selector: '#mytextarea',
                plugins: 'advlist autolink lists link image charmap preview anchor pagebreak code visualchars wordcount',

            });
        </script>
    </head>
    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="HomePage.jsp">Home</a>/<a href="StoreDetail.jsp">Profile</a></p>
            <h1 class="mapping-heading">Create Blog</h1>
        </div>
        <div class="create-blog-container" >
            <div class="create-blog-form">
                <div class="login-header">
                    <h3 class="fnt-30 mg-bot-20 fnt-wei-15">Create new blog</h3>
                    <p class="fnt-20">Fill all informations in the form to create a new blog</p>
                </div>
                <form action="MainController" method="post" enctype="multipart/form-data">
                    <div class="create-blog-body">
                        <div class="form-group-blog">
                            <label for="fullname" class="fnt-wei-bold">Blog Title<span class="cl-red">*</span>:</label>
                            <input id="fullname" name="blogtitle" type="text" placeholder="Blog title..." required>
                        </div>
                        <div>
                            <label for="img" class="fnt-wei-bold">Recipe Image<span class="cl-red">*</span>:</label>
                            <input class="img" id="img" name="img" type="file" accept="image/*" placeholder="Choose image">
                        </div>
                        <div class="cate-recipe">
                            <label for="phone" class="fnt-wei-bold">Category<span class="cl-red">*</span>:</label>
                            <select name="category" id="">
                                <c:forEach var="category" items="${sessionScope.listBlogCategory}">
                                    <option value="${category.getCategoryId()}">${category.getCategoryName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label for="phone" class="fnt-wei-bold rep-des blog-label">Blog Note<span class="cl-red">*</span>:</label>
                            <textarea class="blog-note" name="blognote" id="" cols="30" rows="10" placeholder="Blog introduction..."></textarea>
                        </div>
                        <div>
                            <label for="phone" class="fnt-wei-bold rep-des">Recipe Description<span class="cl-red">*</span>:</label>
                            <textarea id="mytextarea" class="mytextarea" name="description" id="" cols="30" rows="10" placeholder="Blog description..."></textarea>
                        </div>
                    </div>
                    <button class="create-blog" type="submit" value="createBlog" name="action">Create Blog</button>
                </form>
            </div>
        </div>
    </body>
</html>
<c:import url="Footer.jsp"/>
