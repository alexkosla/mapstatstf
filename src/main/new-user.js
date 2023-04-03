const url = "http://localhost:8080/submit-user";

function validateForm() {
    var missingFields = new Array();
    let errormsg = "";
    let hasMiscError = false;

    // for every field in the form, get its value and check if it's empty
    // if it's empty, then add it to a list of missing fields
    // debugger
    let username = document.forms["addUserForm"]["username"].value;
    if (username == "") {
      missingFields.push("Username");
    }
    
    let steam64Id = document.forms["addUserForm"]["steam64Id"].value;
    if (steam64Id == "") 
    {
      missingFields.push("Steam ID");
    }
    if(/\D/.test(steam64Id))
    {
        console.log(typeof steam64Id)
        alert("steam64Id must be a number");
        hasMiscError = true;
    }

    let steam3Id = document.forms["addUserForm"]["steam3Id"].value;
    if (steam3Id == "") {
        // TO-DO: ADD MORE VALIDATION THAT IT'S THE RIGHT FORMAT
      missingFields.push("Steam ID");
    }

    let preferredClass = document.forms["addUserForm"]["preferredClass"].value;
    if (preferredClass == "") {
        // TO-DO: ADD MORE VALIDATION THAT IT'S THE RIGHT FORMAT
      missingFields.push("Steam ID");
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
    else if(hasMiscError)
    {
        console.log("incorrect formatting for input, stats not submitted");
    }
    else{
        // if there are no missing fields or format errors, save the entered data and then send an alert
        saveStats();
        alert("Stats saved!");
    }
  } 

function saveStats(){

    // get all of the stats from the form
    var toSave = document.forms["addUserForm"];

    // try to load any saved states and parse them as a dict
    var loadedStats = JSON.parse(localStorage.getItem('users'));
    let toSaveDict = {};

    // format the output of the isAdmin checkbox to be a capitalized bool
    if(toSave.isAdmin.checked)
        toSave.isAdmin.value = "true";
    else
        toSave.isAdmin.value = "false";

    toSaveDict = {
        username: toSave.username.value,
        steam64Id: toSave.steam64Id.value,
        steam3Id: toSave.steam3Id.value,
        preferredClass: toSave.preferredClass.value,
        isAdmin: toSave.isAdmin.value
    }

    // save the dict you've added the form stats to to local storage
    request = JSON.stringify(toSaveDict);

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

//    return JSON.stringify(toSaveDict);
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
            const csteam3Id = document.createElement("td");
            const csteam3IdText = document.createTextNode(loadedStats.steam3Id[i]);
            csteam3Id.appendChild(csteam3IdText);
            row.appendChild(csteam3Id);

            const cIsAdmin = document.createElement("td");
            const cIsAdminText = document.createTextNode(loadedStats.isAdmin[i]);
            cIsAdmin.appendChild(cIsAdminText);
            row.appendChild(cIsAdmin);

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
    const table = document.getElementById('user-stats');
    var loadedStats = JSON.parse(localStorage.getItem('users'));

    displaySavedRows(table, loadedStats);
}