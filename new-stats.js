function validateForm() {
    var missingFields = new Array();
    let errormsg = "";

    let username = document.forms["fstats"]["username"].value;
    if (username == "") {
      missingFields.push("Username");
    }

    
    let steamid = document.forms["fstats"]["steamid"].value;
    if (steamid == "") {
      missingFields.push("Steam ID");
    }

    
    let logid = document.forms["fstats"]["logid"].value;
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

    if(missingFields.length > 0)
    {
        console.log(missingFields);
        for(let i = 0; i < missingFields.length; i++)
        {
            console.log(errormsg);
            let field = missingFields[i];
            if(i == (missingFields.length-1))
            {
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
  } 