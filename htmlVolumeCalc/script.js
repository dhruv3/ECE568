/**
 * Created with JetBrains WebStorm.
 * User: Dhruv
 * Date: 3/10/17
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */

$(document).ready(function(){

    $("input[type='radio']").on("change", function(){
        chkPanelChanged();
    });

    $("select").on("change", function(){
        shapeSelected(this.value);
    });

    $("#result").on("click", function(){
        calculateVol();
    });

    $("#reset").on("click", function(){
        resetForm();
    });
});

function resetForm(){
    $("#radiusIPBox").val("");
    $("#heightIPBox").val("");
}

function calculateVol(){
    var shape = $("select").val();
    var rad = $("#radiusIPBox").val();
    var height = $("#heightIPBox").val();
    var vol = 0;
    switch(shape){
        case "Cone":
            vol = (Math.PI * Math.pow(rad, 2) * height) / 3;
            break;
        case "Cylinder":
            vol = Math.PI * Math.pow(rad, 2) * height;
            break;
        case "Sphere":
            vol =   (4*Math.PI*Math.pow(rad, 3))/ 3;
            break;
    }
    if(isNaN(vol))
        alert("Incorrect input params");
    else{
        $("#volVal").text(vol);
        $("#radiusVal").text(rad);
        $("#shapeVal").text(shape);
        if(shape != "Sphere")
            $("#heightVal").text(height);
    }
}

function chkPanelChanged(){
    var $radioGrp = $("form :radio");
    if($radioGrp[0].checked == true){
        $("#unit").text("English");
        $("#unit1").text("ft");
        $("#unit2").text("ft");
        $("#unit3").text("ft");
    }
    else{
        $("#unit").text("SI");
        $("#unit1").text("m");
        $("#unit2").text("m");
        $("#unit3").text("m");
    }
}

function shapeSelected(shape){
    $("#shape").text(shape);
    $("#shapeVal").text(shape);
    if(shape == "Sphere")
        $("#heightIPBox").prop("disabled", true);
    else
        $("#heightIPBox").prop("disabled", false);
}

