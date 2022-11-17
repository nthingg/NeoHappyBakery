<%-- 
    Document   : BlogUpdate
    Created on : Oct 12, 2022, 3:22:24 PM
    Author     : thinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="blog" value="${sessionScope.blogUpdateBlog}"/>
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
                init_instance_callback: function (editor) {
                    editor.setContent('${blog.getBlogDescription()}');
                }
            });
        </script>
    </head>
    <body>
        <div class="mapping grad">
            <p class="breadcrums"><a href="HomePage.jsp">Home</a>/<a href="StoreDetail.jsp">Profile</a></p>
            <h1 class="mapping-heading">Update Blog</h1>
        </div>
        <div class="create-blog-container">
            <div class="create-blog-form">
                <div class="login-header">
                    <h3 class="fnt-30 mg-bot-20 fnt-wei-15">Update Blog</h3>
                    <p class="fnt-20">Fill in the form to update the blog</p>
                </div>
                <form action="MainController" method="post" enctype="multipart/form-data">
                    <div class="create-blog-body">
                        <div class="form-group-blog">
                            <label for="fullname" class="fnt-wei-bold">Blog Title:</label>
                            <input id="fullname" name="title" type="text" placeholder="Blog title..." value="${blog.getBlogTitle()}">
                        </div>
                        <div>
                            <label for="img" class="fnt-wei-bold">Recipe Image:</label>
                            <input class="img" id="img" name="img" type="file" accept="image/*" placeholder="Choose image">
                        </div>
                        <div class="cate-recipe">
                            <label for="phone" class="fnt-wei-bold">Category:</label>
                            <p class="blog-detail-p">${blog.getBlogCategory()}</p>
                        </div>
                        <div>
                            <label for="phone" class="fnt-wei-bold rep-des blog-label">Blog Note:</label>
                            <textarea class="blog-note" name="blognote" id="" cols="30" rows="10" placeholder="Blog introduction...">${blog.getBlogNote()}</textarea>
                        </div>
                        <div>
                            <label for="phone" class="fnt-wei-bold rep-des">Recipe Description:</label>
                            <textarea id="mytextarea" class="mytextarea" name="description" id="" cols="30" rows="10" placeholder="Blog description..."></textarea>
                        </div>
                    </div>
                    <input type="hidden" name="blogId" value="${blog.getBlogId()}">
                    <button class="create-blog" type="submit" value="UpdateBlog" name="action">Update Blog</button>
                </form>
            </div>
        </div>
    </body>
</html>
