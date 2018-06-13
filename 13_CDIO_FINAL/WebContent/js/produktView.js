var id;
$("#produktAdminTable").hide();
$(document).ready(function () {
	var id2,value,statuss;
	const enterkey = 13;
	$(".loader").show();
	loadProducts();

	$("#menuLoader").load("menu.html", null, function () {
		rolle = localStorage.getItem('rolle');
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

	$(".btn-secondaryDelete").click(function () {
		$('#deleteModal').modal('hide');
	});

	$('#showMoreModal').on('shown.bs.modal', function () {
		loadProduktBatchKomps();
	});

	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputPBIDKomp")["0"].value = id;
	});

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

});
function generateProduktHTML(produkt) {
	var status = new Array();
	status.push(produkt.status);

	statuss.forEach(function(st) {
		if (st !== status[0]) {
			status.push(st);
		}
	});

	return '<tr><td scope ="row">' + produkt.pbId + '</td>' +
	'<td><select name="' + produkt.pbId + '_status" id="' + produkt.pbId + '_status"><option value="' + status[0] + '">' + status[0] + '</option><option value="' + status[1] + '">' + status[1] + '</option><option value="' + status[2] + '">' + status[2] + '</option></select></td></td>' +
	'<td><input type="text" id = "' + produkt.pbId + "_recept" + '" class="form-control-plaintext" value="' + produkt.receptId + '"></td></td>' +
	'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+produkt.pbId+'"></i></button>' + '</td>' +
	'<td><button type="button" id = "'+produkt.pbId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+produkt.pbId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "' + produkt.pbId + '"></i></button>' + '</td>' +
	'</td></tr>';
}
function generateProduktBatchKompHTML(produktKomp) {
	return '<tr><td scope ="row">' + produktKomp.pbId + '</td>' +
	'<td scope = "row">' + produktKomp.rbId + '</td>' +
	'<td><input type="text" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + "_tara" + '" class="form-control-plaintext" value="' + produktKomp.tara + '"></td></td>' +
	'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_netto" + '" class="form-control-plaintext" value="' + produktKomp.netto + '"></td></td>' +
	'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_operatoer" + '" class="form-control-plaintext" value="' + produktKomp.oprId + '"></td></td>' +
	'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary updateKomp"><i class="fas fa-save" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>'+'</td>' +
	'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>' + '</td>' +
	'</td></tr>';
}

function clearProduktTable() {
	$("#produktTable>tbody").empty();
}

function clearProduktBatchKompTable() {
	$("#produktBatchKompTable>tbody").empty();
}