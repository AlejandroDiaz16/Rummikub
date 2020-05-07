init();

var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var players;
var playersReady;
var card = new Object();
    card.color = "";
    card.value = 0;
var cards;


/*connections*/
webSocket.onopen = function(evt) {
    keepCon = setInterval(keepAlive, 10000);
    updateSocketPlayer();
 //   getPlayersInfo();
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
    if(response.type == "getCardsByPlayer"){
        console.log(response.data);
        cards = response.data.cards;
    }

}

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

/*droppable*/

function init() {
    $( ".droppable-area1, .droppable-area2" ).sortable({
        connectWith: ".connected-sortable",
        stack: '.connected-sortable ul'
    }).disableSelection();
}


$(document).ready(function(){
    $(".draggable").draggable();
    $(".hola21").droppable({
         over: function(event, ui){
            $(this).addClass("droppable-image");
        },
        out: function(event, ui) {
             $(this).removeClass("droppable-image");
            },
        drop: function(e, ui){
            console.log(ui.draggable);
            addSection(ui.draggable); 
            $(this).removeClass("droppable-image");
        }
    });
});


/*get card*/

function initialCards(){
    var nombre=localStorage.getItem("playerName");
    var playerRoom=localStorage.getItem("playerRoom")
    request = {
        type:'getCardsByPlayer',
        data: {
            playerName:nombre,
            room:playerRoom
        }
    }
    webSocket.send(JSON.stringify(request));
}

function printInitialCards(){
    console.log(cards.length);
    $.each(cards,function(index,obj){
        var card;
        //if(obj.color = "Black-Face"){card="Black-Face";}
        //if(obj.color = "Red-Face"){card="Red-Face";}
        if(obj.valor == 0){
            if(obj.color == "Black-Face"){console.log("negro");card=obj.color;}
            if(obj.color == "Red-Face"){console.log("rojo");card=obj.color;}
        }
        if(obj.valor <10 && obj.valor >0 ){ card=obj.color+"0"+obj.valor;}
        if(obj.valor >=10){ card=obj.color+obj.valor;}
        
        //if(obj.color =="Black-Face"){card=obj.color;}

        addToHand(card);
    });
}

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

/*Game functions*/

function orderByColor(){
   console.log("orderColor"); 
}

function normalOrder(){
    console.log(cards);
}

function extraCard(cardName){
    addToHand(cardName);
    console.log("extraCard");
}

function checkMove(){
    console.log("checkMove");
}

function addToHand(cardName){
    var card = callCard(cardName);
    //document.getElementById('#cardsOnHand')
    $('#cardsOnHand').append(card);
}

/*addSection*/
function addSection(card){
   
    var newRow =$('<div class="connected-sortable droppable-area1 colTam"> </div>');
   // var dives = $('<div></div>');
    
    //dives.append(card);
    newRow.append(card);
    
    console.log(card.html());
    console.log(newRow.html());
    $("#cardsGame").append(newRow);
    $(".newRow").html("");
    init();
}



