function validateForm() {
    var missingFields = new Array();
    let errormsg = "";

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

    console.log(missingFields);
    if(missingFields.length > 0)
    {
        console.log(missingFields);
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

function loadStats(){
    // get the stats table by its ID to reference later
    const table = document.getElementById('all-stats');
    var loadedStats = JSON.parse(localStorage.getItem('stats'));
    console.log(loadedStats);

    /* TO-DO
    *  Load the stats in this function and incrementally add them in as rows
    *  https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Traversing_an_HTML_table_with_JavaScript_and_DOM_Interfaces
    *  Be able to trigger loadStats by a button
    *  Add comments
    *  FIX SIDEBAR FORMATTING
    */

    // for(let i = 0; i < )
}