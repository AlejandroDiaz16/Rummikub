init();
function init() {
    $( ".droppable-area1, .droppable-area2" ).sortable({
        connectWith: ".connected-sortable",
        stack: '.connected-sortable ul'
    }).disableSelection();
}










$(document).ready(function(){
    $(".draggable").draggable();
    $(".droppable").droppable({
        drop: function(e, ui){
            //  alert("hola");

        }
    });
});
