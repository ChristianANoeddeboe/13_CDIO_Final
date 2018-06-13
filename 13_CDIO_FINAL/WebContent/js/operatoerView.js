$("#operatoerAdminTable").hide();
$(document).ready(function() {
	var id, value, rolle, statuss;
	const enterkey = 13;
	$(".loader").show();
	
	loadOperators();

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

	$(document).keypress(function(e) {
		if(e.which === enterkey) {
			id = e.target.id;
			$('#updateModal').modal('show');
		}
	});



});

function generateOperatoerHTML(operatoer) {
	var status = new Array();
	status.push(operatoer.aktiv);

	if(statuss[0] === status[0]){
		status[1] = statuss[1];
	}else{
		status[1] = statuss[0];
	}

	return 	'<tr><td scope ="row">' + operatoer.oprId + '</td>' +
	'<td><input type="text" id = "'+operatoer.oprId+"_fornavn"+'" class="form-control-plaintext" value="' + operatoer.fornavn + '"></td></td>' +
	'<td><input type="text" id = "'+operatoer.oprId+"_efternavn"+'" class="form-control-plaintext" value="' + operatoer.efternavn + '"></td></td>' +
	'<td scope = "row"><span id = "'+operatoer.oprId+"_cpr"+'">'+operatoer.cpr+'</span></td></td>' +
	'<td><select class="" name="' + operatoer.oprId + '_aktiv" id="' + operatoer.oprId + '_status"><option value="' + status[0] + '">' + status[0]  + '</option><option value="' + status[1] + '">' + status[1] + '</option>></select></td></td>' +
	'<td><button type="button" id = "'+operatoer.oprId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+operatoer.oprId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function clearOperatoerTable(){
	$("#operatoerAdminTable tbody").empty();
};