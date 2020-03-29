 $('#inputGroupFile03').on('change',function(){
                //get the file name
                var fileName = $(this).val().replace('C:\\fakepath\\', "");
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);
            })

 $('#inputGroupFile04').on('change',function(){
                //get the file name
                var fileName = $(this).val().replace('C:\\fakepath\\', "");
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);
            })

document.getElementById("encrypt-btn").addEventListener("click", function(){
	if(document.getElementById("inputGroupFile03").value != "") {
		alert("da");
	}
	else
		alert("nu");
});