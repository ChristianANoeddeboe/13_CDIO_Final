function loadMenu(id,callbackfunc){
    var rolle;
	$("#menuLoader").load("menu.html", null, function () { //Tilføjer menu.html til nuvaerende side, og fjerner de menu items, som den valgte rolle ikke har tilladelse til.
		rolle = localStorage.getItem('rolle');
		if (rolle == "Produktionsleder") {
			$("#receptAdmin").hide();
			$("#brugerAdmin").hide()
		}
		if (rolle == "Farmaceut") {
			$("#brugerAdmin").hide();
		}
		if(id == 1) {
			callbackfunc(rolle)
		}
	});
}