$(document).ready(function() {
    var id;
    var value;
    var rolle;
    var statuss;
    const enterkey = 13;
    function loadOperatoer(){
        loadStatus();
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/all', //specificerer endpointet
            type : 'GET', //Typen af HTTP requestet (GET er default)
            success : function(data) {//Funktion der skal udføres når data er hentet
                clearOperatoerTable();
                $.each(data,function(i,element){
                    $('#operatoerAdminTable').children().append(generateOperatoerHTML(data[i]));
                });
                $(".slet").click(function(e){
                    id = e.target.id;
                    $('#deleteModal').modal('show');
                    e.preventDefault();
                });
                $(".update").click(function(e){
                    id = e.target.id;
                    $('#updateModal').modal('show');
                });
            },
            error : function(data){
                $.notify(data.responseText, "error");
            }
        });
    };
    loadOperatoer();

    function loadStatus() {
        $.ajax({ //Indleder et asynkront ajax kald
            url: 'rest/other/status_operatoer', //specificerer endpointet
            type: 'GET', //Typen af HTTP requestet (GET er default)
            success: function (data) {//Funktion der skal udføres når data er hentet
                statuss = data;
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


    $(".btn-primaryAdd").click(function(){
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/create', //specificerer endpointet
            data : JSON.stringify({
                oprId : $("#inputID")["0"].value,
                fornavn : $("#inputFornavn")["0"].value,
                efternavn : $("#inputEfternavn")["0"].value,
                cpr : $("#inputCPR")["0"].value
            }),
            contentType : "application/JSON",
            type : 'POST', //Typen af HTTP requestet (GET er default)
            success : function(data) {//Funktion der skal udføres når data er hentet
                $('#addModal').modal('hide');
                $.notify("Operatoeren blev operettet", "success");
                loadOperatoer();
            },
            error : function(data){
                $('#addModal').modal('hide');
                $.notify("Fejl ved oprettelse af operatoeren", "error");
                loadOperatoer();
            }
        });
    });


    $(".btn-primaryUpdate").click(function(){
    	var res = id.split("_");
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/update', //specificerer endpointet
            data : JSON.stringify({
                oprId : res[0],
                fornavn : $(("#"+res[0]+"_fornavn"))["0"].value,
                efternavn : $(("#"+res[0]+"_efternavn"))["0"].value,
                cpr : $(("#"+res[0]+"_cpr"))["0"].value,
                roles : " ",
                aktiv : $(("#"+res[0]+"_aktiv"))["0"].value
                
            }),
            contentType : "application/json",
            type : 'PUT', //Typen af HTTP requestet (GET er default)
            success : function(data) {//Funktion der skal udføres når data er hentet
                $('#updateModal').modal('hide');
                $.notify("Operatoeren blev opdateret", "success");
                loadOperatoer();
            },
            error : function(data){
                $('#updateModal').modal('hide');
                $.notify(data.responseText, "error");
                loadOperatoer();
            }
        });

    })

    $(".btn-primaryDelete").click(function(e){
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/'+id, //specificerer endpointet
            contentType : "plain/text",
            type : 'DELETE', //Typen af HTTP requestet (GET er default)
            success : function(data) {//Funktion der skal udføres når data er hentet
                $('#deleteModal').modal('hide');
                $.notify("Operatoeren blev slettet", "success");
                loadOperatoer();
            },
            error : function(data){
                $('#deleteModal').modal('hide');
                $.notify(data.responseText, "error");
                loadOperatoer();
            }
        });
        e.preventDefault();
    });

    $(".btn-secondaryDelete").click(function(){
        $('#deleteModal').modal('hide');
    });

    //Convenience function for generating html
    function generateOperatoerHTML(operatoer) {
        var status = new Array();
        status.push(operatoer.aktiv);

        if(statuss[0] == status[0]){
            status[1] = statuss[1];
        }else{
            status[1] = statuss[0];
        }

        // statuss.forEach((st) => {
        //     if (st != status[0]) {
        //         statuss.push(st);
        //         $.notify("Pushed")
        //     }
        // });

        return 	'<tr><th scope ="row">' + operatoer.oprId + '</th>' +
            '<td><input type="text" id = "'+operatoer.oprId+"_fornavn"+'" class="form-control-plaintext" value="' + operatoer.fornavn + '"></td></td>' +
            '<td><input type="text" id = "'+operatoer.oprId+"_efternavn"+'" class="form-control-plaintext" value="' + operatoer.efternavn + '"></td></td>' +
            '<td><span id = "'+operatoer.oprId+"_cpr"+'">'+operatoer.cpr+'</span></td></td>' +
            '<td><select class="" name="' + operatoer.oprId + '_aktiv" id="' + operatoer.oprId + '_status"><option value="' + status[0] + '">' + status[0]  + '</option><option value="' + status[1] + '">' + status[1] + '</option>></select></td></td>' +
            '<td><button type="button" id = "'+operatoer.oprId+'" class="btn btn-primary update"><i class="fas fa-sync" id = "'+operatoer.oprId+'"></i></button>'+'</td>' +
            '<td><button type="button" id = "'+operatoer.oprId+'" class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+operatoer.oprId+'"></i></button>'+'</td>' +
            '</td></tr>';
    }
    
    function clearOperatoerTable(){
        $("#operatoerAdminTable tbody").empty();
    };
    
    
    $(document).keypress(function(e) {
		if(e.which == enterkey) {
			id = e.target.id;
			$('#updateModal').modal('show');
		}

	});
    
});
