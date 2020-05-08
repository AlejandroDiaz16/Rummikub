init();

var apiURL = "ws://25.133.184.153:12500";
var webSocket = new WebSocket(apiURL);
var keepCon;
var answerPetition;
var statusPetition;
var players;
var playersReady;
var cardsRepositoryArray = [];
var cardsRepositoryMap = [];

var card = new Object();
card.color = "";
card.value = 0;

cardsRepositoryArray.push({color: 'Black-Face', valor: 0});
cardsRepositoryArray.push({color: 'Red-Face', valor: 0});
for (i=1; i<=14; i++) {
cardsRepositoryArray.push({color: 'Blue', valor: i});
cardsRepositoryArray.push({color: 'Black', valor: i});
cardsRepositoryArray.push({color: 'Yellow', valor: i});
cardsRepositoryArray.push({color: 'Red', valor: i});
}

createHashMapCards(cardsRepositoryArray);

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
        printCardsByPlayer(cards);
    }
    else if(response.type == "setSocketPlayer"){
        console.log(response.data.message);
    }
    else if(response.type == "getPlayersByRoom"){
        players=response.data.playersInfo;
        console.log(response.data);
        printPlayersInfo();
    }
    else if(response.type == "updateBoard"){
        console.log(response.data.message);
    }
    else if(response.type == "getBoard"){
        console.log(response.data);
        cards = response.data.board.jugadas;
        console.log(response.data);
        console.log(cards);
        printCardsOnBoard(cards);
    }
}

function printCardsOnBoard(cards) {
console.log("cartas "+cards);
    $.each(cards,function(index,obj){
        console.log("linea75: "+obj)
        var roww = $("<div class='connected-sortable droppable-area1 colTam ui-sortable'></div>"); 
        $.each(obj.cartas,function(indexe,obje){
            console.log("este: "+obje);
            var cardURL = getURLByCard(obje);
            var imgTag = $("<img></img>");
            imgTag.prop("src", cardURL);
            imgTag.addClass("cardsTam");
            var divv = $("<div class='draggable-item'></div>")
            divv.append(imgTag);
            roww.append(divv);
        });
        $("#cardsGame").append(roww);
    });
}

function printCardsByPlayer(cards) {
    $.each(cards,function(index, obj){
        var cardURL = getURLByCard(obj);
        var imgTag = $("<img></img>");
        imgTag.prop("src", cardURL);
        imgTag.addClass("cardsTam");
        var divv = $("<div class='draggable-item'></div>")
        divv.append(imgTag);
        $("#cardsOnHand").append(divv);
    });
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
        dataTosend.push(cardsRepositoryMap[imgTag.prop("src")]);
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
            imgCard = $(dataa.html());
            //console.log(imgCard.prop("src"));    
            dataTosend2.push(cardsRepositoryMap[imgCard.prop("src")]);
        });
        dataTosend.push({cards:dataTosend2});
    });
    request = {
        type: 'updateBoard',
        data: {
            jugadas: dataTosend,
            room: localStorage.getItem("playerRoom")
        }
    }
    console.log(request);
    webSocket.send(JSON.stringify(request));
}


/*links cards   Black01 */

