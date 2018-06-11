$("#produktAdminTable").hide();
$(document).ready(function () {
	var id, id2;
	var value;
	var statuss;
	const enterkey = 13;
	$(".loader").show();
	function loadProdukt() {
		clearProduktTable();
		loadStatus();
	}
	loadProdukt();

	function loadStatus() {
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
                            e.preventDefault();
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
	}

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
				loadProdukt();
			},
			error: function (data) {
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af produkt batchet", "error");
				loadProdukt();
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
				loadProdukt();
			},
			error: function (data) {
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProdukt();
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
				loadProdukt();
			},
			error: function (data) {
				$('#deleteModal').modal('hide');
				$.notify(data.responseText, "error");
				loadProdukt();
			}
		});

	});

	$(".btn-secondaryDelete").click(function () {
		$('#deleteModal').modal('hide');
	});

	//Produktbatch komp stuff starts here
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

	$('#showMoreModal').on('shown.bs.modal', function () {
		loadProduktBatchKomps();
	});


	$(".btn-primaryAddKomp").click(function () {
		$.ajax({ //Indleder et asynkront ajax kald
			url: 'rest/produktbatch/komponent', //specificerer endpointet
			data: JSON.stringify({
				pbId: $("#inputPBID")["0"].value,
				rbId: $("#inputRBID")["0"].value,
				tara: $("#inputTara")["0"].value,
				netto: $("#inputNetto")["0"].value,
				oprId: $("#inputOpr")["0"].value
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

	//Convenience function for generating html
	function generateProduktHTML(produkt) {
		var status = new Array();
		status.push(produkt.status);

		statuss.forEach(function(st) {
			if (st !== status[0]) {
				status.push(st);
			}
		});

		return '<tr><td scope ="row">' + produkt.pbId + '</td>' +
		'<td><select name="' + produkt.pbId + '_status" id="' + produkt.pbId + '_status"><option value="' + status[0] + '">' + status[0] + '</option><option value="' + status[1] + '">' + status[1] + '</option><option value="' + status[2] + '">' + status[2] + '</option></select></td></td>' +
		'<td><input type="text" id = "' + produkt.pbId + "_recept" + '" class="form-control-plaintext" value="' + produkt.receptId + '"></td></td>' +
		'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary vis"><i class="fas fa-folder-open" id = "'+produkt.pbId+'"></i></button>' + '</td>' +
		'<td><button type="button" id = "'+produkt.pbId+'" class="btn btn-primary update"><i class="fas fa-save" id = "'+produkt.pbId+'"></i></button>'+'</td>' +
		'<td><button type="button" id = "' + produkt.pbId + '" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "' + produkt.pbId + '"></i></button>' + '</td>' +
		'</td></tr>';
	}


	function generateProduktBatchKompHTML(produktKomp) {
		return '<tr><td scope ="row">' + produktKomp.pbId + '</td>' +
		'<td scope = "row">' + produktKomp.rbId + '</td>' +
		'<td><input type="text" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + "_tara" + '" class="form-control-plaintext" value="' + produktKomp.tara + '"></td></td>' +
		'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_netto" + '" class="form-control-plaintext" value="' + produktKomp.netto + '"></td></td>' +
		'<td><input type="text" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + "_operatoer" + '" class="form-control-plaintext" value="' + produktKomp.oprId + '"></td></td>' +
		'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary updateKomp"><i class="fas fa-save" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>'+'</td>' +
		'<td><button type="button" id =  "' + produktKomp.pbId + "_" + produktKomp.rbId + '" class="btn btn-primary sletKomp"><i class="far fa-trash-alt" id = "' + produktKomp.pbId + "_" + produktKomp.rbId + '"></i></button>' + '</td>' +
		'</td></tr>';
	}

	function clearProduktTable() {
		$("#produktTable>tbody").empty();
	}

	function clearProduktBatchKompTable() {
		$("#produktBatchKompTable>tbody").empty();
	}


	$(document).keypress(function (e) {
		if (e.which === enterkey) {
			id = e.target.id;
			value = e.target.value;
			var res = id.split("_");
			if (res.length === 2) {
				$('#updateModal').modal('show');
			} else {
				$('#updateKompModal').modal('show');
			}
		}

	});

});