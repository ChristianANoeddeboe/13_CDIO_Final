const enterkey = 13;
function getEnum(url, successfunc, errorfunc){
	$.ajax({ //Indleder et asynkront ajax kald
		url: url, //specificerer endpointet
		type: 'GET', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			statuss = data;
			successfunc();
		},
		error: function (data) {
			errorfunc();
		}
	});
};

function get(url, successfunc, errorfunc){
	$.ajax({ //Indleder et asynkront ajax kald
		url: url, //specificerer endpointet
		type: 'GET', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			successfunc(data);
		},
		error: function (data) {
			errorfunc(data);
		}
	});
};

function post(url, data, successfunc, errorfunc){
	$.ajax({ //Indleder et asynkront ajax kald
		url : url, //specificerer endpointet
		data : data,
		contentType : "application/JSON",
		type : 'POST', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			successfunc(data);
		},
		error : function(data){
			errorfunc(data);
		}
	});
};

function put(url,data,successfunc, errorfunc){
	var res = id.split("_");
	$.ajax({ //Indleder et asynkront ajax kald
		url : url, //specificerer endpointet
		data : data,
		contentType : "application/json",
		type : 'PUT', //Typen af HTTP requestet (GET er default)
		success : function(data) {//Funktion der skal udføres når data er hentet
			successfunc(data);
		},
		error : function(data){
			errorfunc(data);
		}
	});
}

function Delete(url,successfunc,errorfunc){
	$.ajax({ //Indleder et asynkront ajax kald
		url: url, //specificerer endpointet
		contentType: "plain/text",
		type: 'DELETE', //Typen af HTTP requestet (GET er default)
		success: function (data) {//Funktion der skal udføres når data er hentet
			successfunc(data);
		},
		error: function (data) {
			errorfunc(data);
		}
	});
}