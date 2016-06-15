/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function runtestanalizer() {
    alert("llego al script");
    var dataT = {
        "name": "Daniel",
        "password": "1234"


    };
    var endpoint = document.getElementById("endpoint").value;
    $.ajax({
        type: "POST",
        // data : JSON.stringify(dataT),
        dataType: "json",
        contentType: "application/text", //recibe
        url: "http://localhost:8080/GSAnalizer-1/webservices/analizer/run?resource=\""+endpoint+"\"",
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            alert("Error" + data);
        }
    });
}


//	loadTemplateNames();





function miFuncion()
{
    alert("Activaste la funcion miFuncion() jjj");
}

