jQuery(document).ready(function() {

    var enterkey = 13;

	$("#loginbtn").click(function(){
        localStorage.setItem('rolle', $( "#roller option:selected" ).text()); //Gemmer den valgte rolle i memory, til senere brug.
	});

	//Hvis 'Enter' trykkes simuleres et tryk på login knappen.
    $(document).keypress(function(e) {
        if(e.which === enterkey) {
            $("#loginbtn").click();
        }
    });


	
});

