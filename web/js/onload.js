
function submitStatus() {
    if (document.getElementById("processing") != null) {
        document.getElementById("processing").addEventListener("click", function (e) {
            sessionStorage.setItem("submitted", "true");
        }, false);
    }

    if (document.getElementById("delivering") != null) {
        document.getElementById("delivering").addEventListener("click", function (e) {
            sessionStorage.setItem("submitted", "true");
        }, false);
    }

    if (document.getElementById("completed") != null) {
        document.getElementById("completed").addEventListener("click", function (e) {
            sessionStorage.setItem("submitted", "true");
        }, false);
    }
    
    if (document.getElementById("canceled") != null) {
        document.getElementById("canceled").addEventListener("click", function (e) {
            sessionStorage.setItem("submitted", "true");
        }, false);
    }
    
    if (document.getElementById("all") != null) {
        document.getElementById("all").addEventListener("click", function (e) {
            sessionStorage.setItem("submitted", "true");
        }, false);
    }

    if (!sessionStorage.getItem("submitted")) {
        console.log("submitting");
    } else {
        document.getElementById("click-order").click();
        sessionStorage.removeItem("submitted");
    }
}

function submitCart() {
    if (document.getElementById("add-cart") != null) {
        document.getElementById("add-cart").addEventListener("click", function (e) {
            sessionStorage.setItem("carted", "true");
        }, false);
    }
    
    if (!sessionStorage.getItem("carted")) {
        console.log("carting");
    } else {
        document.getElementById("click-ingredient").click();
        sessionStorage.removeItem("carted");
    }
}

function findRecipe() {
    if (document.getElementById("search-recipe") != null) {
        document.getElementById("search-recipe").addEventListener("click", function (e) {
            sessionStorage.setItem("recipesearched", "true");
        }, false);
    }

    if (!sessionStorage.getItem("recipesearched")) {
        console.log("recipesearching");
    } else {
        document.getElementById("click-recipe").click();
        sessionStorage.removeItem("recipesearched");
    }
}

function findBlog() {
    if (document.getElementById("search-blog") != null) {
        document.getElementById("search-blog").addEventListener("click", function (e) {
            sessionStorage.setItem("blogsearched", "true");
        }, false);
    }

    if (!sessionStorage.getItem("blogsearched")) {
        console.log("blogsearching");
    } else {
        document.getElementById("click-blog").click();
        sessionStorage.removeItem("blogsearched");
    }
}

function findIngredient() {
    if (document.getElementById("search-ingre") != null) {
        document.getElementById("search-ingre").addEventListener("click", function (e) {
            sessionStorage.setItem("ingresearched", "true");
        }, false);
    }

    if (!sessionStorage.getItem("ingresearched")) {
        console.log("ingresearching");
    } else {
        document.getElementById("click-ingredient").click();
        sessionStorage.removeItem("ingresearched");
    }
}

function submit() {
    findRecipe();
    submitCart();
    submitStatus();
    findIngredient();
}