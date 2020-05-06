init();

var card = new Object();
card.color = "";
card.value = 0;


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
            addSection(ui.draggable.html()); 
            $(this).removeClass("droppable-image");
        }
    });
});

/*get data*/

function getData(){
    var element = document.getElementById("cardsGame");
    var jsonObject = {};

    jsonObject.id = element.id;
    jsonObject.innerHTML = element.innerHTML;
    console.log(jsonObject);
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
   
    var newRow = '<div class="connected-sortable droppable-area1 colTam"> <div class="draggable-item ">'+card+'</div></div>';
    $("#cardsGame").append(newRow);
    $(".newRow").html("");
    init();
}



/*links cards*/
function callCard(cardName){
    var card="card-"+cardName;
    var imgCard='<div class="draggable-item "><img class="cardsTam" ';

    if(card == "card-Blue09"){imgCard+='src="https://i.ibb.co/Pwy0NtK/card-Blue09.jpg"  >';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
    else if(card =="card-Blue0"){imgCard+='';}
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
    
    else if(card =="Red-Face"){imgCard+='src="https://i.ibb.co/17VvpWc/card-Red-Face.jpg"  >';}
    else if(card =="Black-Face"){imgCard+='';}

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
    else if(card =="card-Yellow11"){imgCard+='';}
    else if(card =="card-Yellow12"){imgCard+='src="https://i.ibb.co/bRSTLdv/card-Yellow12.jpg"  >';}
    else if(card =="card-Yellow13"){imgCard+='src="https://i.ibb.co/0Q0xXYD/card-Yellow13.jpg"  >';}
    return imgCard+='</div>'; 
}