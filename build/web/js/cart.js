function change() {
    var origin = document.getElementById("origin-address");
    var change = document.getElementById("change-address");
    origin.style.display = "none";
    change.style.display = "block";
}

function cancel() {
    var origin = document.getElementById("origin-address");
    var change = document.getElementById("change-address");
    origin.style.display = "block";
    change.style.display = "none";
}
