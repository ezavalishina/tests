$(document).ready(function () {
    $("#login").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        var way = 'rest/get_customer_id/' + email;
        // Checking for blank fields.
        if (email == '' || password == '') {
            $('input[type="text"],input[type="password"]').css("border", "2px solid red");
            $('input[type="text"],input[type="password"]').css("box-shadow", "0 0 3px red");

            if (email == '') {
                alert("Email is empty");
            } else {
                alert("Password is empty");
            }
        } else {
            $.get({
                url: 'rest/get_role',
                headers: {
                    'Authorization': 'Basic ' + btoa(email + ':' + password)
                }
            })
                .done(function (data) {
                    var dataObj = $.parseJSON(data);
                    if (dataObj['role'] == 'ADMIN') {
                        $.redirect('customers.html', {'login': email, 'pass': password, 'role': 'ADMIN'}, 'GET');
                    } else if (dataObj['role'] == 'UNKNOWN') {
                        $.get({
                            url: way,
                            headers: {
                                'Authorization': 'Basic ' + btoa(email + ':' + password)
                            }
                        })
                            .done(function (data) {
                                if (data['pass'] == password) {
                                    $.redirect('user_customers_view.html', {
                                        'login': email,
                                        'pass': password,
                                        'role': 'UNKNOWN'
                                    }, 'GET');
                                } else {
                                    alert("Password is incorrect");
                                }
                            })
                            .fail(function (data) {
                                $('input[type="text"],input[type="password"]').css({
                                    "border": "2px solid red",
                                    "box-shadow": "0 0 3px red"
                                });
                                alert("Customer with this login is not exist");
                            })
                    }
                });
        }
    });
});