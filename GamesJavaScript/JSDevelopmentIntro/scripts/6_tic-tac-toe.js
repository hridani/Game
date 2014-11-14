//glabal variables
var theCanvas;
var c;
var ctx;
var painted;
var content;
var turn = 0;
var squaresFilled = 0;
var winCombo;

window.onload = function () {
    painted = new Array();
    content = new Array();
    winCombo = [[0, 1, 2], [3, 4, 5], [6, 7, 8],
                        [0, 3, 6], [1, 4, 7], [2, 5, 8],
                        [0, 4, 8], [2, 4, 6]];
    for (var i = 0; i < 9; i++) {
        painted[i] = false;
        content[i] = '';
    }
}
function canvasClicked(canvasNumber) {
    theCanvas = "canvas" + canvasNumber;
    c = document.getElementById(theCanvas);
    cxt = c.getContext("2d");
    if (painted[canvasNumber - 1] == false) {
        if (turn % 2 == 0) {
            cxt.beginPath();
            cxt.moveTo(10, 10);
            cxt.lineTo(40, 40);
            cxt.moveTo(40, 10);
            cxt.lineTo(10, 40);
            cxt.lineWidth = 5;
            cxt.strokeStyle = 'black';
            cxt.stroke();
            cxt.closePath();
            content[canvasNumber - 1] = 'X';
        }
        else {
            cxt.beginPath();
            cxt.arc(25, 25, 20, 0, Math.PI * 2, true);
            cxt.strokeStyle = 'green';
            cxt.lineWidth = 5;
            cxt.stroke();
            cxt.closePath();
            content[canvasNumber - 1] = 'O';
        }
        turn++;
        painted[canvasNumber - 1] = true;
        squaresFilled++;
        checkForWinners(content[canvasNumber - 1]);
        if (squaresFilled == 9) {
            alert("THE GAME IS OVER!");
            location.reload(true);
        }
    }
    else {
        alert("NO NO NO");
    }
}

function checkForWinners(symbol) {
    
    for (var a = 0; a < winCombo.length; a++) {
        if (content[winCombo[a][0]] == symbol &&
        content[winCombo[a][1]] == symbol &&
            content[winCombo[a][2]] == symbol) {
            //test

            var blnumb = "canvas" + winCombo[a][0];
            var blel = document.getElementById(theCanvas);
            setInterval(function(){
                if( blel.style.visibility=="hidden"){
                    blel.style.visibility=="visible";
                }else{
                    blel.style.visibility=="hidden";
                }
            },500,true);
            alert(symbol + " WON! ");
            playAgain();
        }
    }

}

function playAgain() {
    y = confirm("Play again?");
    if (y == true) {
        alert("OK");
        location.reload(true);
    }
    else {
        alert("So long...")
    }

}