const url = "http://localhost:8081/submit-stats";

function validateForm() {
    var missingFields = new Array();
    let errormsg = "";

    // for every field in the form, get its value and check if it's empty
    // if it's empty, then add it to a list of missing fields
    let steam64ID = document.forms["addStatsForm"]["steam64ID"].value;
    if (steam64ID == "") {
      missingFields.push("Steam ID");
    }
    
    let logid = document.forms["addStatsForm"]["logId"].value;
    if (logid == "") {
      missingFields.push("Log ID");
    }
    
    let map = document.forms["addStatsForm"]["map"].value;
    if (map == "") {
      missingFields.push("Map");
    }

    let classname = document.forms["addStatsForm"]["class"].value;
    if (classname == "") {
      missingFields.push("Class");
    }

    let killcount = document.forms["addStatsForm"]["killcount"].value;
    if (killcount == "") {
      missingFields.push("Kills");
    }

    let assistcount = document.forms["addStatsForm"]["assistcount"].value;
    if (assistcount == "") {
      missingFields.push("Assists");
    }

    let deathcount = document.forms["addStatsForm"]["deathcount"].value;
    if (deathcount == "") {
      missingFields.push("Deaths");
    }

    let damage = document.forms["addStatsForm"]["damage"].value;
    if (damage == "") {
      missingFields.push("Damage");
    }

    let damageTaken = document.forms["addStatsForm"]["damageTaken"].value;
    if (damageTaken == "") {
      missingFields.push("Damage Taken");
    }

    let seconds = document.forms["addStatsForm"]["seconds"].value;
    if (seconds == "") {
      missingFields.push("Seconds");
    }

    // for all of the missing fields noted, make a string list of them that will
    // be formatted nicely to be sent as an alert of all the unfilled fields
    if(missingFields.length > 0)
    {
        for(let i = 0; i < missingFields.length; i++)
        {
            let field = missingFields[i];
            if(i == (missingFields.length-1))
            {
                if(missingFields.length == 1)
                    errormsg = field;
                else
                    errormsg = errormsg + "and " + field;
            }
            else
            {
                errormsg = errormsg + field + ", ";
            }
        }
        alert("Missing the following fields: " + errormsg.toString())
        return false;
    }
    else{
        // if there are no missing fields, save the entered data and then send an alert
        saveStats();
        alert("Stats saved!");
    }
  } 

function saveStats(){

    // get all of the stats from the form
    var toSave = document.forms["addStatsForm"];

    // try to load any saved states and parse them as a dict
    var loadedStats = JSON.parse(localStorage.getItem('stats'));
    let toSaveDict = {};

    // if there are stats in localStorage, append the ones from the form onto them
    // if(loadedStats != null)
    // {
    //     toSaveDict = loadedStats;
    //     toSaveDict.steam64ID.push(toSave.steam64ID.value);
    //     toSaveDict.logId.push(toSave.logId.value);
    //     toSaveDict.map.push(toSave.map.value);
    //     toSaveDict.class.push(toSave.class.value);
    //     toSaveDict.kills.push(toSave.killcount.value);
    //     toSaveDict.assists.push(toSave.assistcount.value);
    //     toSaveDict.deaths.push(toSave.deathcount.value);
    //     toSaveDict.damage.push(toSave.damage.value);
    //     toSaveDict.damageTaken.push(toSave.damageTaken.value);
    //     toSaveDict.seconds.push(toSave.seconds.value);
    // }
    // else
    // {
    //     // if there aren't any stats saved in localStorage, then create a new
    //     // dict of arrays to hold the stats
        toSaveDict = {
            logId: toSave.logId.value,
            steam64Id: toSave.steam64ID.value,
            mapId: toSave.map.value,
            className: toSave.class.value,
            kills: toSave.killcount.value,
            assists: toSave.assistcount.value,
            deaths: toSave.deathcount.value,
            damage: toSave.damage.value,
            damageTaken: toSave.damageTaken.value,
            seconds: toSave.seconds.value
        }
    // }

    // save the dict you've added the form stats to to local storage
    localStorage.setItem('stats', JSON.stringify(toSaveDict));
    request = JSON.stringify(toSaveDict);
    // localStorage.setItem('users', request);

    event.preventDefault();
    console.log("about to try submitting users w/ request");
    console.log(request);
    fetch(url, {
        method: 'POST',
        headers: {'Content-type': 'application/json'},
        body: JSON.stringify(toSaveDict)})
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const data = isJson ? await response.json() : null;
        console.log(response)

        if (response.status != 201) {
            return Promise.reject(data || {'status': response.status, 'message' : 'Unexpected Error'});
        }
        loadStats();
    })
    .catch(error => {
        alert('There was an error!\n' +  error.message);
    }).finally(() => {
         $('.form-popup').hide();
    });
}

