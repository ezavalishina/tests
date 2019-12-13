$(document).ready(function(){
    $.get({
        url: 'rest/get_customers',
        headers: {
            'Authorization': 'Basic ' + btoa('admin' + ':' + 'setup')
        }
    }).done(function(data) {
        var json = $.parseJSON(data);

        var dataSet = []
        for(var i = 0; i < json.length; i++) {
            var obj = json[i];
            dataSet.push([obj.firstName, obj.lastName, obj.login])
        }

        //$("#user_customers_table_id").html(data);
        $('#user_customers_table_id')
            .DataTable({
                data: dataSet,
                columns: [
                    { title: "Fist Name" },
                    { title: "Last Name" },
                    { title: "Email" }
                ]
            });
    });
});