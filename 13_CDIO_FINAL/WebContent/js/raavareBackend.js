$(document).ready(function() {
	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare', //specificerer endpointet
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
				loadRaavare();
			},
			error : function(data){
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af raavaren", "error");
				loadRaavare();
			}
		});
	});


	$(".btn-primaryUpdate").click(function(){
		var actualId = id.split("_");	
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare', //specificerer endpointet
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
				loadRaavare();
			},
			error : function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavare();
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
				loadRaavare();
			},
			error : function(data){
				$('#deleteModal').modal('hide');
				$.notify(data.responseText, "error");
				loadRaavare();
			}
		});

	});

	//raavarebatchstuff starts here
	$(".btn-primaryAddKomp").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/raavare/batch', //specificerer endpointet
			data : JSON.stringify({
				rbId : $("#inputRBIDKomp")["0"].value,
				raavareId : $("#inputRaavareIDKomp")["0"].value,
				maengde : $("#inputMaengdeKomp")["0"].value
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
			url : 'rest/raavare/batch', //specificerer endpointet
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
});


function loadRaavare(){
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/raavare', //specificerer endpointet
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
};

function loadRaavareBatch(){
	var res = id.split("_");
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/raavare/batch/'+res[0], //specificerer endpointet
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

};