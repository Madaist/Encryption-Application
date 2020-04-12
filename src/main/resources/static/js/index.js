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
                if(document.getElementById("inputGroupFile04").value == "")
                    empty = true;
                if (empty) {
                    $('#decrypt-btn').attr('disabled', 'disabled');
                } else {
                    $('#decrypt-btn').removeAttr('disabled');
                }
 });


function handleEncryptFileSelect(evt) {
    var files = evt.target.files;
    f = files[0];
    var reader = new FileReader();

    reader.onload = (function(theFile) {
        return function(e) {
            var fileContent = e.target.result;
            var key1 = fileContent.split("\n")[0];
            var key2 = fileContent.split("\n")[1];
            var message = fileContent.substring(key1.length + key2.length).trim();

            fileContent = fileContent.replace(/\s/g, '');
            message = message.replace(/\s/g, '');
            key1 = key1.trim();
            key2 = key2.trim();

            if(fileContent == ''){
                Swal.fire('Oops..', 'It seems like your file is empty', 'error')
            }
            else if(/^[a-zA-Z]+$/.test(key1) === false){
                Swal.fire('Oops..', 'Your first key should contain only letters and be a single string', 'error')
            }
            else if(/^[a-zA-Z]+$/.test(key2) === false){
                Swal.fire('Oops..', 'Your second key should contain only letters and be a single string', 'error')
            }
            else if(!message){
                Swal.fire('Oops..', 'It seems like your text is empty', 'error')
            }
            else if(/^[a-zA-Z]+$/.test(message) === false){
                Swal.fire('Oops..', 'Your message should contain only letters', 'error')
            }
        };
      })(f);

      reader.readAsText(f);
  }


document.getElementById('inputGroupFile03').addEventListener('change', handleEncryptFileSelect, false);

function handleDecryptFileSelect(evt) {
    var files = evt.target.files;
    f = files[0];
    var reader = new FileReader();

    reader.onload = (function(theFile) {
        return function(e) {
            var fileContent = e.target.result;
            var key1 = fileContent.split("\n")[0];
            var key2 = fileContent.split("\n")[1];
            var message = fileContent.substring(key1.length + key2.length).trim();

            fileContent = fileContent.replace(/\s/g, '');
            message = message.replace(/\s/g, '');
            key1 = key1.trim();
            key2 = key2.trim();

            if(fileContent == ''){
                Swal.fire('Oops..', 'It seems like your file is empty', 'error')
            }
            else if(/^[a-zA-Z]+$/.test(key1) === false){
                Swal.fire('Oops..', 'Your first key should contain only letters and be a single string', 'error')
            }
            else if(/^[a-zA-Z]+$/.test(key2) === false){
                Swal.fire('Oops..', 'Your second key should contain only letters and be a single string', 'error')
            }
            else if(!message){
                Swal.fire('Oops..', 'It seems like your text is empty', 'error')
            }
            else if( /^\d+$/.test(message) === false){
                Swal.fire('Oops..', 'Your message should contain only digits', 'error')
            }
            else if(message.length % 2 != 0)
            {
                Swal.fire('Oops..', 'Your message should contain an even number of digits', 'error')
            }
        };
      })(f);

      reader.readAsText(f);
  };

document.getElementById('inputGroupFile04').addEventListener('change', handleDecryptFileSelect, false);