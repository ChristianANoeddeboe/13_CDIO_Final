$(document).ready(function() {
	var id,id2;
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
	
	


	$(".btn-primaryDelete").click(function(e){
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
		e.preventDefault();
	});
	
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
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent/list/'+res[0], //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearReceptKompTable();
				$.each(data,function(i,element){
					$('#receptKompTable').append(generateReceptKompHTML(data[i]));

				});
				$(".sletKomp").click(function(e){
					id2 = e.target.id;
					$('#deleteKompModal').modal('show');
					e.preventDefault();
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
	
	$(".btn-primaryDeleteKomp").click(function(){
		var res = id2.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent/'+res[0]+'/'+res[1], //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#deleteKompModal').modal('hide');
				$.notify("Recept komponenten blev slettet", "success");
				loadReceptKomps();
			},
			error : function(data){
				$('#deleteKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadReceptKomps();
			}
		});

	});
	
	$(".btn-primaryUpdateKomp").click(function(){
		var res = id.split("_")
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent/update', //specificerer endpointet
			data : JSON.stringify({
				receptId : res[0],
				raavareId : res[1],
				nomNetto : $(("#"+res[0]+"_"+res[1]+"_netto"))["0"].value,
				tolerance : $(("#"+res[0]+"_"+res[1]+"_tolerance"))["0"].value
			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateKompModal').modal('hide');
				$.notify("Recept komponenten blev opdateret", "success");
				loadReceptKomps();
			},
			error : function(data){
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadReceptKomps();
			}
		});

	})
	
	//Convenience function for generating html
	function generateReceptHTML(recept) {
		return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
		'<td><input type="text" id = "'+recept.receptId +'"class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary vis">▼</button>'+'</td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+recept.receptId+'"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function generateReceptKompHTML(receptKomp) {
		return 	'<tr><th scope ="row">' + receptKomp.receptId + '</th>' +
		'<th scope = "row">'+receptKomp.raavareId + '</th>' +		
		'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_netto"+'"class="form-control-plaintext" value="' + receptKomp.nomNetto + '"></td></td>' +
		'<td><input type="text" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+"_tolerance"+'"class="form-control-plaintext" value="' + receptKomp.tolerance + '"></td></td>' +
		'<td><button type="button" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id = "'+receptKomp.receptId+"_"+receptKomp.raavareId+'"></i></button>'+'</td>' +
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
			var res = id.split("_");
			if(res.length == 1){
				$('#updateModal').modal('show');
			}else{
				$('#updateKompModal').modal('show');
			}
		}

	});

});