function displaySavedRows(table, loadedStats)
{
    // check if there are any stats loaded from local storage
    if(loadedStats)
    {
        count = loadedStats.logId.length;
        for (let i = 0; i < count; i++)
        {
            // if the table has data rows in it, 
            // for every entry loaded in from localstorage, delete the first data row
            // table row 0 is the header, so we don't want to delete that
            // this will incrementally delete all previously loaded entries in the table
            if(table.rows.length > 1)
            {
                table.deleteRow(1);
            }
        }

        // for each loaded entry, create a data row
        for (let i = 0; i < count; i++)
        {
            const row = document.createElement("tr");

            // create a cell in the row for the logId, set the text to be loaded text
            // and then add it to the row
            const cLogId = document.createElement("td");
            const cLogIdText = document.createTextNode(loadedStats.logId[i]);
            cLogId.appendChild(cLogIdText);
            row.appendChild(cLogId);

            // repeat for all of the saved stats
            const csteam64ID = document.createElement("td");
            const csteam64IDText = document.createTextNode(loadedStats.steam64ID[i]);
            csteam64ID.appendChild(csteam64IDText);
            row.appendChild(csteam64ID);

            const cMap = document.createElement("td");
            const cMapText = document.createTextNode(loadedStats.map[i]);
            cMap.appendChild(cMapText);
            row.appendChild(cMap);

            const cClass = document.createElement("td");
            const cClassText = document.createTextNode(loadedStats.class[i]);
            cClass.appendChild(cClassText);
            row.appendChild(cClass);

            const cKills = document.createElement("td");
            const cKillText = document.createTextNode(loadedStats.kills[i]);
            cKills.appendChild(cKillText);
            row.appendChild(cKills);

            const cAssists = document.createElement("td");
            const cAssistsText = document.createTextNode(loadedStats.assists[i]);
            cAssists.appendChild(cAssistsText);
            row.appendChild(cAssists);

            const cDeaths = document.createElement("td");
            const cDeathsText = document.createTextNode(loadedStats.deaths[i]);
            cDeaths.appendChild(cDeathsText);
            row.appendChild(cDeaths);

            const cDamage = document.createElement("td");
            const cDamageText = document.createTextNode(loadedStats.damage[i]);
            cDamage.appendChild(cDamageText);
            row.appendChild(cDamage);

            const cDamageTaken = document.createElement("td");
            const cDamageTakenText = document.createTextNode(loadedStats.damageTaken[i]);
            cDamageTaken.appendChild(cDamageTakenText);
            row.appendChild(cDamageTaken);

            const cSeconds = document.createElement("td");
            const cSecondsText = document.createTextNode(loadedStats.seconds[i]);
            cSeconds.appendChild(cSecondsText);
            row.appendChild(cSeconds);

            // finally once we're done adding cells onto the row, we append the row onto the table
            table.append(row);
        }
    }
    else
    {
        alert("No stats saved! Try submitting some first.");
    }

}

function loadStats(){
    // load the table and locally stored stats and pass them to a function that will display the data
    const table = document.getElementById('all-stats');
    var loadedStats = JSON.parse(localStorage.getItem('stats'));

    displaySavedRows(table, loadedStats);
}