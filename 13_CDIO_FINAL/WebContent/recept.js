/**
 * 
 */

$(document).ready(function() {
	var id;

	function loadUsers(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/display', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				clearTable();
				$.each(data,function(i,element){
					$('#receptAdmin').children().append(generateOperatoerHTML(data[i]));

				})
				$(".slet").click(function(e){
					id = e.target.id;
					e.preventDefault();
				})
			},
			error : function(data){
				alert("error");
			}
		});
	}
	loadUsers();


	$(".btn-secondary").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/'+id, //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				alert("OK");
				loadUsers();
			},
			error : function(data){
				alert("Error happend");
			}
		});

	})

	//Convenience function for generating html
	function generateOperatoerHTML(recept) {
		return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
		'<td><input type="text" id = "'+recept.receptId +'"class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary slet" data-toggle="modal" data-target="#exampleModalCenter2">Slet</button>'+
		'</td></tr>';
	}

	function clearTable(){
		$("#receptTable>tbody").empty();

	};
	
	
	$(document).keypress(function(e) {
	    if(e.which == 13) {
	    	id = e.target.id;
	    }
	    $('#exampleModalCenter3').modal('show');
	});


});
