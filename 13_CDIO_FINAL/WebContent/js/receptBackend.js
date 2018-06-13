
function loadRecepts(){
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/recept', //specificerer endpointet
		type : 'GET', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			clearReceptTable();
			$.each(data,function(i,element){
				$('#receptAdminTable').children().append(generateReceptHTML(data[i]));

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
			$("#receptAdminTable").show();
		},
		error : function(data){
			$.notify(data.responseText, "error");
		}
	});
};


function loadReceptKomps(){
	var res = id.split("_");
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/recept/komponent/'+res[0], //specificerer endpointet
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

$(document).ready(function() {




	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept', //specificerer endpointet
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
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept', //specificerer endpointet
			data : JSON.stringify({
				receptId : res[0],
				receptNavn : $("#"+id[0]+"_Name")["0"].value
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
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/'+id[0], //specificerer endpointet
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


	//Recept komp stuff starts here

	$(".btn-primaryAddKomp").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent', //specificerer endpointet
			data : JSON.stringify({
				receptId : $("#inputReceptIDKomp")["0"].value,
				raavareId : $("#inputRaavareIDKomp")["0"].value,
				nomNetto : $("#inputNomNettoKomp")["0"].value,
				tolerance: $("#inputToleranceKomp")["0"].value
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
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/komponent', //specificerer endpointet
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

	});
});