/*links cards*/
function callCard(cardName){
    var card="card-"+cardName;
    var imgCard='<div class="draggable-item "><img class="cardsTam" ';

    if(card == "card-Blue09"){imgCard+='src="https://i.ibb.co/Pwy0NtK/card-Blue09.jpg"  >';}
    else if(card =="card-Blue01"){imgCard+='src="https://i.ibb.co/fnMy5Dp/card-Blue01.jpg"  >';}
    else if(card =="card-Blue02"){imgCard+='src="https://i.ibb.co/HTKwp9h/card-Blue02.jpg"  >';}
    else if(card =="card-Blue03"){imgCard+='src="https://i.ibb.co/yQhwdSn/card-Blue03.jpg"  >';}
    else if(card =="card-Blue04"){imgCard+='src="https://i.ibb.co/mBBckZT/card-Blue04.jpg"  >';}
    else if(card =="card-Blue05"){imgCard+='src="https://i.ibb.co/qJ6XT8W/card-Blue05.jpg"  >';}
    else if(card =="card-Blue06"){imgCard+='src="https://i.ibb.co/dMp6C2k/card-Blue06.jpg"  >';}
    else if(card =="card-Blue07"){imgCard+='src="https://i.ibb.co/PTM5GZk/card-Blue07.jpg"  >';}
    else if(card =="card-Blue08"){imgCard+='src="https://i.ibb.co/qkjwSVY/card-Blue08.jpg"  >';}
    else if(card =="card-Blue10"){imgCard+='src="https://i.ibb.co/yRrMG27/card-Blue10.jpg"  >';}
    else if(card =="card-Blue11"){imgCard+='src="https://i.ibb.co/4ZXcFZQ/card-Blue11.jpg"  >';}
    else if(card =="card-Blue12"){imgCard+='src="https://i.ibb.co/jWbQDMs/card-Blue12.jpg"  >';}
    else if(card =="card-Blue13"){imgCard+='src="https://i.ibb.co/1Rxn4dT/card-Blue13.jpg"  >';}
    
    else if(card =="card-Red01"){imgCard+='src="https://i.ibb.co/vZgV640/card-Red01.jpg" >';}
    else if(card =="card-Red02"){imgCard+='src="https://i.ibb.co/TknVWJY/card-Red02.jpg" >';}
    else if(card =="card-Red03"){imgCard+='src="https://i.ibb.co/bR4gFF3/card-Red03.jpg" >';}
    else if(card =="card-Red04"){imgCard+='src="https://i.ibb.co/xSbRYBm/card-Red04.jpg" >';}
    else if(card =="card-Red05"){imgCard+='src="https://i.ibb.co/BGTPj9s/card-Red05.jpg" >';}
    else if(card =="card-Red06"){imgCard+='src="https://i.ibb.co/drpLdz2/card-Red06.jpg" >';}
    else if(card =="card-Red07"){imgCard+='src="https://i.ibb.co/4JMRyn1/card-Red07.jpg" >';}
    else if(card =="card-Red08"){imgCard+='src="https://i.ibb.co/FxkQN2w/card-Red08.jpg" >';}
    else if(card =="card-Red09"){imgCard+='src="https://i.ibb.co/HPdngFK/card-Red09.jpg" >';}
    else if(card =="card-Red10"){imgCard+='src="https://i.ibb.co/w6VMHB4/card-Red10.jpg" >';}
    else if(card =="card-Red11"){imgCard+='src="https://i.ibb.co/cw1zWNq/card-Red11.jpg" >';}
    else if(card =="card-Red12"){imgCard+='src="https://i.ibb.co/cLPHF3Z/card-Red12.jpg" >';}
    else if(card =="card-Red13"){imgCard+='src="https://i.ibb.co/31j998r/card-Red13.jpg" >';}
    
    else if(card =="card-Red-Face"){imgCard+='src="https://i.ibb.co/17VvpWc/card-Red-Face.jpg"  >';}
    else if(card =="card-Black-Face"){imgCard+='src="https://i.ibb.co/9qFmSPb/card-Black-Face.jpg">';}

    else if(card =="card-Yellow01"){imgCard+='src="https://i.ibb.co/6tN6RN1/card-Yellow01.jpg"  >';}
    else if(card =="card-Yellow02"){imgCard+='src="https://i.ibb.co/whCr9D8/card-Yellow02.jpg"  >';}
    else if(card =="card-Yellow03"){imgCard+='src="https://i.ibb.co/9tb79BM/card-Yellow03.jpg"  >';}
    else if(card =="card-Yellow04"){imgCard+='src="https://i.ibb.co/qn4wp8G/card-Yellow04.jpg"  >';}
    else if(card =="card-Yellow05"){imgCard+='src="https://i.ibb.co/BPJsnvf/card-Yellow05.jpg"  >';}
    else if(card =="card-Yellow06"){imgCard+='src="https://i.ibb.co/qB3dW7h/card-Yellow06.jpg"  >';}
    else if(card =="card-Yellow07"){imgCard+='src="https://i.ibb.co/ynY8vPL/card-Yellow07.jpg"  >';}
    else if(card =="card-Yellow08"){imgCard+='src="https://i.ibb.co/y0Mn0rN/card-Yellow08.jpg"  >';}
    else if(card =="card-Yellow09"){imgCard+='src="https://i.ibb.co/9w7zSmj/card-Yellow09.jpg"  >';}
    else if(card =="card-Yellow10"){imgCard+='src="https://i.ibb.co/SxWth9d/card-Yellow10.jpg"  >';}
    else if(card =="card-Yellow11"){imgCard+='src="https://i.ibb.co/vXpdd0z/card-Yellow11.jpg"  >';}
    else if(card =="card-Yellow12"){imgCard+='src="https://i.ibb.co/bRSTLdv/card-Yellow12.jpg"  >';}
    else if(card =="card-Yellow13"){imgCard+='src="https://i.ibb.co/0Q0xXYD/card-Yellow13.jpg"  >';}

    else if(card =="card-Black01"){imgCard+='src="https://i.ibb.co/DgcjjHF/card-Black01.jpg" >';}
    else if(card =="card-Black02"){imgCard+='src="https://i.ibb.co/bKTXsDc/card-Black02.jpg">';}
    else if(card =="card-Black03"){imgCard+='src="https://i.ibb.co/hBq8J26/card-Black03.jpg">';}
    else if(card =="card-Black04"){imgCard+='src="https://i.ibb.co/VT4F0x9/card-Black04.jpg">';}
    else if(card =="card-Black05"){imgCard+='src="https://i.ibb.co/n3b6RQn/card-Black05.jpg">';}
    else if(card =="card-Black06"){imgCard+='src="https://i.ibb.co/j8s0VzQ/card-Black06.jpg">';}
    else if(card =="card-Black07"){imgCard+='src="https://i.ibb.co/JpNmbtz/card-Black07.jpg">';}
    else if(card =="card-Black08"){imgCard+='src="https://i.ibb.co/JyYJWNT/card-Black08.jpg">';}
    else if(card =="card-Black09"){imgCard+='src="https://i.ibb.co/3BHLjKh/card-Black09.jpg">';}
    else if(card =="card-Black10"){imgCard+='src="https://i.ibb.co/wYZQXVD/card-Black10.jpg">';}
    else if(card =="card-Black11"){imgCard+='src="https://i.ibb.co/YZHcVFv/card-Black11.jpg">';}
    else if(card =="card-Black12"){imgCard+='src="https://i.ibb.co/zZ454wv/card-Black12.jpg">';}
    else if(card =="card-Black13"){imgCard+='src="https://i.ibb.co/ZByFYNg/card-Black13.jpg">';}

    return imgCard+='</div>'; 
}


function append(){

  var element = document.getElementById("cardsOnHand");
  var jsonObject = {};
  jsonObject.id = element.id;
  jsonObject.innerHTML = element.innerHTML;

  var jsonString = JSON.stringify(jsonObject); // this is json for your div. 
console.log(jsonString);

  
}