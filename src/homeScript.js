var StP1=0;
var StP2=0;
var StP3=0;
var StP4=0;


function startGame(){
	var num =StP1+StP2+StP3+StP4; 
	if (num >= 2) {
		alert("empieza el juego con: "+num+" jugadores");
	}else{alert("El juego no puede empezar con menos de 2 jugadores");}
}

function statusPlayer1(){
	statePlayer1 = document.getElementById("changeState1");
	if(statePlayer1.innerHTML == "Ready" ) {
		$("#changeState1").replaceWith('<a id="changeState1" onclick="statusPlayer1();" class="btn btn-primary">'+'Not Ready'+'</a>');
		$("#statusPlayer1").replaceWith('<div class="card-footer text-muted" id="statusPlayer1">'+"I'm Ready"+'</div>');
		StP1=1;
	}else if (statePlayer1.innerHTML == "Not Ready" ) {
		$("#changeState1").replaceWith('<a id="changeState1" onclick="statusPlayer1();" class="btn btn-primary">'+'Ready'+'</a>');
		$("#statusPlayer1").replaceWith('<div class="card-footer text-muted" id="statusPlayer1">'+"I'm not Ready"+'</div>');
		StP1=0;
	}
}


function statusPlayer2(){
	statePlayer2 = document.getElementById("changeState2");
	if(statePlayer2.innerHTML == "Ready" ) {
		$("#changeState2").replaceWith('<a id="changeState2" onclick="statusPlayer2();" class="btn btn-primary">'+'Not Ready'+'</a>');
		$("#statusPlayer2").replaceWith('<div class="card-footer text-muted" id="statusPlayer2">'+"I'm Ready"+'</div>');
		StP2=1;
	}else if (statePlayer2.innerHTML == "Not Ready" ) {
		$("#changeState2").replaceWith('<a id="changeState2" onclick="statusPlayer2();" class="btn btn-primary">'+'Ready'+'</a>');
		$("#statusPlayer2").replaceWith('<div class="card-footer text-muted" id="statusPlayer2">'+"I'm not Ready"+'</div>');
		StP2=0;
	}
}


function statusPlayer3(){
	statePlayer3 = document.getElementById("changeState3");
	if(statePlayer3.innerHTML == "Ready" ) {
		$("#changeState3").replaceWith('<a id="changeState3" onclick="statusPlayer3();" class="btn btn-primary">'+'Not Ready'+'</a>');
		$("#statusPlayer3").replaceWith('<div class="card-footer text-muted" id="statusPlayer3">'+"I'm Ready"+'</div>');
		StP3=1;
	}else if (statePlayer3.innerHTML == "Not Ready" ) {
		$("#changeState3").replaceWith('<a id="changeState3" onclick="statusPlayer3();" class="btn btn-primary">'+'Ready'+'</a>');
		$("#statusPlayer3").replaceWith('<div class="card-footer text-muted" id="statusPlayer3">'+"I'm not Ready"+'</div>');
		StP3=0;
	}
}


function statusPlayer4(){
	statePlayer4 = document.getElementById("changeState4");
	if(statePlayer4.innerHTML == "Ready" ) {
		$("#changeState4").replaceWith('<a id="changeState4" onclick="statusPlayer4();" class="btn btn-primary">'+'Not Ready'+'</a>');
		$("#statusPlayer4").replaceWith('<div class="card-footer text-muted" id="statusPlayer4">'+"I'm Ready"+'</div>');
		StP4=1;
	}else if (statePlayer4.innerHTML == "Not Ready" ) {
		$("#changeState4").replaceWith('<a id="changeState4" onclick="statusPlayer4();" class="btn btn-primary">'+'Ready'+'</a>');
		$("#statusPlayer4").replaceWith('<div class="card-footer text-muted" id="statusPlayer4">'+"I'm not Ready"+'</div>');
		StP4=0;
	}
}