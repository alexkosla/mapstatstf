function validateForm() {
    var missingFields = new Array();
    let errormsg = "";

    // for every field in the form, get its value and check if it's empty
    // if it's empty, then add it to a list of missing fields
    let username = document.forms["fstats"]["username"].value;
    if (username == "") {
      missingFields.push("Username");
    }
    
    let steamid = document.forms["fstats"]["steamId"].value;
    if (steamid == "") {
      missingFields.push("Steam ID");
    }
    
    let logid = document.forms["fstats"]["logId"].value;
    if (logid == "") {
      missingFields.push("Log ID");
    }
    
    let map = document.forms["fstats"]["map"].value;
    if (map == "") {
      missingFields.push("Map");
    }

    let classname = document.forms["fstats"]["class"].value;
    if (classname == "") {
      missingFields.push("Class");
    }

    let killcount = document.forms["fstats"]["killcount"].value;
    if (killcount == "") {
      missingFields.push("Kills");
    }

    let deathcount = document.forms["fstats"]["deathcount"].value;
    if (deathcount == "") {
      missingFields.push("Deaths");
    }

    let damage = document.forms["fstats"]["damage"].value;
    if (damage == "") {
      missingFields.push("Damage");
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
    var toSave = document.forms["fstats"];

    // try to load any saved states and parse them as a dict
    var loadedStats = JSON.parse(localStorage.getItem('stats'));
    let toSaveDict = {};

    // if there are stats in localStorage, append the ones from the form onto them
    if(loadedStats != null)
    {
        toSaveDict = loadedStats;
        toSaveDict.username.push(toSave.username.value);
        toSaveDict.steamId.push(toSave.steamId.value);
        toSaveDict.logId.push(toSave.logId.value);
        toSaveDict.map.push(toSave.map.value);
        toSaveDict.class.push(toSave.class.value);
        toSaveDict.kills.push(toSave.killcount.value);
        toSaveDict.deaths.push(toSave.deathcount.value);
        toSaveDict.damage.push(toSave.damage.value);
    }
    else
    {
        // if there aren't any stats saved in localStorage, then create a new
        // dict of arrays to hold the stats
        toSaveDict = {
            username: [toSave.username.value],
            steamId: [toSave.steamId.value],
            logId: [toSave.logId.value],
            map: [toSave.map.value],
            class: [toSave.class.value],
            kills: [toSave.killcount.value],
            deaths: [toSave.deathcount.value],
            damage: [toSave.damage.value]
        }
    }

    // save the dict you've added the form stats to to local storage
    localStorage.setItem('stats', JSON.stringify(toSaveDict));
}

function displaySavedRows(table, loadedStats)
{
    // check if there are any stats loaded from local storage
    if(loadedStats)
    {
        count = loadedStats.username.length;
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

            // create a cell in the row for the username, set the text to be loaded text
            // and then add it to the row
            const cUsername = document.createElement("td");
            const cUsernameText = document.createTextNode(loadedStats.username[i]);
            cUsername.appendChild(cUsernameText);
            row.appendChild(cUsername);

            // repeat for all of the saved stats
            const cSteamId = document.createElement("td");
            const cSteamIdText = document.createTextNode(loadedStats.steamId[i]);
            cSteamId.appendChild(cSteamIdText);
            row.appendChild(cSteamId);

            const cLogId = document.createElement("td");
            const cLogIdText = document.createTextNode(loadedStats.logId[i]);
            cLogId.appendChild(cLogIdText);
            row.appendChild(cLogId);

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

            const cDeaths = document.createElement("td");
            const cDeathsText = document.createTextNode(loadedStats.deaths[i]);
            cDeaths.appendChild(cDeathsText);
            row.appendChild(cDeaths);

            const cDamage = document.createElement("td");
            const cDamageText = document.createTextNode(loadedStats.damage[i]);
            cDamage.appendChild(cDamageText);
            row.appendChild(cDamage);

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