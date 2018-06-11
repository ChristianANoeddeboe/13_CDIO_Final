 $("#raavareAdminTable").hide();
$(document).ready(function() {
	var id,id2;
	var value;
	const enterkey = 13;
	$(".loader").show();
	function loadProdukt(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/all', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres naar data er hentet
				clearRaavareTable();
				$.each(data,function(i,element){
					$('#raavareAdminTable').children().append(generateRaavareHTML(data[i]));
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
                $(".update").click(function(e){
                    id = e.target.id;
                    $('#updateModal').modal('show');
                });
                $(".loader").hide();
                $("#raavareAdminTable").show();
			},
			error : function(data){
				$.notify(data.responseText, "error");
			}
		});
	}

	loadProdukt();

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
	

	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/create', //specificerer endpointet
			data : JSON.stringify({
				raavareId : $("#inputRaavareID")["0"].value,
				raavareNavn : $("#inputRaavareNavn")["0"].value,
				leverandoer : $("#inputLeverandør")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#addModal').modal('hide');
				$.notify("Raavaren blev operettet", "success");
				loadProdukt();
			},
			error : function(data){
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af raavaren", "error");
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
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#updateModal').modal('hide');
				$.notify("Raavaren blev opdateret", "success");
				loadProdukt();
			},
			error : function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProdukt();
			}
		});
	});
	
	$(".btn-primaryDelete").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/'+id, //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#deleteModal').modal('hide');
				$.notify("Raavaren blev slettet", "success");
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
	function loadRaavareBatch(){
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/list/'+res[0], //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres naar data er hentet
				clearRaavareBatchTable();
				$.each(data,function(i,element){
					$('#raavareBatchTable').append(generateRaavareBatchKompHTML(data[i]));

				});
				$(".sletKomp").click(function(e){
					id2 = e.target.id;
					$('#deleteKompModal').modal('show');
					e.preventDefault();
				});
                $(".updateKomp").click(function(e){
                    id = e.target.id;
                    $('#updateKompModal').modal('show');
                });
			},
			error : function(data){
				$.notify(data.responseText, "error");
			}
		});
	
	}
	
	$('#showMoreModal').on('shown.bs.modal', function () {
		loadRaavareBatch();
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
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#addKompModal').modal('hide');
				$.notify("Raavare batchen blev operettet", "success");
				loadRaavareBatch();
			},
			error : function(data){
				$('#addKompModal').modal('hide');
				$.notify("Fejl ved oprettelse af Raavare batchen", "error");
				loadRaavareBatch();
			}
		});
	});
	
	$(".btn-primaryDeleteKomp").click(function(){
		var res = id2.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch/'+res[0], //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#deleteKompModal').modal('hide');
				$.notify("Råvare batchen blev slettet", "success");
				loadRaavareBatch();
			},
			error : function(data){
				$('#deleteKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavareBatch();
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
			success : function(data) {//Funktion der skal udføres naar data er hentet
				$('#updateKompModal').modal('hide');
				$.notify("Råvare batchen blev opdateret", "success");
				loadRaavareBatch();
			},
			error : function(data){
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavareBatch();
			}
		});
	});
	
	//Convenience function for generating html
	function generateRaavareHTML(raavare) {
		return 	'<tr><td scope ="row">' + raavare.raavareId + '</td>' +
		'<td><input type="text" id = "'+raavare.raavareId +"_navn"+'" class="form-control-plaintext" value="' + raavare.raavareNavn + '"></td></td>' +
		'<td><input type="text" id = "'+raavare.raavareId +"_leverandør"+'" class="form-control-plaintext" value="' + raavare.leverandoer + '"></td></td>' +
		'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary vis"><i class="fas fa-folder-open"></i></button>'+'</td>' +
        '<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
		'<td><button type="button" id = "'+raavare.raavareId+'" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+raavare.raavareId+'"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function generateRaavareBatchKompHTML(raavareBatch) {
		return 	'<tr><td scope ="row">' + raavareBatch.rbId + '</td>' +
		'<td scope = "row">'+raavareBatch.raavareId + '</td>' +
		'<td><input type="text" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+"_mængde"+'"class="form-control-plaintext" value="' + raavareBatch.maengde + '"></td></td>' +
        '<td><button type="button" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"class="btn btn-primary updateKomp"><i class="fas fa-save" id = "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"></i></button>'+'</td>' +
		'<td><button type="button" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id =  "'+raavareBatch.rbId+"_"+raavareBatch.raavareId+'"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function clearRaavareTable(){
		$("#raavareAdminTable tbody").empty();
	}

	function clearRaavareBatchTable(){
		$("#raavareBatchTable tbody").empty();
	}


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
