jQuery(document).ready(function() {
    var rolle;
    loadMenu();
    rolle = localStorage.getItem('rolle');
    $(".text-muted").text(rolle);
});