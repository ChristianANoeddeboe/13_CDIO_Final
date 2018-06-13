function loadOperators() {
	$.ajax({ //Indleder et asynkront ajax kald
		url: 'rest/enum/status_operatoer', //specificerer endpointet
		type: 'GET', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			statuss = data;
			$.ajax({ //Indleder et asynkront ajax kald
				url : 'rest/operatoer', //specificerer endpointet
				type : 'GET', //Typen af HTTP requestet (GET er default)
				success : function(data) {//Funktion der skal udføres når data er hentet
					clearOperatoerTable();
					$.each(data,function(i,element){
						$('#operatoerAdminTable').children().append(generateOperatoerHTML(data[i]));
					});
					$(".update").click(function(e){
						id = e.target.id;
						$('#updateModal').modal('show');
					});
					$(".loader").hide();
					$("#operatoerAdminTable").show();
				},
				error : function(data){
					$.notify(data.responseText, "error");
				}
			});
		},
		error: function (data) {
			$.notify(data.responseText, "error");
		}
	});
};
$(document).ready(function () {	



	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/operatoer', //specificerer endpointet
			data : JSON.stringify({
				oprId : $("#inputID")["0"].value,
				fornavn : $("#inputFornavn")["0"].value,
				efternavn : $("#inputEfternavn")["0"].value,
				cpr : $("#inputCPR")["0"].value,
				roles : "Administrator"
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addModal').modal('hide');
				$.notify("Operatoeren blev operettet", "success");
				loadOperators();
			},
			error : function(data){
				$('#addModal').modal('hide');
				$.notify("Fejl ved oprettelse af operatoeren", "error");
				loadOperators();
			}
		});
	});


	$(".btn-primaryUpdate").click(function(){
		var res = id.split("_");
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/operatoer', //specificerer endpointet
			data : JSON.stringify({
				oprId : res[0],
				fornavn : $(("#"+res[0]+"_fornavn"))["0"].value,
				efternavn : $(("#"+res[0]+"_efternavn"))["0"].value,
				cpr : $(("#"+res[0]+"_cpr"))["0"].textContent,
				roles : "Administrator",
				aktiv : $(("#"+res[0]+"_status"))["0"].value

			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateModal').modal('hide');
				$.notify("Operatoeren blev opdateret", "success");
				loadOperators();
			},
			error : function(data){
				$('#updateModal').modal('hide');
				$.notify(data.responseText, "error");
				loadOperators();
			}
		});
	});
});