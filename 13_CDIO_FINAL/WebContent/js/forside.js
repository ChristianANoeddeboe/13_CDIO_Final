jQuery(document).ready(function() {

    var rolle;

    $("#menuLoader").load("menu.html", null, function () {
        rolle = localStorage.getItem('rolle');
        $(".text-muted").text(rolle);
        if (rolle === "Laborant") {
            $("#operatoerAdmin").hide();
            $("#receptAdmin").hide();
            $("#raavareAdmin").hide();
        }
        if (rolle === "Værksfører") {
            $("#receptAdmin").hide();
            $("#operatoerAdmin").hide()
        }
        if (rolle === "Pharmaceut") {
            $("#operatoerAdmin").hide();
        }
    });




});