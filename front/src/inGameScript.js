init();

var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var players;
var playersReady;
var cardsOnHand =[];


var card = new Object();
card.color = "";
card.value = 0;

var cards;


/*connections*/
webSocket.onopen = function (evt) {
    keepCon = setInterval(keepAlive, 10000);
    updateSocketPlayer();
    initialCards();
    getPlayersInfo();
}

function keepAlive() {
    request = {
        type: 'keepAlive',
        data: {}
    }
    webSocket.send(JSON.stringify(request));
}

webSocket.onmessage = function (JSONResponse) {
    var response = JSON.parse(JSONResponse.data);
    if (response.type == "getCardsByPlayer") {
        $("#cardsOnHand").empty();
        console.log(response.data);
        cards = response.data.cards;
        printInitialCards();
    }
    else if(response.type == "setSocketPlayer"){
        console.log(response.data.message);
    }
    else if(response.type == "getPlayersByRoom"){
        players=response.data.playersInfo;
        console.log(response.data);
        printPlayersInfo();
    }

}

function updateSocketPlayer() {
    var namePlayer = localStorage.getItem("playerName");
    var roomPlayer = localStorage.getItem("playerRoom");
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
    $(".droppable-area1, .droppable-area2").sortable({
        connectWith: ".connected-sortable",
        stack: '.connected-sortable ul',
        update: function(){
            removeEmptyColTam();
        }
    }).disableSelection();
}


$(document).ready(function () {
    $(".draggable").draggable();
    $(".hola21").droppable({
        over: function (event, ui) {
            $(this).addClass("droppable-image");
        },
        out: function (event, ui) {
            $(this).removeClass("droppable-image");
        },
        drop: function (e, ui) {
            addSection(ui.draggable.html());
            ui.draggable.remove();
            removeEmptyColTam();
            $(this).removeClass("droppable-image");
        }
    });
});


/*player info*/
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

function printPlayersInfo() {
    console.log("qwe");
    $.each(players, function (index, obj) {
        var clase = "#player" + (index + 1);
        console.log(clase);
        $(clase).text(obj.playerName);
    });
}

/**/

function removeEmptyColTam() {
    var cardsGameChildren = $("#cardsGame").children();
    for (i = 0; i < cardsGameChildren.length; i++) {
        var colTam = $(cardsGameChildren[i]);
        var img = colTam.find("img");
        if(img.length == 0){
            colTam.remove()
        }
    }
}

/*get card*/

function initialCards() {
    var nombre = localStorage.getItem("playerName");
    var playerRoom = localStorage.getItem("playerRoom")
    request = {
        type: 'getCardsByPlayer',
        data: {
            playerName: nombre,
            room: playerRoom
        }
    }
    webSocket.send(JSON.stringify(request));
}


/*Game functions*/

function checkMove() {
    console.log("checkMove");
}

/*addSection*/

function addSection(card) {

    var newRow = '<div class="connected-sortable droppable-area1 colTam"> <div class="draggable-item ">' + card + '</div></div>';

    $("#cardsGame").append(newRow);
    $(".newRow").html("");
    init();
}

/*ordenamientos*/

function orderCardsByColor(){
    updateCardsByPlayer();
    request = {
        type: 'sortCardsByColor',
        data: {
            room: localStorage.getItem("playerRoom")
        }
    }
    webSocket.send(JSON.stringify(request));
}

function orderCardsByNumber(){
    updateCardsByPlayer();
    request = {
        type: 'sortCardsByNumber',
        data: {
            room: localStorage.getItem("playerRoom")
        }
    }
    webSocket.send(JSON.stringify(request));
}

function updateCardsByPlayer(){
    var jugadas=$("#cardsOnHand").children();
    var dataTosend=[];
    $.each(jugadas,function(index,obj){
        var dataa = $(obj);
        var imgTag = $(dataa.html());
        dataTosend.push(cardsOnHand[imgTag.prop("src")]);
    });
    request = {
        type: 'updateCardsByPlayer',
        data: {
            cards: dataTosend,
            room: localStorage.getItem("playerRoom")
        }
    }
    webSocket.send(JSON.stringify(request));
}


function addCardToPlayer(){

    updateCardsByPlayer();
    request = {
        type: 'addCardToPlayer',
        data: {
            room:localStorage.getItem("playerRoom")
        }
    }
    webSocket.send(JSON.stringify(request));
}


/*send board*/

