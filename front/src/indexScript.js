var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var namePlayer;
var roomToJoin;

/*connections*/
webSocket.onopen = function(evt) {
	keepCon = setInterval(keepAlive, 10000);
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
	//console.log(response);
	
	if(response.type == "createRoom"){
		answerPetition=response.data.room;
		localStorage.setItem("playerName",namePlayer);
		localStorage.setItem("playerRoom",answerPetition);
		window.location.href="room.html";
		//console.log(localStorage.getItem("playerName"));
	}
	else if(response.type == "joinToRoom"){
		if(response.data.message == "200 OK"){
			roomToJoin = $("#roomCode").val();
			localStorage.setItem("playerName",namePlayer);
			localStorage.setItem("playerRoom",roomToJoin);
			window.location.href="room.html";
		}	
		else if(response.data.message == "Player is already in room"){

			swal({
  			title: "Error!",
  			text: "Player is already in room!",
  			icon: "warning",
  			button: "OK",
		});
			//alert("Player is already in room");
		}
		else if(response.data.message == "Room is full"){
			
			swal({
  			title: "Error!",
  			text: "The room is full!",
  			icon: "warning",
  			button: "OK",
		});
			//alert("The Room is full");
		}
		else if(response.data.message == "Romm doesn't exist"){
			
			swal({
  			title: "Error!",
  			text: "The Room doesn't exist!",
  			icon: "warning",
  			button: "OK",
		});
			//alert("The Room doesn't exist");
		}
		else{alert("unknown error :o");console.log(response.data.message);}	
	}
}

/*methods*/
function createGame(){
	if ($("#playerNamee").val()!="") {
	namePlayer=$("#playerNamee").val();
	request = {
		type: 'createRoom',
		data: {
			playerName:namePlayer
		}
	}
	webSocket.send(JSON.stringify(request));
	}else{
		swal({
  			title: "Wrong name!",
  			text: "Ups Empty name!",
  			icon: "warning",
  			button: "OK",
		});

	}

}


function joinGame(){
	if ($("#playerNamee").val()!="") {
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
	}else{
		swal({
  			title: "Wrong name!",
  			text: "Ups Empty name or invalid room!",
  			icon: "warning",
  			button: "OK",
		});

	}

}