function getURLByCard(card) {
    var card1;
    if (card.valor == 0) {
        if (card.color == "Black-Face") {card1 = card.color;}
        if (card.color == "Red-Face") {card1 = card.color;}
    }
    if (card.valor < 10 && card.valor > 0) {
        card1 = card.color + "0" + card.valor;
    }
    if (card.valor >= 10) {
        card1 = card.color + card.valor;
    }
    var cardName = "card-" + card1;
    var imgCard = '<div class="draggable-item "><img class="cardsTam" ';

    if (cardName == "card-Blue09") {
        imgCard += 'src="https://i.ibb.co/Pwy0NtK/card-Blue09.jpg">';
    } else if (cardName == "card-Blue01") {
        imgCard += 'src="https://i.ibb.co/fnMy5Dp/card-Blue01.jpg">';
    } else if (cardName == "card-Blue02") {
        imgCard += 'src="https://i.ibb.co/HTKwp9h/card-Blue02.jpg">';
    } else if (cardName == "card-Blue03") {
        imgCard += 'src="https://i.ibb.co/yQhwdSn/card-Blue03.jpg">';
    } else if (cardName == "card-Blue04") {
        imgCard += 'src="https://i.ibb.co/mBBckZT/card-Blue04.jpg">';
    } else if (cardName == "card-Blue05") {
        imgCard += 'src="https://i.ibb.co/qJ6XT8W/card-Blue05.jpg">';
    } else if (cardName == "card-Blue06") {
        imgCard += 'src="https://i.ibb.co/dMp6C2k/card-Blue06.jpg">';
    } else if (cardName == "card-Blue07") {
        imgCard += 'src="https://i.ibb.co/PTM5GZk/card-Blue07.jpg">';
    } else if (cardName == "card-Blue08") {
        imgCard += 'src="https://i.ibb.co/qkjwSVY/card-Blue08.jpg">';
    } else if (cardName == "card-Blue10") {
        imgCard += 'src="https://i.ibb.co/yRrMG27/card-Blue10.jpg">';
    } else if (cardName == "card-Blue11") {
        imgCard += 'src="https://i.ibb.co/4ZXcFZQ/card-Blue11.jpg">';
    } else if (cardName == "card-Blue12") {
        imgCard += 'src="https://i.ibb.co/jWbQDMs/card-Blue12.jpg">';
    } else if (cardName == "card-Blue13") {
        imgCard += 'src="https://i.ibb.co/SnNfPcv/card-Blue13.jpg">';
    } else if (cardName == "card-Red01") {
        imgCard += 'src="https://i.ibb.co/vZgV640/card-Red01.jpg">';
    } else if (cardName == "card-Red02") {
        imgCard += 'src="https://i.ibb.co/TknVWJY/card-Red02.jpg">';
    } else if (cardName == "card-Red03") {
        imgCard += 'src="https://i.ibb.co/bR4gFF3/card-Red03.jpg">';
    } else if (cardName == "card-Red04") {
        imgCard += 'src="https://i.ibb.co/xSbRYBm/card-Red04.jpg">';
    } else if (cardName == "card-Red05") {
        imgCard += 'src="https://i.ibb.co/BGTPj9s/card-Red05.jpg">';
    } else if (cardName == "card-Red06") {
        imgCard += 'src="https://i.ibb.co/drpLdz2/card-Red06.jpg">';
    } else if (cardName == "card-Red07") {
        imgCard += 'src="https://i.ibb.co/4JMRyn1/card-Red07.jpg">';
    } else if (cardName == "card-Red08") {
        imgCard += 'src="https://i.ibb.co/FxkQN2w/card-Red08.jpg">';
    } else if (cardName == "card-Red09") {
        imgCard += 'src="https://i.ibb.co/HPdngFK/card-Red09.jpg">';
    } else if (cardName == "card-Red10") {
        imgCard += 'src="https://i.ibb.co/w6VMHB4/card-Red10.jpg">';
    } else if (cardName == "card-Red11") {
        imgCard += 'src="https://i.ibb.co/cw1zWNq/card-Red11.jpg">';
    } else if (cardName == "card-Red12") {
        imgCard += 'src="https://i.ibb.co/cLPHF3Z/card-Red12.jpg">';
    } else if (cardName == "card-Red13") {
        imgCard += 'src="https://i.ibb.co/C5SBdBK/card-Red13.jpg">';
    } else if (cardName == "card-Red-Face") {
        imgCard += 'src="https://i.ibb.co/17VvpWc/card-Red-Face.jpg">';
    } else if (cardName == "card-Black-Face") {
        imgCard += 'src="https://i.ibb.co/9qFmSPb/card-Black-Face.jpg">';
    } else if (cardName == "card-Yellow01") {
        imgCard += 'src="https://i.ibb.co/6tN6RN1/card-Yellow01.jpg">';
    } else if (cardName == "card-Yellow02") {
        imgCard += 'src="https://i.ibb.co/whCr9D8/card-Yellow02.jpg">';
    } else if (cardName == "card-Yellow03") {
        imgCard += 'src="https://i.ibb.co/9tb79BM/card-Yellow03.jpg">';
    } else if (cardName == "card-Yellow04") {
        imgCard += 'src="https://i.ibb.co/qn4wp8G/card-Yellow04.jpg">';
    } else if (cardName == "card-Yellow05") {
        imgCard += 'src="https://i.ibb.co/BPJsnvf/card-Yellow05.jpg">';
    } else if (cardName == "card-Yellow06") {
        imgCard += 'src="https://i.ibb.co/qB3dW7h/card-Yellow06.jpg">';
    } else if (cardName == "card-Yellow07") {
        imgCard += 'src="https://i.ibb.co/ynY8vPL/card-Yellow07.jpg">';
    } else if (cardName == "card-Yellow08") {
        imgCard += 'src="https://i.ibb.co/y0Mn0rN/card-Yellow08.jpg">';
    } else if (cardName == "card-Yellow09") {
        imgCard += 'src="https://i.ibb.co/9w7zSmj/card-Yellow09.jpg">';
    } else if (cardName == "card-Yellow10") {
        imgCard += 'src="https://i.ibb.co/SxWth9d/card-Yellow10.jpg">';
    } else if (cardName == "card-Yellow11") {
        imgCard += 'src="https://i.ibb.co/vXpdd0z/card-Yellow11.jpg">';
    } else if (cardName == "card-Yellow12") {
        imgCard += 'src="https://i.ibb.co/bRSTLdv/card-Yellow12.jpg">';
    } else if (cardName == "card-Yellow13") {
        imgCard += 'src="https://i.ibb.co/Vtk0sBt/card-Yellow13.jpg">';
    } else if (cardName == "card-Black01") {
        imgCard += 'src="https://i.ibb.co/DgcjjHF/card-Black01.jpg">';
    } else if (cardName == "card-Black02") {
        imgCard += 'src="https://i.ibb.co/bKTXsDc/card-Black02.jpg">';
    } else if (cardName == "card-Black03") {
        imgCard += 'src="https://i.ibb.co/hBq8J26/card-Black03.jpg">';
    } else if (cardName == "card-Black04") {
        imgCard += 'src="https://i.ibb.co/VT4F0x9/card-Black04.jpg">';
    } else if (cardName == "card-Black05") {
        imgCard += 'src="https://i.ibb.co/n3b6RQn/card-Black05.jpg">';
    } else if (cardName == "card-Black06") {
        imgCard += 'src="https://i.ibb.co/j8s0VzQ/card-Black06.jpg">';
    } else if (cardName == "card-Black07") {
        imgCard += 'src="https://i.ibb.co/JpNmbtz/card-Black07.jpg">';
    } else if (cardName == "card-Black08") {
        imgCard += 'src="https://i.ibb.co/JyYJWNT/card-Black08.jpg">';
    } else if (cardName == "card-Black09") {
        imgCard += 'src="https://i.ibb.co/3BHLjKh/card-Black09.jpg">';
    } else if (cardName == "card-Black10") {
        imgCard += 'src="https://i.ibb.co/wYZQXVD/card-Black10.jpg">';
    } else if (cardName == "card-Black11") {
        imgCard += 'src="https://i.ibb.co/YZHcVFv/card-Black11.jpg">';
    } else if (cardName == "card-Black12") {
        imgCard += 'src="https://i.ibb.co/zZ454wv/card-Black12.jpg">';
    } else if (cardName == "card-Black13") {
        imgCard += 'src="https://i.ibb.co/HN7vBzL/card-Black13.jpg">';
    }
    imgCard += '</div>';
    imgCard = $(imgCard);
    var addToHash = $(imgCard.children("img")[0]);
    return addToHash.prop("src");
}

function createHashMapCards(cards) {

    $.each(cards, function (index, obj) {
        cardsRepositoryMap[getURLByCard(obj)]=obj;
    });
    //console.log(cardsOnHand);
   // console.log("asd");
}


