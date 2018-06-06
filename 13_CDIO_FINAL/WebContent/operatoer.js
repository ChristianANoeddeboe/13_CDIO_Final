$(document).ready(function() {
    var id;
    var value;
    var rolle;
    const enterkey = 13;
    function loadOperatoer(){
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/all', //specificerer endpointet
            type : 'GET', //Typen af HTTP requestet (GET er default)
            success : function(data) {//Funktion der skal udføres når data er hentet
                clearOperatoerTable();
                $.each(data,function(i,element){
                    data.status = 'Aktiv'
                    $('#operatoerAdminTable').children().append(generateOperatoerHTML(data[i]));
                });
                $(".slet").click(function(e){
                    id = e.target.id;
                    $('#deleteModal').modal('show');
                    e.preventDefault();
                });
            },
            error : function(data){
                $.notify(data.responseText, "error");
            }
        });
    };
    loadOperatoer();

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
        $.ajax({ //Indleder et asynkront ajax kald
            url : 'rest/operatoer/update', //specificerer endpointet
            data : JSON.stringify({
                oprId : id,
                fornavn : value
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

    $(".btn-primaryDelete").click(function(){
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

    });

    $(".btn-secondaryDelete").click(function(){
        $('#deleteModal').modal('hide');
    });

    //Convenience function for generating html
    function generateOperatoerHTML(operatoer) {
        return 	'<tr><th scope ="row">' + operatoer.id + '</th>' +
            '<td><input type="text" id = "'+operatoer.id+'"class="form-control-plaintext" value="' + operatoer.fornavn + '"></td></td>' +
            '<td><input type="text" id = "'+operatoer.id+'"class="form-control-plaintext" value="' + operatoer.efternavn + '"></td></td>' +
            '<td><input type="text" id = "'+operatoer.id+'"class="form-control-plaintext" value="' + operatoer.cpr + '"></td></td>' +
            '<td><input type="text" id = "'+operatoer.id+'"class="form-control-plaintext" value="' + operatoer.status + '"></td></td>' +
            '<td><button type="button" id = "'+operatoer.id+'"class="btn btn-primary slet"><i class="far fa-trash-alt" id = "'+operatoer.id+'"></i></button>'+'</td>' +
            '</td></tr>';
    }

    function clearOperatoerTable(){
        $("#operatoerAdminTable>tbody").empty();
    };

});
