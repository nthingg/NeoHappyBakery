<%-- 
    Document   : RecipeUpdate
    Created on : Oct 12, 2022, 3:26:17 PM
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

<c:set var="repUpdateListStep" value="${sessionScope.repUpdateListStep}"/>
<c:set var="repUpdateListIngre" value="${sessionScope.repUpdateListIngre}"/>
<c:set var="recipe" value="${sessionScope.repUpdate}"/> 

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
                    editor.setContent('${recipe.getRecipeDesciption()}');
                }
            });
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    </head>
</head>
<body>


    <div class="mapping grad">
        <p class="breadcrums"><a href="">Home</a>/<a href="">Recipe</a></p>
        <h1 class="mapping-heading">Update Recipe</h1>
    </div>
    <div class="recipe-create-container">
        <div class="recipe-create-form">
            <div class="login-header">
                <h3 class="fnt-30 mg-bot-20 ">Update Recipe</h3>
                <p class="fnt-20">Fill in the form to update the recipe
                </p>
            </div>
            <form action="MainController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="recipeId" value="${recipe.getRecipeId()}"> 
                <h3 class="lining-header">Basic Information</h3>  
                <div class="recipe-create-body">
                    <div class="box-father">
                        <div class="box">
                            <div class="form-group">
                                <label for="fullname" class="fnt-wei-bold">Recipe Name<span class="cl-red">*</span>:</label>
                                <p class="recipe-detail-p">${recipe.getRecipeName()}</p>
                            </div>

                            <div>
                                <label for="phone" class="fnt-wei-bold cate">Category:</label>
                                <p class="recipe-detail-cate">${recipe.getCategoryName()}</p>
                            </div>
                            <div>
                                <label for="phone" class="fnt-wei-bold cate">Country:</label>
                                <p class="recipe-detail-cate">${recipe.getCountryName()}</p>
                            </div>
                        </div>
                        <div class="box">
                            <div>
                                <label for="img" class="fnt-wei-bold">Recipe Image:</label>
                                <input class="img" id="img" name="img" type="file" accept="image/*" placeholder="Choose image">
                            </div>
                            <div class="form-group">
                                <label for="mail" class="fnt-wei-bold">Prepare Time (mins):</label>
                                <input id="mail" name="preptime" type="number" placeholder="Input prepare time" value="${recipe.getPrepTime()}">
                            </div>
                            <div class="form-group">
                                <label for="pass" class="fnt-wei-bold">Cook Time (mins):</label>
                                <input id="pass" name="cooktime" type="text" placeholder="Input cook time" value="${recipe.getCookTime()}">
                            </div>
                            <div class="form-group">
                                <label for="phone" class="fnt-wei-bold">Yields:</label>
                                <input id="phone" name="yields" type="text" placeholder="Input total yields" value="${recipe.getYields()}">
                            </div>
                        </div>
                    </div>
                    <div>
                        <label for="phone" class="fnt-wei-bold rep-des">Recipe Description:</label>
                        <textarea id="mytextarea" class="mytextarea" name="description" id="" cols="30" rows="10"></textarea>
                    </div>
                </div>

                <h3 class="lining-header-bor bord-top ">Ingredient Information</h3>   

                <div  class="ingredient-recipe">
                    <c:forEach var="ingredient" items="${repUpdateListIngre}">
                        <div class="form-group-ingre">
                            <p class="recipe-detail-ingre">${ingredient.getIngredientName()}</p>
                        </div>
                    </c:forEach>
                </div>
                

                <h3 class="lining-header-bor bord-top ">Step Information</h3>    

                <div class="step-recipe">
                    <c:forEach var="step" items="${repUpdateListStep}">
                        <div>
                            <textarea class="step" name="step" cols="30" rows="10">${step.getStepDescription()}</textarea>
                        </div>
                    </c:forEach>
                    <div  id="step-recipe"></div>
                </div>
                <button type="button" class="add-step" onclick="addStep()">Add More Step</button>

                <button class="create-recipe" type="submit" value="UpdateRecipe" name="action">Update Recipe</button>
            </form>
        </div>
    </div>

    <script src="js/addStep.js"></script>
</body>
</html>

<c:import url="Footer.jsp"/>
