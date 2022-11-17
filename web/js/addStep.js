function addStep() {
    let html = '';
    html += '<div class="step-father">';
    html += '<textarea class="step" name="step" cols="30" rows="10" placeholder="Input step description"></textarea>';
    html += '<a class="remove-step-btn" onclick="remove(this)"><i class="fa fa-minus-circle"></i></a>';
    html += '</div>';
    $('#step-recipe').append(html);
};

function addIngredient() {
    let html = '';
    html += '<div class="form-group-ingre">';
    html += '<input name="ingredients" id="phone" type="text" placeholder="Input ingredient name">';
    html += '<a class="remove-ingre-btn" onclick="remove(this)"><i class="fa fa-minus-circle"></i></a>';
    html += '</div>';
    $('#ingredient-recipe').append(html);
};

function remove(el) {
    var element = el.parentElement;
    element.remove();
}

