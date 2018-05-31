/* Implement ajax call to rest Service */
$(window).on("load",function(){
	$(document).on('click', '.loginbtn', function() {
		event.stopImmediatePropagation();
		let user = $(".Username").val();
		let password = $(".Password").val();
		if (true) {
			$(".menuloader").load("menu.html");
			$(".loader").html("");
			$(".loader").load("brugeradmin.html"); 
			loadOperatoer();
			$(".Username").val("");
			$(".Password").val("");
		} else {
			$(".loginbtn").css("background-color", "#4286f4");
			$(".loginbtn").css("text-decoration-line","line-through");
			$(".wrongpass").css("display","block");
		}
	});

	$(document).on('click', '.opretbtn', function() {
		event.stopImmediatePropagation();
		$(".loader").load("opretbruger.html");
	});

	$(document).on('click', '.brugeradminbtn', function() {
		event.stopImmediatePropagation();
		$(".loader").load("brugeradmin.html");
		loadOperatoer();
	});

	$(document).on('click', '.logudbtn', function() {
		event.stopImmediatePropagation();
		$(".menuloader").html("");
		$(".loader").load("index.html");
	});

})


$("#createbtn").click(function(){
	var data2 = $("#brugerAdmin").serializeJSON();
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/operator/add', //specificerer endpointet
		data : data2,
		contentType : "application/JSON",
		type : 'POST', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert(data);
		},
		error : function(data){
			alert(data.responseText);
		}
	});
	return false;
})

$("#updatebtn").click(function(){
	var data2 = $("#brugerAdmin").serializeJSON();
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/operator/update', //specificerer endpointet
		data : data2,
		contentType : "application/JSON",
		type : 'PUT', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert(data);
		},
		error : function(data){
			alert(data.responseText);
		}
	});
	return false;
})



$(document).on('click', '#deleteUserBtn',function(){
	event.stopImmediatePropagation();
	var id = $(this).closest("tr").find("td:nth-child(1)").text();
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/operator/delete', //specificerer endpointet
		data : id,
		contentType : "plain/text",
		type : 'DELETE', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert(data);
			loadOperatoer();
		},
		error : function(data){
			alert(data.responseText);
		}
	});
});

$(document).on('click', '#modifyUserBtn',function(){
	event.stopImmediatePropagation();
	$(".loader").load("modificerbruger.html");
});

function clearTable(){
	$("#operatoerTable>tbody").empty();
	
};

function loadOperatoer() {
	$.ajax({ //Indleder et asynkront ajax kald
		url : 'rest/operator/display', //specificerer endpointet
		type : 'GET', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			clearTable();
			$.each(data,function(i,element){
				$('.brugeradmin').children().append(generateOperatoerHTML(data[i]));
			})
		},
		error : function(data){
			alert("error");
		}
	});
}
//Convenience function for generating html
function generateOperatoerHTML(operator) {
	return 	'<tr><td>' + operator.userId + '</td>' +
	'<td>' + operator.userName + '</td>' +
	'<td>' + operator.ini + '</td>' +
	'<td>' + operator.roles + '</td>' +
	'<td>' + operator.cpr + '</td>' +
	'<td><button id = "deleteUserBtn">Slet Bruger</button></td>' +
	'</tr>';
}
