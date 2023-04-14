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

function attack()
{
    //send attack message
    ws.send("attack")
    //send attack message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has attacked"
    document.getElementById("output").append(p);

    //turn addition
    turn += 1;
}

function defend()
{
    //send defend message to server
    ws.send("defend")
    //send defend message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has defended"
    document.getElementById("output").append(p);

    //turn addition
    turn += 1;
}

function heal()
{
    //send heal message to server
    ws.send("heal")
    //send heal message to output
    p = document.createElement("p")
    p.textContent = "(Turn " + turn + "): " + usernameInput.value + " has healed"
    document.getElementById("output").append(p);

    //turn addition
    turn += 1;
}

// need to catch all the messages from the server

function leave()
{
    output = document.getElementById("output");
    while (output.firstChild != null)
    {
        output.removeChild(output.firstChild);
    }
    ws.close();

    //leave message for room
    p = document.createElement("p")
    p.textContent = usernameInput.value + " has left the room"
    document.getElementById("output").append(p);

    //buttons work when room is left
}