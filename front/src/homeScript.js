
var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var players;

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
	if(response.type == "updateState"){}
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
		if(localStorage.getItem("playerName") == obj.playerName){
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
	console.log();
}

