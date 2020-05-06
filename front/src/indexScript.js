var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var namePlayer;
/*connections*/
webSocket.onopen = function(evt) {
	keepCon = setInterval(keepAlive, 10000);
}

localStorage.setItem("a", 'basdasd');
console.log(localStorage.getItem("a"));

function keepAlive() {
	request = {
		type: 'keepAlive',
		data: {}
	}
	webSocket.send(JSON.stringify(request));
}

webSocket.onmessage = function(JSONResponse){
	var response = JSON.parse(JSONResponse.data);
	//console.log(response);
	
	if(response.type == "createRoom"){
		answerPetition=response.data.room;

		Cookies.set('playerName',namePlayer);
		Cookies.set('playerRoom',answerPetition);
		console.log("affff");
		console.log(Cookies.get('playerName'));
	}
	else if(response.type == "joinToRoom"){
		if(response.data.message == "200 OK"){statusPetition=response.data.message;}
		else if(response.data.message == "Player is already in room"){alert("Player is already in room");}
		else if(response.data.message == "Room is full"){alert("The Room is full");}
		else if(response.data.message == "Romm doesn't exist"){alert("The Room doesn't exist");}
		else{alert("unknown error :o");console.log(response.data.message);}	
	}
}

/*methods*/
function createGame(){
	namePlayer=$("#playerNamee").val();
	request = {
		type: 'createRoom',
		data: {
			playerName:namePlayer
		}
	}
	webSocket.send(JSON.stringify(request));
	//window.location.href="room.html";
}


function joinGame(){
	namePlayer=$("#playerNamee").val();
	codeRoom = $("#roomCode").val();	
	request = {
		type: 'joinToRoom',
		data: {
			playerName: namePlayer,
			room: codeRoom
		}
	}
	webSocket.send(JSON.stringify(request));
}

