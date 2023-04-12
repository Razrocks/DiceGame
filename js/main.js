// Getting and setting the username
let username;
let usernameInput = document.getElementById("usernameInput");
usernameInput.addEventListener("keydown", function(event){
    if (event.key === "Enter")
    {
        setUsername();
    }
})

function setUsername()
{
    username = usernameInput.value;
    usernameInput.value = "";
    
}

// Creating/joining a room
let ws;
let roomCode;
let roomCodeInput = document.getElementById("roomCodeInput");
roomCodeInput.addEventListener("keydown", function(event)
{
    if (event.key === "Enter")
    {
        setRoom();
    }
})

function setRoom()
{
    roomCode = roomCodeInput.value;
    roomCodeInput.value = "";

    // Connect to the websocket
    ws = new WebSocket("ws://localhost:8080/"+roomCode);
    // register username with server
}

function attack()
{
    //send attack message to server with username and command "attack"
}

function defend()
{
    //send defend message to server with username and command "defend"
}

function heal()
{
    //send heal message to server with username and command "heal"
}

function leave()
{
    output = document.getElementById("output");
    while (output.firstChild != null)
    {
        output.removeChild(output.firstChild);
    }
    ws.close();
}