// Creating/joining a room
let ws;
let roomCode;
let roomCodeInput = document.getElementById("roomCodeInput");
let usernameInput = document.getElementById("usernameInput");
let turn;
function setRoomAndUsername()
{
    roomCode = roomCodeInput.value;
    roomCodeInput.value = "";
    // Connect to the websocket
    ws = new WebSocket("ws://localhost:8080/w23-csci2020u-project-team43-1.0-SNAPSHOT/ws/"+roomCode);

    // TEMPORARY FIX (LEAVE THIS IF A BETTER METHOD IS NOT FOUND)
    // waiting a bit for the connection to be established (maybe find a way to send this msg only when the connection is established)
    // ws.readyState would be something to look at for this
    setTimeout(function() {
        ws.send("username:" + usernameInput.value);
    }, 10000);

    //display room code joined on output
    document.getElementById('output').innerHTML =  usernameInput.value + ' has joined Room: ' + roomCode

    //turn reset
    turn = 1;
}

function endTurn() {
    //sets next turn
    turn += 1;

    //Display player healths *TO DO*

    //Depending on how shield works display shields maybe *TO DO*

    //Display next players turn *TO DO*

}

function attack()
{
    //send attack message
    ws.send("attack")
    //send attack message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has attacked"
    document.getElementById("output").append(p);

    //turn end
    endTurn();
}

function defend()
{
    //send defend message to server
    ws.send("defend")
    //send defend message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has defended"
    document.getElementById("output").append(p);

    //turn end
    endTurn();
}

function heal()
{
    //send heal message to server
    ws.send("heal")
    //send heal message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has healed"
    document.getElementById("output").append(p);


    //turn end
    endTurn();
}

// need to catch all the messages from the server

function leave()
{
    output = document.getElementById("output");
    while (output.firstChild != null)
    {
        output.removeChild(output.firstChild);
    }

    //leave message for room
    p = document.createElement("p")
    p.textContent = usernameInput.value + " has left the room"
    document.getElementById("output").append(p);

    ws.close();

    //ws is set to null so the buttons will not work as ws is reset when room is left
    ws = null;
}