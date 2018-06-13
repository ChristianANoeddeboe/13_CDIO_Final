function loadProducts() {
	$.ajax({ //Indleder et asynkront ajax kald
		url: 'rest/enum/status_produktbatch', //specificerer endpointet
		type: 'GET', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			statuss = data;
			$.ajax({ //Indleder et asynkront ajax kald
				url: 'rest/produktbatch', //specificerer endpointet
				type: 'GET', //Typen af HTTP requestet (GET er default)
				success: function (data) {//Funktion der skal udføres når data er hentet
					clearProduktTable();
					$.each(data, function (i, element) {
						$('#produktAdminTable').children().append(generateProduktHTML(data[i]));
					});
					$(".slet").click(function (e) {
						id = e.target.id;
						$('#deleteModal').modal('show');
						e.preventDefault();
					});
					$(".vis").click(function (e) {
						id = e.target.id;
						$('#showMoreModal').modal('show');
					});
					$(".update").click(function(e){
						id = e.target.id;
						$('#updateModal').modal('show');
					});
					$(".loader").hide();
					$("#produktAdminTable").show();
				},

				error: function (data) {
					$.notify(data.responseText, "error");
				}
			});
		},
		error: function (data) {
			$.notify(data.responseText, "error");
		}
	});
};

function loadProduktBatchKomps() {
	var res = id.split("_");
	$.ajax({ //Indleder et asynkront ajax kald
		url: 'rest/produktbatch/komponent/' + res[0], //specificerer endpointet
		type: 'GET', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			clearProduktBatchKompTable();
			$.each(data, function (i, element) {
				$('#produktBatchKompTable').append(generateProduktBatchKompHTML(data[i]));

			});
			$(".sletKomp").click(function (e) {
				id2 = e.target.id;
				$('#deleteKompModal').modal('show');
				e.preventDefault();
			});
			$(".updateKomp").click(function(e){
				id = e.target.id;
				$('#updateKompModal').modal('show');
			});
		},
		error: function (data) {
			$.notify(data.responseText, "error");
		}
	});
}

$(document).ready(function () {

	$(".btn-primaryAdd").click(function () {
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch', //specificerer endpointet
			data: JSON.stringify({
				pbId: $("#inputID")["0"].value,
				status: $("input:checked").val(),
				receptId: $("#inputReceptId")["0"].value
			}),
			contentType: "application/JSON",
			type: 'POST', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#addModal').modal('hide');
				$.notify("Produkt batchet blev operettet", "success");
				loadProducts();
			},
			error: function (data) {
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af produkt batchet", "error");
				loadProducts();
			}
		});
	});


	$(".btn-primaryUpdate").click(function () {
		var actualId = id.split("_");

		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch', //specificerer endpointet
			data: JSON.stringify({
				pbId: actualId[0],
				status: $("#" + actualId[0] + "_status")["0"].value,
				receptId: $("#" + actualId[0] + "_recept")["0"].value
			}),
			contentType: "application/json",
			type: 'PUT', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#updateModal').modal('hide');
				$.notify("ProduktBatchet blev opdateret", "success");
				loadProducts();
			},
			error: function (data) {
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProducts();
			}
		});
	});

	$(".btn-primaryDelete").click(function () {
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch/' + id, //specificerer endpointet
			contentType: "plain/text",
			type: 'DELETE', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#deleteModal').modal('hide');
				$.notify("Produktbatchet blev slettet", "success");
				loadProducts();
			},
			error: function (data) {
				$('#deleteModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProducts();
			}
		});

	});





	//Produktbatch komp stuff starts here



	$(".btn-primaryAddKomp").click(function () {
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch/komponent', //specificerer endpointet
			data: JSON.stringify({
				pbId: $("#inputPBIDKomp")["0"].value,
				rbId: $("#inputRBIDKomp")["0"].value,
				tara: $("#inputTaraKomp")["0"].value,
				netto: $("#inputNettoKomp")["0"].value,
				oprId: $("#inputOprKomp")["0"].value
			}),
			contentType: "application/JSON",
			type: 'POST', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#addKompModal').modal('hide');
				$.notify("Produktbatch komponenten blev operettet", "success");
				loadProduktBatchKomps();
			},
			error: function (data) {
				$('#addKompModal').modal('hide');
				$.notify("Fejl ved oprettelse af produktbatch komponenten", "error");
				loadProduktBatchKomps();
			}
		});
	});

	$(".btn-primaryDeleteKomp").click(function () {
		var res = id2.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch/komponent/' + res[0] + '/' + res[1], //specificerer endpointet
			contentType: "plain/text",
			type: 'DELETE', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#deleteKompModal').modal('hide');
				$.notify("Produktbatch komponenten blev slettet", "success");
				loadProduktBatchKomps();
			},
			error: function (data) {
				$('#deleteKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProduktBatchKomps();
			}
		});

	});

	$(".btn-primaryUpdateKomp").click(function () {
		var res = id.split("_")
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch/komponent', //specificerer endpointet
			data: JSON.stringify({
				pbId: res[0],
				rbId: res[1],
				tara: $(("#" + res[0] + "_" + res[1] + "_tara"))["0"].value,
				netto: $(("#" + res[0] + "_" + res[1] + "_netto"))["0"].value,
				oprId: $(("#" + res[0] + "_" + res[1] + "_operatoer"))["0"].value
			}),
			contentType: "application/json",
			type: 'PUT', //Typen af HTTP requestet (GET er default)
			success: function (data) {//Funktion der skal udføres når data er hentet
				$('#updateKompModal').modal('hide');
				$.notify("Produktbatch komponenten blev opdateret", "success");
				loadProduktBatchKomps();
			},
			error: function (data) {
				$('#updateKompModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProduktBatchKomps();
			}
		});
	});
});