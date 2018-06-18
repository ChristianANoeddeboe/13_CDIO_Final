var id, statuss, temp;
$(document).ready(function() {
	showLoader();
	loadOperators();
	loadMenu();
	clickAddHandler();
	clickUpdateHandler();
	addEnterHandler();
	enableTooltips();
});

function addEnterHandler(){
	$(document).keypress(function(e) { //show modal on enter.
		if(e.which === enterkey) {
			id = e.target.id;
			$('#updateModal').modal('show');
		}
	});
}

function clickUpdateHandler(){ // Tilfoejer funktion til at aabne confirmation modal for update og paabegynder genindlaesningen af data for operatoer.
	$(".btn-primaryUpdate").click(function(){
		put('rest/bruger',
			JSON.stringify({
			oprId : id.split("_")[0],
			fornavn : $(("#"+id.split("_")[0]+"_fornavn"))["0"].value,
			efternavn : $(("#"+id.split("_")[0]+"_efternavn"))["0"].value,
			cpr : $(("#"+id.split("_")[0]+"_cpr"))["0"].textContent,
			roles : $(("#"+id.split("_")[0]+"_roles"))["0"].value,
			aktiv : $(("#"+id.split("_")[0]+"_aktiv"))["0"].value
		}),
		function(data){
			$('#updateModal').modal('hide');
			$.notify("Operatoeren blev opdateret", "success");
			loadOperators();
		},
		function(data){
			$('#updateModal').modal('hide');
			$.notify(data.responseText, "error");
			loadOperators();
		})
	});
}

function clickAddHandler(){
	$(".btn-primaryAdd").click(function(){ // Tilfoejer funktion til at aabne add modal når add knap trykkes.
		post('rest/bruger',
			JSON.stringify({
			oprId : $("#inputID")["0"].value,
			fornavn : $("#inputFornavn")["0"].value,
			efternavn : $("#inputEfternavn")["0"].value,
			cpr : $("#inputCPR")["0"].value,
			roles : $("input:checked").val()
		}),
		function(){
			$('#addModal').modal('hide');
			$.notify("Operatoeren blev operettet", "success");
			loadOperators();
		}, 
		function(){
			$('#addModal').modal('hide');
			$.notify("Fejl ved oprettelse af operatoeren", "error");
			loadOperators();
		});
	});
}

// Indlaeser data for operatoererne og genindlaeser tabellen.
function loadOperators(){
	getEnum('rest/enum/status_operatoer',function(){
		temp = statuss;
		getEnum('rest/enum/roller', function(){
			get('rest/bruger',function(data){
				clearOperatoerTable();
				appendToTable(data);
				generateClickForTable();
				hideLoader();
			}, function(data){
				$.notify(data.responseText, "error");
			});
		}, function(data){
			$.notify(data.responseText, "error");			
		});
	}, function(data){
		$.notify(data.responseText, "error");
	});
}

function hideLoader(){
	$(".loader").hide();
	$("#operatoerAdminTable").show();
}

function showLoader(){
	$("#operatoerAdminTable").hide();
	$(".loader").show();
}

function appendToTable(data){
	$.each(data,function(i,element){
		$('#operatoerAdminTable').children().append(generateOperatoerHTML(data[i]));
	});
}

function generateClickForTable(){ // Tilfoejer funktion til at aabne update modal når update knap trykkes.
	$(".update").click(function(e){
		id = e.target.id;
		$('#updateModal').modal('show');
	});	
}

function enableTooltips() {
	$('body').tooltip({
	    selector: '[tooltip-toggle="tooltip"]'
	});
}

function generateOperatoerHTML(operatoer) { //Tilfoejer indholder i tabellen.
	var status = new Array();
	status.push(operatoer.aktiv);

	if(temp[0] === status[0]){
		status[1] = temp[1];
	}else{
		status[1] = temp[0];
	}
	
	var roller = new Array();
	roller.push(operatoer.roles);
	statuss.forEach(function(st) {
		if (st !== roller[0]) {
			roller.push(st);
		}
	});

	return 	'<tr><th scope ="row">' + operatoer.oprId + '</th>' +
	'<td><input type="text" id = "'+operatoer.oprId+"_fornavn"+'" class="form-control-plaintext" value="' + operatoer.fornavn + '"></td></td>' +
	'<td><input type="text" id = "'+operatoer.oprId+"_efternavn"+'" class="form-control-plaintext" value="' + operatoer.efternavn + '"></td></td>' +
	'<th scope = "row"><span id = "'+operatoer.oprId+"_cpr"+'">'+operatoer.cpr+'</span></th></td>' +
	'<td><select class="" name="' + operatoer.oprId + '_roles" id="' + operatoer.oprId + '_roles"><option value="' + roller[0] + '">' + roller[0]  + '</option><option value="' + roller[1] + '">' + roller[1] +'</option><option value="' + roller[2] + '">' + roller[2]  + '</option><option value="' + roller[3] + '">' + roller[3]  + '</option>></select></td></td>' +
	'<td><select class="" name="' + operatoer.oprId + '_aktiv" id="' + operatoer.oprId + '_aktiv"><option value="' + status[0] + '">' + status[0]  + '</option><option value="' + status[1] + '">' + status[1] + '</option>></select></td></td>' +
	'<td><button type="button" id = "'+operatoer.oprId+'" class="btn btn-primary update" tooltip-toggle="tooltip" data-placement="top"  title="Gem"><i class="fas fa-save" id = "'+operatoer.oprId+'"></i></button>'+'</td>' +
	'</td></tr>';
}

function clearOperatoerTable(){
	$("#operatoerAdminTable tbody").empty();
}