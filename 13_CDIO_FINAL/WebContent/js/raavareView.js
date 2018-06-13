var id,id2,value;
$(document).ready(function() {
	showLoader();
	loadRaavare();
	loadMenu();
	clickAddHandler();
	clickDeleteHandler();
	clickDismissDeleteHandler()
	addEnterHandler();
	clickUpdateHandler();
	clickShowMoreHandler();
	clickAddKompHandler();
	clickDeleteKompHandler();
	clickUpdateKompHandler();
});

function clickUpdateKompHandler(){ //Tilfoejer funktion til at bekraefte opdateringen af et raavare batch, via en modal.
	$(".btn-primaryUpdateKomp").click(function(){
		put('rest/raavare/batch/',
			JSON.stringify({
				rbId : id.split("_")[1],
				raavareId : id.split("_")[0],
				maengde :  $(("#"+id.split("_")[0]+"_"+id.split("_")[1]+"_mængde"))["0"].value
			}),
			function(data){
				$('#updateKompModal').modal('hide');
				$.notify("Råvare batchen blev opdateret", "success");
				loadRaavareBatch();
			},
			function(data){
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavareBatch();
			});
	});
}

function clickDeleteKompHandler(){ //Tilfoejer funktion til at bekraefte delete af et raavare batch, via en modal.
	$(".btn-primaryDeleteKomp").click(function(){
		Delete('rest/raavare/batch/'+id2.split("_")[1],
		function(){
			$('#deleteKompModal').modal('hide');
			$.notify("Råvare batchen blev slettet", "success");
			loadRaavareBatch();
		},
		function(){
			$('#deleteKompModal').modal('hide');
			$.notify(data.responseText, "error");
			loadRaavareBatch();
		});
	});
}

function loadRaavareBatch(){
	get('rest/raavare/batch/'+id.split("_")[0],
	function(data){
		clearRaavareBatchTable();
		appendToSubTable(data);
		generateClickForSubTable();
	},
	function(data){
		$.notify(data.responseText, "error");
	});
}

function clickAddKompHandler(){ //Funktion til at tilfoeje den indtastede information når et raavare batch tilfoejes.
	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputRaavareIDKomp")["0"].value = id;
	});
	$(".btn-primaryAddKomp").click(function(){
		post('rest/raavare/batch',
			JSON.stringify({
				rbId : $("#inputRBIDKomp")["0"].value,
				raavareId : $("#inputRaavareIDKomp")["0"].value,
				maengde : $("#inputMaengdeKomp")["0"].value
			}),
			function(data){
				$('#addKompModal').modal('hide');
				$.notify("Raavare batchen blev operettet", "success");
				loadRaavareBatch();
			},
			function(data){
				$('#addKompModal').modal('hide');
				$.notify("Fejl ved oprettelse af Raavare batchen", "error");
				loadRaavareBatch();
		});
	});
}

function clickShowMoreHandler(){
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadRaavareBatch();
	});
}

function addEnterHandler(){ //Tilfoejer funktionalitet til at aabne update modal for den raavare der er markeret, naar 'Enter' trykkes.
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
}

function clickUpdateHandler(){	//Funktion til at haandtere opdatering af en raavare.
	$(".btn-primaryUpdate").click(function(){
		put('rest/raavare',
			JSON.stringify({
				raavareId : id.split("_")[0],
				raavareNavn : $("#"+id.split("_")[0]+"_navn")["0"].value,
				leverandoer : $("#"+id.split("_")[0]+"_leverandør")["0"].value
			}),
			function(data){
				$('#updateModal').modal('hide');
				$.notify("Raavaren blev opdateret", "success");
				loadRaavare();
			},
			function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavare();
		});
	});
}

function clickDismissDeleteHandler(){ //Haandterer hvis man ikke vil slette alligevel.
	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
}

function clickDeleteHandler(){ //Tilfoejer funktion til at slette en raavare naar slet bekraeftes.
	$(".btn-primaryDelete").click(function(){
		Delete('rest/raavare/'+id,
		function(data){
			$('#deleteModal').modal('hide');
			$.notify("Raavaren blev slettet", "success");
			loadRaavare();
		},
		function(data){
			$('#deleteModal').modal('hide');
			$.notify(data.responseText, "error");
			loadRaavare();
		});
	});
}

