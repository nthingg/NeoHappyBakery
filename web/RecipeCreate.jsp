<%-- 
    Document   : RecipeCreate
    Created on : Oct 12, 2022, 3:27:30 PM
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
        <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    </head>
</head>
<body>

    <c:set var="addRepCates" value="${sessionScope.addRepCates}"/>
    <c:set var="addRepCountry" value="${sessionScope.addRepCountry}"/>

    <div class="mapping grad">
        <p class="breadcrums"><a href="">Home</a>/<a href="">Profile</a></p>
        <h1 class="mapping-heading">Create Recipe</h1>
    </div>
    <div class="recipe-create-container">
        <div class="recipe-create-form">
            <div class="login-header">
                <h3 class="fnt-30 mg-bot-20 ">Create New Recipe</h3>
                <p class="fnt-20">Fill in the form to create new Recipe</p>
            </div>
            <form action="MainController" method="post" enctype="multipart/form-data"> 
                <h3 class="lining-header">Basic Information</h3>  
                <div class="recipe-create-body">
                    <div class="box-father">
                        <div class="box">
                            <div class="form-group">
                                <label for="fullname" class="fnt-wei-bold">Recipe Name<span class="cl-red">*</span>:</label>
                                <input name="repname" id="fullname" type="text" placeholder="Input Recipe name" required>
                            </div>
                            <div>
                                <label for="img" class="fnt-wei-bold">Recipe Image<span class="cl-red">*</span>:</label>
                                <input class="img" id="img" name="img" type="file" accept="image/*" placeholder="Choose image">
                            </div>
                            <div class="cate-recipe">
                                <label for="phone" class="fnt-wei-bold">Category<span class="cl-red">*</span>:</label>
                                <select name="category">
                                    <c:forEach var="category" items="${addRepCates}">
                                        <option value="${category.getCategoryId()}">${category.getCategoryName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="cate-recipe cate-recipe-mgn" >
                                <label for="phone" class="fnt-wei-bold">Country<span class="cl-red">*</span>:</label>
                                <select name="country">
                                    <c:forEach var="country" items="${addRepCountry}">
                                        <option value="${country.getCountryId()}">${country.getCountryName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="box">
                            <div class="form-group">
                                <label for="mail" class="fnt-wei-bold">Prepare Time (mins)<span
                                        class="cl-red">*</span>:</label>
                                <input name="preptime" id="mail" type="number" placeholder="Input prepare time" required>
                            </div>
                            <div class="form-group">
                                <label for="pass" class="fnt-wei-bold">Cook Time (mins)<span
                                        class="cl-red">*</span>:</label>
                                <input name="cooktime" id="pass" type="number" placeholder="Input cook time" required>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="fnt-wei-bold">Yields<span class="cl-red">*</span>:</label>
                                <input name="yield" id="phone" type="number" placeholder="Input number of serving" required>
                            </div>
                        </div>
                    </div>
                    <div>
                        <label for="phone" class="fnt-wei-bold rep-des">Recipe Description<span class="cl-red">*</span>:</label>
                        <textarea id="mytextarea" class="mytextarea" name="description" id="" cols="30" rows="10"></textarea>
                    </div>
                </div>

                <h3 class="lining-header-bor bord-top ">Ingredient Information</h3>   

                <div id="ingredient-recipe" class="ingredient-recipe">
                    <div class="form-group-ingre">
                        <input name="ingredients" id="phone" type="text" placeholder="Input ingredient name" required>
                    </div>
                    <div class="form-group-ingre">
                        <input name="ingredients" id="phone" type="text" placeholder="Input ingredient name" required>
                    </div>
                    <div class="form-group-ingre">
                        <input name="ingredients" id="phone" type="text" placeholder="Input ingredient name" required>
                    </div>
                    <div id="ingredient-recipe"></div>
                </div>
                
                <button type="button" class="add-ingre" onclick="addIngredient()">Add More Ingredient</button>

                <h3 class="lining-header-bor bord-top ">Step Information</h3>    

                <div class="step-recipe">
                    <div class="step-father">
                        <textarea class="step" name="step" cols="30" rows="10" placeholder="Input step description"></textarea>
                    </div>

                    <div class="step-father">
                        <textarea class="step" name="step" cols="30" rows="10" placeholder="Input step description"></textarea>
                    </div>

                    <div class="step-father">
                        <textarea class="step" name="step" cols="30" rows="10" placeholder="Input step description"></textarea>
                    </div>
                    <div id="step-recipe"></div>
                </div>
                
                <button type="button" class="add-step" onclick="addStep()">Add More Step</button>

                <button class="create-recipe" type="submit" value="CreateRecipe" name="action">Create Recipe</button>
            </form>
        </div>
    </div>

    <script src="js/addStep.js"></script>
</body>
</html>
<c:import url="Footer.jsp"/>
