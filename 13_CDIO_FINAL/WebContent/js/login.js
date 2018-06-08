jQuery(document).ready(function() {

    var enterkey = 13;

	$("#loginbtn").click(function(){
        localStorage.setItem('rolle', $( "#roller option:selected" ).text());
	});

    $(document).keypress(function(e) {
        if(e.which === enterkey) {
            $("#loginbtn").click();
        }
    });


	
});

