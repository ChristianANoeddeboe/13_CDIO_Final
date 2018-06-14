function loadMenu(id,callbackfunc){
    var rolle;
	$("#menuLoader").load("menu.html", null, function () { //Tilføjer menu.html til nuvaerende side, og fjerner de menu items, som den valgte rolle ikke har tilladelse til.
		rolle = localStorage.getItem('rolle');
		if (rolle === "Produktionsleder") {
			$("#receptAdmin").hide();
			$("#operatoerAdmin").hide()
		}
		if (rolle === "Farmaceut") {
			$("#operatoerAdmin").hide();
		}
		if(id === 1) {
			callbackfunc(rolle)
		}
	});
}