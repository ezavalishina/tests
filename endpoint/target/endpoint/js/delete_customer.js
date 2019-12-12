$(document).ready(function(){
    $("#delete_customer_id").click(
        function() {
            var email = $("#del_email_id").val();

            // check fields
            if(email =='') {
                $('input[type="text"],input[type="password"]').css("border","2px solid red");
                $('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
                alert("Email is empty");
            } else {
                $.post({
                    url: 'rest/delete_customer',
                    headers: {
                        'Authorization': 'Basic ' + btoa('admin' + ':' + 'setup'),
                        'Content-Type': 'application/json'
                    },
                    data: JSON.stringify({
                        "firstName":null,
                        "lastName":null,
                        "login":email,
                        "pass":null,
                        "balance":null
                    })
                }).done(function(data) {
                    $.redirect('/customers.html', {'login': 'admin', 'pass': 'setup', 'role': 'ADMIN'}, 'GET');
                });
            }
        }
    );
});