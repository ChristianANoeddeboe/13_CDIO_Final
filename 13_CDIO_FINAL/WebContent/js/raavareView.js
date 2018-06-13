
var id;
$(document).ready(function() {
	$("#raavareAdminTable").hide();
	var id2,value;
	const enterkey = 13;
	$(".loader").show();
	loadRaavare();
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

	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
	
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadRaavareBatch();
	});

	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputRBIDKomp")["0"].value = id;
	});

	$(document).keypress(function(e) {
		if(e.which === enterkey) {
			id = e.target.id;
			value = e.target.value;
			var res = id.split("_");
			if(res.length === 2){
				$('#updateModal').modal('show');
			}else{
				$('#updateKompModal').modal('show');
			}
		}
	});
	
});


function generateRaavareHTML(raavare) {
	return 	'<tr><td scope ="row">' + raavare.raavareId + '</td>' +
	'<td><input type="text" id = "'+raavare.raavareId +"_navn"+'" class="form-control-plaintext" value="' + raavare.raavareNavn + '"></td></td>' +
	'<td><input type="text" id = "'+raavare.raavareId +"_leverandør"+'" class="form-control-plaintext" value="' + raavare.leverandoer + '"></td></td>' +
	'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
    '<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function generateRaavareBatchKompHTML(raavareBatch) {
	return 	'<tr><td scope ="row">' + raavareBatch.rbId + '</td>' +
	'<td scope = "row">'+raavareBatch.raavareId + '</td>' +
	'<td><input type="text" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+"_mængde"+'" class="form-control-plaintext" value="' + raavareBatch.maengde + '"></td></td>' +
    '<td><button type="button" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'" class="btn btn-primary updateKomp"><i class="fas fa-save" id = "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"></i></button>'+'</td>' +
	'<td><button type="button" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"></i></button>'+'</td>' +
	'</td></tr>';
};
function clearRaavareTable(){
	$("#raavareAdminTable tbody").empty();
};

function clearRaavareBatchTable(){
	$("#raavareBatchTable tbody").empty();
};