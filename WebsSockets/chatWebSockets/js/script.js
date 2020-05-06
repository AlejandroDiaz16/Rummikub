var myUsername = prompt("Por favor ingrese su nombre");
var receiverUsername = prompt("Por favor ingrese el nombre de la persona con quien quiere iniciar un chat");

$("#enviar").click(function() {
    enviarMensaje();
});

//notar el protocolo.. es 'ws' y no 'http'
var wsUri = "ws://localhost:30001";
var websocket = new WebSocket(wsUri); //creamos el socket

websocket.onopen = function(evt) { //manejamos los eventos...
    console.log("Conectado..."); //... y aparecerá en la pantalla
    login(myUsername);
};

websocket.onmessage = function(evt) { // cuando se recibe un mensaje
    $("#chat").html($("#chat").html() + evt.data);
};

websocket.onerror = function(evt) {
    console.log("oho!.. error:" + evt.data);
};

function login(username) {
    a={
        type:'login',
        username:username
    };
    var mes=JSON.stringify(a);
    websocket.send(mes);
}

function enviarMensaje() {
    message = $("#message").val();
    a={
        type:'message',
        receiver: receiverUsername,
        message: message
    };
    var mes=JSON.stringify(a);
    websocket.send(mes);
    console.log("Enviando:" + message);
}


