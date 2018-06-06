jQuery(document).ready(function() {

	$("#loginbtn").click(function(){
        localStorage.setItem('rolle', $( "#roller option:selected" ).text());
        alert(rolle+" saved");

	});


	
});

