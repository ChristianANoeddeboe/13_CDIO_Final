var id,id2,value,statuss;
$(document).ready(function () {
	showLoader();
	loadProducts();
	loadMenu();
	clickAddHandler();
	clickUpdateHandler();
	clickDeleteHandler();
	clickDismissDeleteHandler();
	addEnterHandler();
	clickShowMoreHandler();
	clickAddKompHandler();
	clickDeleteKompHandler();
	clickUpdateKompHandler();
});

function clickUpdateKompHandler(){ //Tilfoejer funktion til at bekraefte opdateringen af et produkt komponent, via en modal.
	$(".btn-primaryUpdateKomp").click(function () {
		put('rest/produktbatch/komponent',JSON.stringify({
			pbId: id.split("_")[0],
			rbId: id.split("_")[1],
			tara: $(("#" + id.split("_")[0] + "_" + id.split("_")[1] + "_tara"))["0"].value,
			netto: $(("#" + id.split("_")[0] + "_" + id.split("_")[1] + "_netto"))["0"].value,
			oprId: $(("#" + id.split("_")[0] + "_" + id.split("_")[1] + "_operatoer"))["0"].value
		}),
		function(){
			$('#updateKompModal').modal('hide');
			$.notify("Produktbatch komponenten blev opdateret", "success");
			loadProductBatchKomps();
		},
		function(){
			$('#updateKompModal').modal('hide');
			$.notify(data.responseText, "error");
			loadProductBatchKomps();
		});
	});
}

function clickDeleteKompHandler(){ //Tilfoejer funktion til at bekraefte delete af et produkt komponent, via en modal.
	$(".btn-primaryDeleteKomp").click(function () {
		Delete('rest/produktbatch/komponent/'+id2.split("_")[0]+"/"+id2.split("_")[1],
		function(data){
			$('#deleteKompModal').modal('hide');
			$.notify("Produktbatch komponenten blev slettet", "success");
			loadProductBatchKomps();
		},
		function(data){
			$('#deleteKompModal').modal('hide');
			$.notify(data.responseText, "error");
			loadProductBatchKomps();
		})
	});
}

function clickAddKompHandler(){ //Funktion til at tilfoeje den indtastede information når et produkt komponent tilfoejes.
	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputPBIDKomp")["0"].value = id;
	});
	$(".btn-primaryAddKomp").click(function () {
		post('rest/produktbatch/komponent',JSON.stringify({
			pbId: $("#inputPBIDKomp")["0"].value,
			rbId: $("#inputRBIDKomp")["0"].value,
			tara: $("#inputTaraKomp")["0"].value,
			netto: $("#inputNettoKomp")["0"].value,
			oprId: $("#inputOprKomp")["0"].value
		}),function(data){
			$('#addKompModal').modal('hide');
			$.notify("Produktbatch komponenten blev operettet", "success");
			loadProductBatchKomps();
		},function(data){
			$('#addKompModal').modal('hide');
			$.notify("Fejl ved oprettelse af produktbatch komponenten", "error");
			loadProduktBatchKomps()
		});
	});
}

function clickShowMoreHandler(){ //Aabner den tilsvarende modal for det produkt vi har valgt at se produkt komponenter for.
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadProductBatchKomps();
	});
}

function loadProductBatchKomps(){
	get('rest/produktbatch/komponent/'+id.split("_")[0],
			function(data){
		clearProduktBatchKompTable();
		appendToSubTable(data)
		generateClickForSubTable();
	}, function(data){
		$.notify(data.responseText, "error");
		
	});
}

function addEnterHandler(){ //Tilfoejer funktionalitet til at aabne update modal for det produkt der er markeret, naar 'Enter' trykkes.
	$(document).keypress(function (e) {
		if (e.which === enterkey) {
			id = e.target.id;
			value = e.target.value;
			var res = id.split("_");
			if (res.length === 2) {
				$('#updateModal').modal('show');
			} else {
				$('#updateKompModal').modal('show');
			}
		}

	});
}

function clickDismissDeleteHandler(){
	$(".btn-secondaryDelete").click(function () {
		$('#deleteModal').modal('hide');
	});
}

function clickDeleteHandler(){ //Tilfoejer funktion til at slette et produkt naar slet bekraeftes.
	$(".btn-primaryDelete").click(function (e) {
		Delete('rest/produktbatch/'+id,
		function(data){
			$('#deleteModal').modal('hide');
			$.notify("Produktbatchet blev slettet", "success");
			loadProducts();
		},
		function(data){
			$('#deleteModal').modal('hide');
			$.notify(data.responseText, "error");
			loadProducts();
		});
	});
}

