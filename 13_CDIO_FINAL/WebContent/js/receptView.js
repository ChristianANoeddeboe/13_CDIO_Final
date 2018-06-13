var id;
$("#receptAdminTable").hide();
$(document).ready(function() {
	$(".loader").show();
	var id2,value,rolle;
    const enterkey = 13;
	loadRecepts();

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
	
	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputReceptIDKomp")["0"].value = id;
	});

	$('#showMoreModal').on('shown.bs.modal', function () {
		loadReceptKomps();
	});
	
	$(document).keypress(function(e) {
		if(e.which == enterkey) {
			id = e.target.id;
			value = e.target.value;
			var res = id.split("_");
			if(res.length == 1){
				$('#updateModal').modal('show');
			}else{
				$('#updateKompModal').modal('show');
			}
		}

	});

});

//Convenience function for generating html
function generateReceptHTML(recept) {
	return 	'<tr><td scope ="row">' + recept.receptId + '</td>' +
	'<td><input type="text" id = "'+recept.receptId +"_Name"+'" class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function generateReceptKompHTML(receptKomp) {
	return 	'<tr><td scope ="row">' + receptKomp.receptId + '</td>' +
	'<td scope = "row">'+receptKomp.raavareId + '</td>' +
	'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_netto"+'" class="form-control-plaintext" value="' + receptKomp.nomNetto + '"></td></td>' +
	'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_tolerance"+'" class="form-control-plaintext" value="' + receptKomp.tolerance + '"></td></td>' +
	'<td><button type="button" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'" class="btn btn-primary updateKomp"><i class="fas fa-save" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function clearReceptTable(){
	$("#receptTable tbody").empty();
};

function clearReceptKompTable(){
	$("#receptKompTable tbody").empty();
};