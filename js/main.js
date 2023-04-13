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
    ws = new WebSocket("ws://localhost:8080/w23-csci2020u-project-team43-1.0-SNAPSHOT/ws/"+roomCode);



    
    
    // IGNORE THIS PART
    // while (ws.)
    // // Wait for 3 seconds before sending the "hello" message
    // setTimeout(function() {
    //     ws.send("hello");
    // }, 10000);
    
    // register username with server
}

// function used to make sure websocket is created and connected
function isConnected

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