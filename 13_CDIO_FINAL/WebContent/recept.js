/**
 * 
 */

$(document).ready(function() {
	var id;
	var value;
	const enterkey = 13;
	function loadRecepts(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/all', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearReceptTable();
				$.each(data,function(i,element){
					$('#receptAdmin').children().append(generateReceptHTML(data[i]));

				});
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
			},
			error : function(data){
				$.notify(data.responseText, "error");
			}
		});
	};

	loadRecepts();



	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/create', //specificerer endpointet
			data : JSON.stringify({
				receptId : $("#inputID")["0"].value,
				receptNavn : $("#inputName")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addModal').modal('hide');
				$.notify("Recepten blev operettet", "success");
				loadRecepts();
			},
			error : function(data){
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af recepten", "error");
				loadRecepts();
			}
		});
	});


	$(".btn-primaryUpdate").click(function(){		
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/update', //specificerer endpointet
			data : JSON.stringify({
				receptId : id,
				receptNavn : value
			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateModal').modal('hide');
				$.notify("Recepten blev opdateret", "success");
				loadRecepts();
			},
			error : function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRecepts();
			}
		});

	})


	$(".btn-primaryDelete").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/'+id, //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#deleteModal').modal('hide');
				$.notify("Recepten blev slettet", "success");
				loadRecepts();
			},
			error : function(data){
				$('#deleteModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRecepts();
			}
		});

	});

	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
	//Recept komp stuff starts here
	function loadReceptKomps(){
		
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent/list/'+id, //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearReceptKompTable();
				$.each(data,function(i,element){
					$('#receptKompTable').append(generateReceptKompHTML(data[i]));

				});
			},
			error : function(data){
				$.notify(data.responseText, "error");
			}
		});
	
	};
	
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadReceptKomps();
	});
	


	$(".btn-primaryAddKomp").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent/create', //specificerer endpointet
			data : JSON.stringify({
				receptId : $("#inputReceptID")["0"].value,
				raavareId : $("#inputRaavareID")["0"].value,
				nomNetto : $("#inputNomNetto")["0"].value,
				tolerance: $("#inputTolerance")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addKompModal').modal('hide');
				$.notify("Receptenkomponenten blev operettet", "success");
				loadReceptKomps();
			},
			error : function(data){
				$('#addKompModal').modal('hide');
				$.notify("Fejl ved oprettelse af receptkomponenten", "error");
				loadReceptKomps();
			}
		});
	});
	
	
	//Convenience function for generating html
	function generateReceptHTML(recept) {
		return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
		'<td><input type="text" id = "'+recept.receptId +'"class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary vis">▼</button>'+'</td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary slet"><i class="far fa-trash-alt"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function generateReceptKompHTML(receptKomp) {
		return 	'<tr><th scope ="row">' + receptKomp.receptId + '</th>' +
		'<td>'+receptKomp.raavareId + '</td>' +		
		'<td><input type="text" id = "'+receptKomp.receptId+receptKomp.raavareId+'"class="form-control-plaintext" value="' + receptKomp.nomNetto + '"></td></td>' +
		'<td><input type="text" id = "'+receptKomp.receptId+receptKomp.raavareId+'"class="form-control-plaintext" value="' + receptKomp.tolerance + '"></td></td>' +
		'<td><button type="button" id = "'+receptKomp.receptId+'"class="btn btn-primary slet"><i class="far fa-trash-alt"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function clearReceptTable(){
		$("#receptTable>tbody").empty();
	};

	function clearReceptKompTable(){
		$("#receptKompTable>tbody").empty();
	};


	$(document).keypress(function(e) {
		if(e.which == enterkey) {
			id = e.target.id;
			value = e.target.value;
			$('#updateModal').modal('show');
		}

	});

});