function sendBoard(){
    var board=$("#cardsGame").children();
    var dataTosend=[];
    $.each(board,function(index,obj){
        var data1 = $(obj);
        var dataTosend2=[];
        var hijos = data1.children();
        $.each(hijos,function(indexe,obje){
            var dataa = $(obje);
            var dixe =$("<div></div>");
            dixe.append(dataa);
            dataTosend2[index]=cardsOnHand[dixe.html()+""];
        });
        dataTosend[index]=dataTosend2;
    });
    console.log(dataTosend);
}


/*links cards   Black01 */

function printInitialCards() {

    $.each(cards, function (index, obj) {
        var card1;
        if (obj.valor == 0) {
            if (obj.color == "Black-Face") {card1 = obj.color;}
            if (obj.color == "Red-Face") {card1 = obj.color;}
        }
        if (obj.valor < 10 && obj.valor > 0) {
            card1 = obj.color + "0" + obj.valor;
        }
        if (obj.valor >= 10) {
            card1 = obj.color + obj.valor;
        }
        var card = "card-" + card1;
        var imgCard = '<div class="draggable-item "><img class="cardsTam" ';

    if (card == "card-Blue09") {
        imgCard += 'src="https://i.ibb.co/Pwy0NtK/card-Blue09.jpg">';
    } else if (card == "card-Blue01") {
        imgCard += 'src="https://i.ibb.co/fnMy5Dp/card-Blue01.jpg">';
    } else if (card == "card-Blue02") {
        imgCard += 'src="https://i.ibb.co/HTKwp9h/card-Blue02.jpg">';
    } else if (card == "card-Blue03") {
        imgCard += 'src="https://i.ibb.co/yQhwdSn/card-Blue03.jpg">';
    } else if (card == "card-Blue04") {
        imgCard += 'src="https://i.ibb.co/mBBckZT/card-Blue04.jpg">';
    } else if (card == "card-Blue05") {
        imgCard += 'src="https://i.ibb.co/qJ6XT8W/card-Blue05.jpg">';
    } else if (card == "card-Blue06") {
        imgCard += 'src="https://i.ibb.co/dMp6C2k/card-Blue06.jpg">';
    } else if (card == "card-Blue07") {
        imgCard += 'src="https://i.ibb.co/PTM5GZk/card-Blue07.jpg">';
    } else if (card == "card-Blue08") {
        imgCard += 'src="https://i.ibb.co/qkjwSVY/card-Blue08.jpg">';
    } else if (card == "card-Blue10") {
        imgCard += 'src="https://i.ibb.co/yRrMG27/card-Blue10.jpg">';
    } else if (card == "card-Blue11") {
        imgCard += 'src="https://i.ibb.co/4ZXcFZQ/card-Blue11.jpg">';
    } else if (card == "card-Blue12") {
        imgCard += 'src="https://i.ibb.co/jWbQDMs/card-Blue12.jpg">';
    } else if (card == "card-Blue13") {
        imgCard += 'src="https://i.ibb.co/SnNfPcv/card-Blue13.jpg">';
    } else if (card == "card-Red01") {
        imgCard += 'src="https://i.ibb.co/vZgV640/card-Red01.jpg">';
    } else if (card == "card-Red02") {
        imgCard += 'src="https://i.ibb.co/TknVWJY/card-Red02.jpg">';
    } else if (card == "card-Red03") {
        imgCard += 'src="https://i.ibb.co/bR4gFF3/card-Red03.jpg">';
    } else if (card == "card-Red04") {
        imgCard += 'src="https://i.ibb.co/xSbRYBm/card-Red04.jpg">';
    } else if (card == "card-Red05") {
        imgCard += 'src="https://i.ibb.co/BGTPj9s/card-Red05.jpg">';
    } else if (card == "card-Red06") {
        imgCard += 'src="https://i.ibb.co/drpLdz2/card-Red06.jpg">';
    } else if (card == "card-Red07") {
        imgCard += 'src="https://i.ibb.co/4JMRyn1/card-Red07.jpg">';
    } else if (card == "card-Red08") {
        imgCard += 'src="https://i.ibb.co/FxkQN2w/card-Red08.jpg">';
    } else if (card == "card-Red09") {
        imgCard += 'src="https://i.ibb.co/HPdngFK/card-Red09.jpg">';
    } else if (card == "card-Red10") {
        imgCard += 'src="https://i.ibb.co/w6VMHB4/card-Red10.jpg">';
    } else if (card == "card-Red11") {
        imgCard += 'src="https://i.ibb.co/cw1zWNq/card-Red11.jpg">';
    } else if (card == "card-Red12") {
        imgCard += 'src="https://i.ibb.co/cLPHF3Z/card-Red12.jpg">';
    } else if (card == "card-Red13") {
        imgCard += 'src="https://i.ibb.co/C5SBdBK/card-Red13.jpg">';
    } else if (card == "card-Red-Face") {
        imgCard += 'src="https://i.ibb.co/17VvpWc/card-Red-Face.jpg">';
    } else if (card == "card-Black-Face") {
        imgCard += 'src="https://i.ibb.co/9qFmSPb/card-Black-Face.jpg">';
    } else if (card == "card-Yellow01") {
        imgCard += 'src="https://i.ibb.co/6tN6RN1/card-Yellow01.jpg">';
    } else if (card == "card-Yellow02") {
        imgCard += 'src="https://i.ibb.co/whCr9D8/card-Yellow02.jpg">';
    } else if (card == "card-Yellow03") {
        imgCard += 'src="https://i.ibb.co/9tb79BM/card-Yellow03.jpg">';
    } else if (card == "card-Yellow04") {
        imgCard += 'src="https://i.ibb.co/qn4wp8G/card-Yellow04.jpg">';
    } else if (card == "card-Yellow05") {
        imgCard += 'src="https://i.ibb.co/BPJsnvf/card-Yellow05.jpg">';
    } else if (card == "card-Yellow06") {
        imgCard += 'src="https://i.ibb.co/qB3dW7h/card-Yellow06.jpg">';
    } else if (card == "card-Yellow07") {
        imgCard += 'src="https://i.ibb.co/ynY8vPL/card-Yellow07.jpg">';
    } else if (card == "card-Yellow08") {
        imgCard += 'src="https://i.ibb.co/y0Mn0rN/card-Yellow08.jpg">';
    } else if (card == "card-Yellow09") {
        imgCard += 'src="https://i.ibb.co/9w7zSmj/card-Yellow09.jpg">';
    } else if (card == "card-Yellow10") {
        imgCard += 'src="https://i.ibb.co/SxWth9d/card-Yellow10.jpg">';
    } else if (card == "card-Yellow11") {
        imgCard += 'src="https://i.ibb.co/vXpdd0z/card-Yellow11.jpg">';
    } else if (card == "card-Yellow12") {
        imgCard += 'src="https://i.ibb.co/bRSTLdv/card-Yellow12.jpg">';
    } else if (card == "card-Yellow13") {
        imgCard += 'src="https://i.ibb.co/Vtk0sBt/card-Yellow13.jpg">';
    } else if (card == "card-Black01") {
        imgCard += 'src="https://i.ibb.co/DgcjjHF/card-Black01.jpg">';
    } else if (card == "card-Black02") {
        imgCard += 'src="https://i.ibb.co/bKTXsDc/card-Black02.jpg">';
    } else if (card == "card-Black03") {
        imgCard += 'src="https://i.ibb.co/hBq8J26/card-Black03.jpg">';
    } else if (card == "card-Black04") {
        imgCard += 'src="https://i.ibb.co/VT4F0x9/card-Black04.jpg">';
    } else if (card == "card-Black05") {
        imgCard += 'src="https://i.ibb.co/n3b6RQn/card-Black05.jpg">';
    } else if (card == "card-Black06") {
        imgCard += 'src="https://i.ibb.co/j8s0VzQ/card-Black06.jpg">';
    } else if (card == "card-Black07") {
        imgCard += 'src="https://i.ibb.co/JpNmbtz/card-Black07.jpg">';
    } else if (card == "card-Black08") {
        imgCard += 'src="https://i.ibb.co/JyYJWNT/card-Black08.jpg">';
    } else if (card == "card-Black09") {
        imgCard += 'src="https://i.ibb.co/3BHLjKh/card-Black09.jpg">';
    } else if (card == "card-Black10") {
        imgCard += 'src="https://i.ibb.co/wYZQXVD/card-Black10.jpg">';
    } else if (card == "card-Black11") {
        imgCard += 'src="https://i.ibb.co/YZHcVFv/card-Black11.jpg">';
    } else if (card == "card-Black12") {
        imgCard += 'src="https://i.ibb.co/zZ454wv/card-Black12.jpg">';
    } else if (card == "card-Black13") {
        imgCard += 'src="https://i.ibb.co/HN7vBzL/card-Black13.jpg">';
    }
    imgCard += '</div>';
    imgCard = $(imgCard);
    var addToHash = $(imgCard.children("img")[0]);
    cardsOnHand[addToHash.prop("src")]=obj;
    $("#cardsOnHand").append(imgCard);
    });
    //console.log(cardsOnHand);
   // console.log("asd");
}


