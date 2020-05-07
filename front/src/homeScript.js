
var apiURL = "ws://25.133.184.148:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var players;
var playersReady;

/*connections*/
webSocket.onopen = function(evt) {
	keepCon = setInterval(keepAlive, 10000);
	updateSocketPlayer();
	getPlayersInfo();
}

function keepAlive() {
	request = {
		type: 'keepAlive',
		data: {}
	}
	webSocket.send(JSON.stringify(request));
}

webSocket.onmessage = function(JSONResponse){
	var response = JSON.parse(JSONResponse.data);
	if(response.type == "getPlayersByRoom"){
		players=response.data.playersInfo;
		printPlayersInfo();
	}
	else if(response.type == "updateState"){console.log(response.data.message);}
	else if(response.type == "startGame"){
		if(response.data.message == "200 OK"){
			playersReady=response.data.isPlayersReady;
			if(playersReady == true){window.location.href="inGame.html";}
			else{alert("Are missing players to be ready")}
		}
		else if(response.data.message == "Room doesn't exist"){alert("The room doesn't exist");}
		else if(response.data.message == "Don't enough players"){alert("There aren't enough players");}
	}

}



/*functions*/

/*init()*/
function updateSocketPlayer(){
	var namePlayer=localStorage.getItem("playerName");
	var roomPlayer= localStorage.getItem("playerRoom");
	request = {
		type: 'setSocketPlayer',
		data: {
			playerName: namePlayer,
			room: roomPlayer
		}
	}
	webSocket.send(JSON.stringify(request));
}

function getPlayersInfo(){
	var roomPlayer= localStorage.getItem("playerRoom");
	request = {
		type: 'getPlayersByRoom',
		data: {
			room: roomPlayer
		}
	}
	webSocket.send(JSON.stringify(request));
}

/* player name and status*/
function printPlayersInfo(){
	console.log(players.length);
	$.each(players,function(index, obj){
		var clase="#player"+(index+1);
		var statusPlayer="#statusPlayer"+(index+1);
		var changeState="#changeState"+(index+1);
		console.log(clase);
		$(clase).text(obj.playerName);
		if(obj.state == false){$(statusPlayer).text("I'm not ready");}
		else if(obj.state == true){$(statusPlayer).text("I'm ready");}
		$(changeState).addClass("changes");
		if(localStorage.getItem("playerName").toLowerCase() == obj.playerName.toLowerCase()){
			$(changeState).removeClass("changes");
		}
	});
}

function changeStatus(){
	request = {
		type:'updateState',
		data: {
			room:localStorage.getItem("playerRoom")
		}
	}
	webSocket.send(JSON.stringify(request));
}



/*start the game*/
function startGame(){
	request = {
		type: 'startGame',
		data: {
			room:localStorage.getItem("playerRoom")
		}
	}
	webSocket.send(JSON.stringify(request));
}

