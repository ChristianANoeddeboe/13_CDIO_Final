jQuery(document).ready(function() {
    loadMenu(function(rolle){
        $(".text-muted").text(rolle); // Kalder loadMenu fra menu.js, og den kalder så tilbage, der tilfoejer den rolle der er valgt som tekst på skaermen.
    });
});