 $('#inputGroupFile03').on('change',function(){
                //get the file name
                var fileName = $(this).val().replace('C:\\fakepath\\', "");
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);

                var empty = false;
                if(document.getElementById("inputGroupFile03").value == "")
                    empty = true;
                if (empty) {
                    $('#encrypt-btn').attr('disabled', 'disabled');
                } else {
                    $('#encrypt-btn').removeAttr('disabled');
                }
  });

 $('#inputGroupFile04').on('change',function(){
                //get the file name
                var fileName = $(this).val().replace('C:\\fakepath\\', "");
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);

                 var empty = false;
                if(document.getElementById("inputGroupFile03").value == "")
                    empty = true;
                if (empty) {
                    $('#encrypt-btn').attr('disabled', 'disabled');
                } else {
                    $('#encrypt-btn').removeAttr('disabled');
                }
 });


function handleFileSelect(evt) {
    var files = evt.target.files;
    f = files[0];
    var reader = new FileReader();

    reader.onload = (function(theFile) {
        return function(e) {
        var fileContent = e.target.result;
        var key = fileContent.split("\n")[0].trim();
        console.log(key);
        if(/^[a-zA-Z]+$/.test(key) === false){
            Swal.fire(
                'Oops..',
                 'Your key should contain only letters and be a single string',
                 'error'
            )
        }};
      })(f);

      reader.readAsText(f);
  }


document.getElementById('inputGroupFile03').addEventListener('change', handleFileSelect, false);
document.getElementById('inputGroupFile04').addEventListener('change', handleFileSelect, false);