function clickAddHandler(){ //Tilfoejer funktion til at tilfoeje et produkt, naar tilfoej knappen trykkes.
	$(".btn-primaryAdd").click(function () {
		post('rest/produktbatch',
				JSON.stringify({
			pbId: $("#inputID")["0"].value,
			status: $("input:checked").val(),
			receptId: $("#inputReceptId")["0"].value
		}),
		function(data){
			$('#addModal').modal('hide');
			$.notify("Produkt batchet blev operettet", "success");
			loadProducts();
		},
		function(data){
			$('#addModal').modal('hide');
			$.notify("Fejl ved oprettelse af produkt batchet", "error");
			loadProducts();
		});
	});
}

function clickUpdateHandler(){ //Funktion til at haandtere opdatering af et produkt.
	$(".btn-primaryUpdate").click(function () {
		put('rest/produktbatch',
		JSON.stringify({
			pbId: id.split("_")[0],
			status: $("#" +  id.split("_")[0] + "_status")["0"].value,
			receptId: $("#" +  id.split("_")[0] + "_recept")["0"].value
		}),
		function(data){
			$('#updateModal').modal('hide');
			$.notify("ProduktBatchet blev opdateret", "success");
			loadProducts();
		}, 
		function(data){
			$('#updateModal').modal('hide');
			$.notify(data.responseText, "error");
			loadProducts();
		});
	});
}

function loadProducts() {
	getEnum('rest/enum/status_produktbatch',function(){
		get('rest/produktbatch',function(data){
			clearProduktTable();
			appendToTable(data);
			generateClickForTable();
			hideLoader()
		},function(data){
			$.notify(data.responseText, "error");
		});
	},function(data){
		$.notify(data.responseText, "error");
	});
}

function showLoader(){
	$("#produktAdminTable").hide();
	$(".loader").show();
}

function hideLoader(){
	$(".loader").hide();
	$("#produktAdminTable").show();
}

function appendToTable(data){
	$.each(data, function (i, element) {
		$('#produktAdminTable').children().append(generateProduktHTML(data[i]));
	});
}

function appendToSubTable(data){
	$.each(data, function (i, element) {
		$('#produktBatchKompTable').append(generateProduktBatchKompHTML(data[i]));

	});
}

function generateClickForSubTable(){ //Funktion til at vise modal for opdatering eller slet, baseret på hvilken knap trykkes.
	$(".sletKomp").click(function (e) {
		id2 = e.target.id;
		$('#deleteKompModal').modal('show');
		e.preventDefault();
	});
	$(".updateKomp").click(function(e){
		id = e.target.id;
		$('#updateKompModal').modal('show');
	});
}

function generateClickForTable(){ //Funktion til at vise den tilsvareende modal for den knap der trykkes paa.
	$(".slet").click(function (e) {
		id = e.target.id;
		$('#deleteModal').modal('show');
		e.preventDefault();
	});
	$(".vis").click(function (e) {
		id = e.target.id;
		$('#showMoreModal').modal('show');
	});
	$(".update").click(function(e){
		id = e.target.id;
		$('#updateModal').modal('show');
	});
}

function generateProduktHTML(produkt) { //Genererer indholdet i produkt tabellen, ud fra data fodret fra vores system.
	var status = new Array();
	status.push(produkt.status);

	statuss.forEach(function(st) {
		if (st !== status[0]) {
			status.push(st);
		}
	});

	return '<tr><th scope ="row">' + produkt.pbId + '</th>' +
	'<td><select name="' + produkt.pbId + '_status" id="' + produkt.pbId + '_status"><option value="' + status[0] + '">' + status[0] + '</option><option value="' + status[1] + '">' + status[1] + '</option><option value="' + status[2] + '">' + status[2] + '</option></select></td></td>' +
	'<td><input type="text" id = "' + produkt.pbId + "_recept" + '" class="form-control-plaintext" value="' + produkt.receptId + '"></td></td>' +
	'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+produkt.pbId+'"></i></button>' + '</td>' +
	'<td><button type="button" id = "'+produkt.pbId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+produkt.pbId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary slet"><i class="fas fa-trash-alt" id = "' + produkt.pbId + '"></i></button>' + '</td>' +
	'</td></tr>';
}

function generateProduktBatchKompHTML(produktKomp) { //Genererer indholdet i produkt komponent tabellen, ud fra data fodret fra vores system.
	return '<tr><th scope ="row">' + produktKomp.pbId + '</th>' +
	'<th scope = "row">' + produktKomp.rbId + '</th>' +
	'<td><input type="text" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + "_tara" + '" class="form-control-plaintext" value="' + produktKomp.tara + '"></td></td>' +
	'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_netto" + '" class="form-control-plaintext" value="' + produktKomp.netto + '"></td></td>' +
	'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_operatoer" + '" class="form-control-plaintext" value="' + produktKomp.oprId + '"></td></td>' +
	'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary updateKomp"><i class="fas fa-save" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>'+'</td>' +
	'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary sletKomp"><i class="fas fa-trash-alt" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>' + '</td>' +
	'</td></tr>';
}

function clearProduktTable() {
	$("#produktTable>tbody").empty();
}

function clearProduktBatchKompTable() {
	$("#produktBatchKompTable>tbody").empty();
}