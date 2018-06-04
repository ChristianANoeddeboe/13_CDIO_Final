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
		return 	'<tr><td>' + recept.receptId + '</td>' +
		'<td>' + recept.receptNavn + '</td>' +
		'</tr>';
	}
	
	function clearTable(){
		$("#receptTable>tbody").empty();
		
	};
});
