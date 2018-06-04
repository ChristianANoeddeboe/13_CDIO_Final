/**
 * 
 */

$(document).ready(function() {
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/recept/display', //specificerer endpointet
		type : 'GET', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			clearTable();
			$.each(data,function(i,element){
				$('#receptAdmin').children().append(generateOperatoerHTML(data[i]));
			})
		},
		error : function(data){
			alert("error");
		}
	});

	//Convenience function for generating html
	function generateOperatoerHTML(recept) {
		return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
		'<td><input type="text" class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
		'<td><button type="button" class="btn btn-primary slet" data-toggle="modal" data-target="#exampleModalCenter2">Slet</button>'+
		'</td></tr>';
	}
	
	function clearTable(){
		$("#receptTable>tbody").empty();
		
	};
});
