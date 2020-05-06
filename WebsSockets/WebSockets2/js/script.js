var myUsername = prompt("Por favor ingrese su nombre");
var aliveConn;
$("#enviar").click(function() {
    enviarMensaje();
});

//notar el protocolo.. es 'ws' y no 'http'
//var wsUri = "ws://25.133.184.153:30001";
var wsUri = "ws://localhost:30001";

var websocket = new WebSocket(wsUri); //creamos el socket

websocket.onopen = function(evt) { //manejamos los eventos...
    console.log("Conectado..."); //... y aparecerá en la pantalla
    login(myUsername);
};

websocket.onmessage = function(evt) { // cuando se recibe un mensaje
    $("#chat").html($("#chat").html() + evt.data);
    $("#chat").html($("#chat").html() + "<br>");
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
    aliveConn = setInterval(function() {
        b = {
            type: 'alive'
        }
        websocket.send(JSON.stringify(b));
    }, 5000);
}

function enviarMensaje() {
    message = $("#message").val();
    a={
        username: myUsername,
        type:'message',
        receiver: null,
        message: message
    };
    var mes=JSON.stringify(a);
    websocket.send(mes);
    console.log("Enviando:" + message);
}