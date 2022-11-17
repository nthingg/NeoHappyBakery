filterSelectionAdmin("user-table")
function filterSelectionAdmin(c) {
    var x, i;
    x = document.getElementsByClassName("filterDivAdmin");
    if (c == "user-table") c = "user-table";
    for (i = 0; i < x.length; i++) {
        w3RemoveClass(x[i], "show-filter-admin");
        if (x[i].className.indexOf(c) > -1) w3AddClass(x[i], "show-filter-admin");
    }
}

function w3AddClass(element, name) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = name.split(" ");
    for (i = 0; i < arr2.length; i++) {
        if (arr1.indexOf(arr2[i]) == -1) { element.className += " " + arr2[i]; }
    }
}

function w3RemoveClass(element, name) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = name.split(" ");
    for (i = 0; i < arr2.length; i++) {
        while (arr1.indexOf(arr2[i]) > -1) {
            arr1.splice(arr1.indexOf(arr2[i]), 1);
        }
    }
    element.className = arr1.join(" ");
}

// Add active class to the current button (highlight it)
var btnContainer = document.getElementById("myBtnContainerAdmin");
var btns = btnContainer.getElementsByClassName("btn-admin");
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        var current = document.getElementsByClassName("active-filter-admin");
        current[0].className = current[0].className.replace("active-filter-admin", " ");
        this.className += "active-filter-admin";
    });
}