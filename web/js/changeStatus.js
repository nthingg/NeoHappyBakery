function change() {
    var origin = document.getElementById("origin-stat");
    var change = document.getElementById("change-stat");
    origin.style.display = "none";
    change.style.display = "block";
}

function cancel() {
    var origin = document.getElementById("origin-stat");
    var change = document.getElementById("change-stat");
    origin.style.display = "block";
    change.style.display = "none";
}