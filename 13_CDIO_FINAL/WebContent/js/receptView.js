var id,id2,value;

$(document).ready(function() {
	showLoader();
	loadRecepts();
	loadMenu();
	clickAddHandler();
	clickDeleteHandler();
	clickDismissDeleteHandler();
	addEnterHandler();
	clickUpdateHandler();
	clickShowMoreHandler();
	clickAddKompHandler();
	clickDeleteKompHandler();
	clickUpdateKompHandler();
	

});
function clickUpdateKompHandler(){ //Tilfoejer funktion til at bekraefte opdateringen af et recept komponent, via en modal.
	$(".btn-primaryUpdateKomp").click(function(){
		put('rest/recept/komponent',
			JSON.stringify({
				receptId : id.split("_")[0],
				raavareId : id.split("_")[1],
				nomNetto : $(("#"+id.split("_")[0]+"_"+id.split("_")[1]+"_netto"))["0"].value,
				tolerance : $(("#"+id.split("_")[0]+"_"+id.split("_")[1]+"_tolerance"))["0"].value
			}),
			function(data){
				$('#updateKompModal').modal('hide');
				$.notify("Recept komponenten blev opdateret", "success");
				loadReceptKomps();
			},
			function(data){
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadReceptKomps();
			});
	});
}
function clickDeleteKompHandler() { //Tilfoejer funktion til at bekraefte delete af et recept komponent, via en modal.
	$(".btn-primaryDeleteKomp").click(function(){
		Delete('rest/recept/komponent/'+id2.split("_")[0]+"/"+id2.split("_")[1],
				function(data){
					$('#deleteKompModal').modal('hide');
					$.notify("Recept komponenten blev slettet", "success");
					loadReceptKomps();
				},
				function(data){
					$('#deleteKompModal').modal('hide');
					$.notify(data.responseText, "error");
					loadReceptKomps();
				});
	});
}

function clickAddKompHandler() { //Funktion til at tilfoeje den indtastede information når et recept komponent tilfoejes.
	$('#addKompModal').on('shown.bs.modal', function(){
		$("#inputReceptIDKomp")["0"].value = id;
	});
	$(".btn-primaryAddKomp").click(function(){
		post('rest/recept/komponent',
			JSON.stringify({
				receptId : $("#inputReceptIDKomp")["0"].value,
				raavareId : $("#inputRaavareIDKomp")["0"].value,
				nomNetto : $("#inputNomNettoKomp")["0"].value,
				tolerance: $("#inputToleranceKomp")["0"].value
				}),
				function(data){
					$('#addKompModal').modal('hide');
					$.notify("Receptenkomponenten blev operettet", "success");
					loadReceptKomps();
					},
				function(data){
					$('#addKompModal').modal('hide');
					$.notify("Fejl ved oprettelse af receptkomponenten", "error");
					loadReceptKomps();
					})
			});
};
function clickShowMoreHandler(){
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadReceptKomps();
	});
};
function generateClickForSubTable(){ //Funktion til at vise modal for opdatering eller slet, baseret på hvilken knap trykkes.
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
function appendToSubTable(data){
	$.each(data,function(i,element){
		$('#receptKompTable').append(generateReceptKompHTML(data[i]));
	});
};
function loadReceptKomps(){
	get('rest/recept/komponent/'+id.split("_")[0],
	function(data){
		clearReceptKompTable();
		appendToSubTable(data);
		generateClickForSubTable();
	},
	function(data){
		$.notify(data.responseText, "error");
	});
}
function clickUpdateHandler(){ //Funktion til at haandtere opdatering af en recept.
	$(".btn-primaryUpdate").click(function(){	
		put('rest/recept',
			JSON.stringify({
				receptId : id.split("_")[0],
				receptNavn : $("#"+id[0]+"_Name")["0"].value
			}),
			function(data){
				$('#updateModal').modal('hide');
				$.notify("Recepten blev opdateret", "success");
				loadRecepts();
			},
			function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRecepts();
			});
	})
};
function addEnterHandler(){ //Tilfoejer funktionalitet til at aabne update modal for den recept der er markeret, naar 'Enter' trykkes.
	$(document).keypress(function(e) {
		if(e.which == enterkey) {
			id = e.target.id;
			value = e.target.value;
			var res = id.split("_");
			if(res.length == 2){
				$('#updateModal').modal('show');
			}else{
				$('#updateKompModal').modal('show');
			}
		}

	});
}
function clickDismissDeleteHandler(){ //Haandterer hvis man ikke vil slette alligevel.
	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
};
function clickDeleteHandler(){ //Tilfoejer funktion til at slette en recept naar slet bekraeftes.
	$(".btn-primaryDelete").click(function(){
		Delete('rest/recept/'+id.split("_")[0],
		function(data){
			$('#deleteModal').modal('hide');
			$.notify("Recepten blev slettet", "success");
			loadRecepts();
		},
		function(data){
			$('#deleteModal').modal('hide');
			$.notify(data.responseText, "error");
			loadRecepts();
		});
	});
};
function clickAddHandler(){ //Tilfoejer funktion til at tilfoeje en recept, naar tilfoej knappen trykkes.
	$(".btn-primaryAdd").click(function(){
		post('rest/recept',
		JSON.stringify({
			receptId : $("#inputID")["0"].value,
			receptNavn : $("#inputName")["0"].value
		}),
		function(){
			$('#addModal').modal('hide');
			$.notify("Recepten blev operettet", "success");
			loadRecepts();
		},
		function(){
			$('#addModal').modal('hide');
			$.notify("Fejl ved oprettelse af recepten", "error");
			loadRecepts();
		})
	});
};
function appendToTable(data){
	$.each(data,function(i,element){
		$('#receptAdminTable').children().append(generateReceptHTML(data[i]));

	});
};
function generateClickForTable(){ //Funktion til at vise den tilsvareende modal for den knap der trykkes paa.
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
};
function loadRecepts(){
	get('rest/recept',
	function(data){
		clearReceptTable();
		appendToTable(data);
		generateClickForTable();
		hideLoader();
	},
	function(data){
		$.notify(data.responseText, "error");
	});
	
}
function hideLoader(){
	$(".loader").hide();
	$("#receptAdminTable").show();
}
function showLoader(){
	$("#receptAdminTable").hide();
	$(".loader").show();
};
function generateReceptHTML(recept) {
	return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
	'<td><input type="text" id = "'+recept.receptId +"_Name"+'" class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+recept.receptId+'" class="btn btn-primary slet"><i class="fas fa-trash-alt" id = "'+recept.receptId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function generateReceptKompHTML(receptKomp) {
	return 	'<tr><th scope ="row">' + receptKomp.receptId + '</th>' +
	'<td scope = "row">'+receptKomp.raavareId + '</td>' +
	'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_netto"+'" class="form-control-plaintext" value="' + receptKomp.nomNetto + '"></td></td>' +
	'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_tolerance"+'" class="form-control-plaintext" value="' + receptKomp.tolerance + '"></td></td>' +
	'<td><button type="button" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'" class="btn btn-primary updateKomp"><i class="fas fa-save" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"></i></button>'+'</td>' +
	'<td><button type="button" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'" class="btn btn-primary sletKomp"><i class="fas fa-trash-alt" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"></i></button>'+'</td>' +
	'</td></tr>';
};

function clearReceptTable(){
	$("#receptTable tbody").empty();
};

function clearReceptKompTable(){
	$("#receptKompTable tbody").empty();
};