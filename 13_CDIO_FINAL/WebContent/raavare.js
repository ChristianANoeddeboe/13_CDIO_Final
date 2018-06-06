$(document).ready(function() {
	var id,id2;
	var value;
	const enterkey = 13;
	function loadProdukt(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/all', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearRaavareTable();
				$.each(data,function(i,element){
					$('#raavareAdmin').children().append(generateRaavareHTML(data[i]));

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
	loadProdukt();

	

	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/create', //specificerer endpointet
			data : JSON.stringify({
				raavareId : $("#inputRåvareID")["0"].value,
				raavareNavn : $("#inputRåvareNavn")["0"].value,
				leverandoer : $("#inputLeverandør")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addModal').modal('hide');
				$.notify("Råvaren blev operettet", "success");
				loadProdukt();
			},
			error : function(data){
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af råvaren", "error");
				loadProdukt();
			}
		});
	});


	$(".btn-primaryUpdate").click(function(){
		var actualId = id.split("_");
		
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/update', //specificerer endpointet
			data : JSON.stringify({
				raavareId : actualId[0],
				raavareNavn : $("#"+actualId[0]+"_navn")["0"].value,
				leverandoer : $("#"+actualId[0]+"_leverandør")["0"].value
			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateModal').modal('hide');
				$.notify("Råvaren blev opdateret", "success");
				loadProdukt();
			},
			error : function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProdukt();
			}
		});

	})
	
	$(".btn-primaryDelete").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/'+id, //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#deleteModal').modal('hide');
				$.notify("Råvaren blev slettet", "success");
				loadProdukt();
			},
			error : function(data){
				$('#deleteModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProdukt();
			}
		});

	});

	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
	
	//Produktbatch komp stuff starts here
	function loadRåvareBatch(){
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/list/'+res[0], //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearRåvareBatchTable();
				$.each(data,function(i,element){
					$('#råvareBatchTable').append(generateRåvareBatchKompHTML(data[i]));

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
		loadRåvareBatch();
	});
	


	$(".btn-primaryAddKomp").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/create', //specificerer endpointet
			data : JSON.stringify({
				rbId : $("#inputRBID")["0"].value,
				raavareId : $("#inputRaavareID")["0"].value,
				maengde : $("#inputMaengde")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addKompModal').modal('hide');
				$.notify("Råvare batchen blev operettet", "success");
				loadRåvareBatch();
			},
			error : function(data){
				$('#addKompModal').modal('hide');
				$.notify("Fejl ved oprettelse af Råvare batchen", "error");
				loadRåvareBatch();
			}
		});
	});
	
	$(".btn-primaryDeleteKomp").click(function(){
		var res = id2.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/'+res[0], //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#deleteKompModal').modal('hide');
				$.notify("Produktbatch komponenten blev slettet", "success");
				loadRåvareBatch();
			},
			error : function(data){
				$('#deleteKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRåvareBatch();
			}
		});

	});
	
	$(".btn-primaryUpdateKomp").click(function(){
		var res = id.split("_")
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/update', //specificerer endpointet
			data : JSON.stringify({
				rbId : res[0],
				raavareId : res[1],
				maengde :  $(("#"+res[0]+"_"+res[1]+"_mængde"))["0"].value
			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateKompModal').modal('hide');
				$.notify("Produktbatch komponenten blev opdateret", "success");
				loadRåvareBatch();
			},
			error : function(data){
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRåvareBatch();
			}
		});

	})
	
	//Convenience function for generating html
	function generateRaavareHTML(raavare) {
		return 	'<tr><th scope ="row">' + raavare.raavareId + '</th>' +
		'<td><input type="text" id = "'+raavare.raavareId +"_navn"+'"class="form-control-plaintext" value="' + raavare.raavareNavn + '"></td></td>' +
		'<td><input type="text" id = "'+raavare.raavareId +"_leverandør"+'"class="form-control-plaintext" value="' + raavare.leverandoer + '"></td></td>' +
		'<td><button type="button" id = "'+raavare.raavareId+'"class="btn btn-primary vis">▼</button>'+'</td>' +
		'<td><button type="button" id = "'+raavare.raavareId+'"class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function generateRåvareBatchKompHTML(råvareBatch) {
		return 	'<tr><th scope ="row">' + råvareBatch.rbId + '</th>' +
		'<th scope = "row">'+råvareBatch.raavareId + '</th>' +
		'<td><input type="text" id =  "'+råvareBatch.rbId+"_"+råvareBatch.raavareId+"_mængde"+'"class="form-control-plaintext" value="' + råvareBatch.maengde + '"></td></td>' +
		'<td><button type="button" id =  "'+råvareBatch.rbId+"_mængde"+'" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id ="'+råvareBatch.rbId+"_mængde"+'"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function clearRaavareTable(){
		$("#raavareTable tbody").empty();
	};

	function clearRåvareBatchTable(){
		$("#råvareBatchTable tbody").empty();
	};


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

});
