var apiURL = "ws://25.133.184.153:8500";
var webSocket = new WebSocket(apiURL);

function joinGame(){
	codeRoom = $("#roomCode").val();
	console.log(codeRoom);

}

webSocket.onopen = function(evt) {
	setInterval(keepAlive(), 10000);
}

function keepAlive() {
	request = {
		type: 'keepAlive',
		data: {}
	}
	webSocket.send(JSON.stringify(request));
}

webSocket.onmessage = function(JSONResponse) {

}

function createGame(){
	console.log("creatingGame");
}


