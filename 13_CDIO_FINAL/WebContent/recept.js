/**
 * 
 */

$(document).ready(function() {
	var id;
	var value;
	const enterkey = 13;
	function loadUsers(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/all', //specificerer endpointet
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
				alert("Error fetching data");
			}
		});
	};
	
	loadUsers();

	
	
	$(".btn-primaryAdd").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/create', //specificerer endpointet
			data : JSON.stringify({
				receptId : $("#inputID")["0"].value,
				receptNavn : $("#inputName")["0"].value
			}),
			contentType : "application/JSON",
			type : 'POST', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#addModal').modal('hide');
				$.notify("Recepten blev operettet", "success");
				loadUsers();
			},
			error : function(data){
				alert("error happend");
			}
		});
	});
	
	
	
	$(".btn-primaryUpdate").click(function(){		
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/update', //specificerer endpointet
			data : JSON.stringify({
				receptId : id,
				receptNavn : value
			}),
			contentType : "application/json",
			type : 'PUT', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#updateModal').modal('hide');
				$.notify("Recepten blev opdateret", "success");
				loadUsers();
			},
			error : function(data){
				alert("Error happend");
			}
		});

	})
	
	
	$(".btn-primaryDelete").click(function(){
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/recept/'+id, //specificerer endpointet
			contentType : "plain/text",
			type : 'DELETE', //Typen af HTTP requestet (GET er default)
			success : function(data) {//Funktion der skal udføres når data er hentet
				$('#deleteModal').modal('hide');
				$.notify("Recepten blev slettet", "success");
				loadUsers();
			},
			error : function(data){
				alert("Error happend");
			}
		});

	});
	
	$(".btn-secondaryDelete").click(function(){
		$('#deleteModal').modal('hide');
	});
	
	
	//Convenience function for generating html
	function generateOperatoerHTML(recept) {
		return 	'<tr><th scope ="row">' + recept.receptId + '</th>' +
		'<td><input type="text" id = "'+recept.receptId +'"class="form-control-plaintext" value="' + recept.receptNavn + '"></td></td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary vis" data-toggle="modal" data-target="#showMoreModal">▼</button>'+'</td>' +
		'<td><button type="button" id = "'+recept.receptId+'"class="btn btn-primary slet" data-toggle="modal" data-target="#deleteModal"><i class="far fa-trash-alt"></i></button>'+'</td>' +
		'</td></tr>';
	}

	function clearTable(){
		$("#receptTable>tbody").empty();

	};
	
	
	$(document).keypress(function(e) {
		if(e.which == enterkey) {
			id = e.target.id;
			value = e.target.value;
			$('#updateModal').modal('show');
		}
		
	});

});
