function addToTable(data)
{
    // sorting data by wins
    data.sort((a, b) => b.wins - a.wins);

    // adding data to table
    let count = 1;
    let table = document.getElementById("leaderboard");
    for (let i = 0; i < data.length; i++)
    {
        // adding the first place with special css
        if (count === 1)
        {
            let row = document.createElement("tr");
            let rank = document.createElement("td");
            let name = document.createElement("td");
            let wins = document.createElement("td");

            rank.classList.add("first");
            name.classList.add("first");
            wins.classList.add("first");

            rank.textContent = count;
            name.textContent = data[i].name;
            wins.textContent = data[i].wins;

            row.append(rank);
            row.append(name);
            row.append(wins);
            count += 1;
            table.append(row);
        }
        else
        {
            let row = document.createElement("tr");
            let rank = document.createElement("td");
            let name = document.createElement("td");
            let wins = document.createElement("td");

            rank.textContent = count;
            name.textContent = data[i].name;
            wins.textContent = data[i].wins;

            row.append(rank);
            row.append(name);
            row.append(wins);
            count += 1;
            table.append(row);
        }

        
    }
}

// requestiong for the leaderboard data
(function requestData() {
    fetch("http://localhost:8080/w23-csci2020u-project-team43-1.0-SNAPSHOT/api/leaderboard",{
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
        .then(response => response.json())
        .then(response => addToTable(response))
        .catch((err) => {
            console.log("something went wrong: " + err);
        });
})();