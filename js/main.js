// Creating/joining a room
let ws;
let roomCode;
let roomCodeInput = document.getElementById("roomCodeInput");
let usernameInput = document.getElementById("usernameInput");
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
    }, 5000);

    // When a message is received from the server
    ws.addEventListener("message", function (event) 
    {
        console.log("Message from server: " + event.data);
        message = event.data.split(",");

        // Handling the messeges sent from the server
        switch (message[0])
        {
            case "OTHER":
                document.getElementById("OpponentHP").textContent = "Opponent HP:"+message[1]
                break;

            case "HP":
                document.getElementById("SelfHP").textContent = "Your HP:"+message[1];
                break;

            case "TAKEDMG":
                document.getElementById("SelfHP").textContent = "Your HP:"+message[1];
                break;
            
            case "ROLL":
                p = document.createElement("p");
                p.textContent = "You rolled:"+message[1]+". Do you want to Attack or Heal?";
                document.getElementById("output").append(p);
                break;

            case "NOTYOURTURN":
                p = document.createElement("p");
                p.textContent = "It is not your turn, please wait for your opponent to finish their turn";
                document.getElementById("output").append(p);
                break;
            case "FULLHP":
                p = document.createElement("p");
                p.textContent = "You are at full HP, you healed 0 HP";
                document.getElementById("output").append(p);
                break;

            case "WIN":
                p = document.createElement("p");
                p.textContent = "You won!";
                document.getElementById("output").append(p);
                break;
            
            case "LOSE":
                p = document.createElement("p");
                p.textContent = "You lost!";
                document.getElementById("output").append(p);
                break;
        }
    });
}

function attack()
{
    //send attack message
    ws.send("ATTACK");
    p = document.createElement("p");
    p.textContent = "You attacked";
    document.getElementById("output").append(p);
}

function heal()
{
    //send heal message to server
    ws.send("HEAL");
    p = document.createElement("p");
    p.textContent = "You healed";
    document.getElementById("output").append(p);
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
}