function clickAddHandler(){ //Tilfoejer funktion til at tilfoeje en raavare, naar tilfoej knappen trykkes.
	$(".btn-primaryAdd").click(function(){
		post('rest/raavare',
		JSON.stringify({
			raavareId : $("#inputRaavareID")["0"].value,
			raavareNavn : $("#inputRaavareNavn")["0"].value,
			leverandoer : $("#inputLeverandør")["0"].value
		}),
		function(data){
			$('#addModal').modal('hide');
			$.notify("Raavaren blev operettet", "success");
			loadRaavare();
		},
		function(data){
			$('#addModal').modal('hide');
			$.notify("Fejl ved oprettelse af raavaren", "error");
			loadRaavare();
		})
		
	});
}

function generateClickForSubTable() { //Funktion til at vise modal for opdatering eller slet, baseret på hvilken knap trykkes.
	$(".sletKomp").click(function(e){
		id2 = e.target.id;
		$('#deleteKompModal').modal('show');
		e.preventDefault();
	});
    $(".updateKomp").click(function(e){
        id = e.target.id;
        $('#updateKompModal').modal('show');
    });
};
function generateClickForTable() { //Funktion til at vise den tilsvareende modal for den knap der trykkes paa.
	$(".slet").click(function(e){
		id = e.target.id;
		$('#deleteModal').modal('show');
		e.preventDefault();
	});
	$(".vis").click(function(e){
		id = e.target.id;
		$('#showMoreModal').modal('show');
		e.preventDefault();

	});
	$(".update").click(function(e){
		id = e.target.id;
		$('#updateModal').modal('show');
	});
}

function appendToSubTable(data){
	$.each(data,function(i,element){
		$('#raavareBatchTable').append(generateRaavareBatchKompHTML(data[i]));

	});
}

function appendToTable(data){
	$.each(data,function(i,element){
		$('#raavareAdminTable').children().append(generateRaavareHTML(data[i]));
	});
}

function loadRaavare(){
	get('rest/raavare',
	function(data){
		clearRaavareTable();
		appendToTable(data);
		generateClickForTable();
		hideLoader();	
	},
	function(data){
		$.notify(data.responseText, "error");
	});
}

function showLoader(){
	$("#raavareAdminTable").hide();
	$(".loader").show();
}

function hideLoader(){
	$(".loader").hide();
	$("#raavareAdminTable").show();
}

function generateRaavareHTML(raavare) {
	return 	'<tr><th scope ="row">' + raavare.raavareId + '</th>' +
	'<td><input type="text" id = "'+raavare.raavareId +"_navn"+'" class="form-control-plaintext" value="' + raavare.raavareNavn + '"></td></td>' +
	'<td><input type="text" id = "'+raavare.raavareId +"_leverandør"+'" class="form-control-plaintext" value="' + raavare.leverandoer + '"></td></td>' +
	'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
    '<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
	'</td></tr>';
}

function generateRaavareBatchKompHTML(raavareBatch) {
	return 	'<tr><th scope ="row">' + raavareBatch.rbId + '</th>' +
	'<th scope = "row">'+raavareBatch.raavareId + '</th>' +
	'<td><input type="text" id =  "'+raavareBatch.raavareId+"_"+raavareBatch.rbId+"_mængde"+'" class="form-control-plaintext" value="' + raavareBatch.maengde + '"></td></td>' +
    '<td><button type="button" id =  "'+raavareBatch.raavareId+"_"+raavareBatch.rbId+'" class="btn btn-primary updateKomp"><i class="fas fa-save" id = "'+raavareBatch.raavareId+"_"+raavareBatch.rbId+'"></i></button>'+'</td>' +
	'<td><button type="button" id =  "'+raavareBatch.raavareId+"_"+raavareBatch.rbId+'" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id =  "'+raavareBatch.raavareId+"_"+raavareBatch.rbId+'"></i></button>'+'</td>' +
	'</td></tr>';
}

function clearRaavareTable(){
	$("#raavareAdminTable tbody").empty();
}

function clearRaavareBatchTable(){
	$("#raavareBatchTable tbody").empty();
}