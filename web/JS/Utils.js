// --------------------------------*/
// --------- Querry params ------- */
// --------------------------------*/

function readQParamsToList(loc) {
    var qparams = [];
    //console.log(loc);
    if (loc.indexOf("?") >= 0 && loc.indexOf("=") >= 0) {
        loc = loc.substring(loc.indexOf("?") + 1);
        //console.log(loc);
        if (loc.indexOf("&") > -1) {
            var allqparams = loc.split("&");
            //console.log(allqparams);
            for (i in allqparams) {
                //keyword for qparam
                qparams.push(allqparams[i].split("="));
                //console.log(qparams);
            }
        } else {
            qparams.push(loc.split("="));
            //console.log(qparams);
        }
    } else {
        qparams = [[0]]
        //console.log(qparams);
    }
    return qparams;
}

function returnValueOf(array, slotName) {
    for (i in array) {
        if (array[i][0] == slotName)
            return array[i][1];
    }
    return null;
}

// ------------------------*/
// --------- Media ------- */
// ------------------------*/

function unBlur(nsfwobject) {
    nsfwobject.classList.remove("nsfw");
    setTimeout(function () {
        nsfwobject.closest("a").href = nsfwobject.getAttribute("realhref");
    }, 10);
}

function generateMedia(json) {
    console.log(json);
    var col2 = document.createElement("DIV");
    col2.setAttribute("class", "col-2");
    var imageholder = document.createElement("DIV");
    imageholder.setAttribute("class", "imageholder");
    var a = document.createElement("A");
    a.href = "media.html?id=" + json.mediaId;
    var img = document.createElement("IMG");
    img.src = json.mediaLocation; //TODO fix path
    img.classList.add("image");
    a.append(img);
    imageholder.appendChild(a);
    var caption = document.createElement("DIV");
    caption.setAttribute("class", "caption");
    caption.innerText = json.title;
    if (json.nsfw == true) {
        img.classList.add("nsfw");
        img.setAttribute("onclick", "unBlur(this)");
        $(img).attr("realhref", img.closest("a").href);
        img.closest("a").removeAttribute("href");
        caption.innerText = json.title + "\nNSFW\n(Click to see content)";
    }
    imageholder.appendChild(caption);
    col2.appendChild(imageholder);
    console.log(col2);
    return col2;
}


// --------------------------------------*/
// --------- Json/Array to table ------- */
// --------------------------------------*/

function jsonArrayToArray(jsonArray) {
    var a = [];
    var b = [];
    for (key in jsonArray) {
        b.push(key);
    }
    a.push(b);
    for (key in jsonArray) {
        b = [];
        if (jsonArray[key].length > 1) {
            for (value in jsonArray[key]) {
                b.push(jsonArray[key][value]);
            }
        } else {
            b.push(jsonArray[key]);
        }
        a.push(b);
    }
}

function arrayToTable(arrayToBeTable) {
    console.log(arrayToBeTable);
    var table = document.getElementById("tables");
    table.innerHTML = '';
    var thead = document.createElement("THEAD");
    var tbody = document.createElement("TBODY");
    var tr = document.createElement("TR");
    for (i = 0; i < arrayToBeTable.length; i++) {
        if (i == 0) {
            for (var header in arrayToBeTable[i]) {
                var th = document.createElement("TH");
                th.textContent = arrayToBeTable[i][header];
                tr.appendChild(th);
            }
            thead.appendChild(tr);
            table.appendChild(thead);
        } else {
            tr = document.createElement("TR");
            for (var value in arrayToBeTable[i]) {
                var th = document.createElement("TD");
                th.textContent = arrayToBeTable[i][value];
                tr.appendChild(th);
            }
            tbody.appendChild(tr);
            table.appendChild(tbody);
        }
    }
}

// --------------------------------------*/
// ----------- Signin Form  ------------ */
// --------------------------------------*/

function onSignIn(event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/signin",
        data: $(".signin-form").serialize(),
        success: function (data, textStatus, xhr) {
            console.log(data);
            document.cookie = "SessionID = " + data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// --------------------------------------*/
// ------------ Signup Form  ----------- */
// --------------------------------------*/

function onSignUp(event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/signup",
        data: $(".signup-form").serialize(),
        success: function (data, textStatus, xhr) {
            console.log(JSON.parse(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// --------------------------------------*/
// ------------ Upload Form  ----------- */
// --------------------------------------*/

function onUpload(event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/upload",
        cache: false,
        contentType: false,
        processData: false,
        data: new FormData($(event.target)[0]),
        success: function (data, textStatus, xhr) {
            console.log(JSON.parse(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// --------------------------------------*/
// ------------ Friend List  ----------- */
// --------------------------------------*/


$("#friend-list-heading").click(function () {
    if ($("#friend-list .panel-body").is(":visible")) {
        $("#friend-list .panel-body").slideUp();
    } else {
        $("#friend-list .panel-body").slideDown();
    }
});

// --------------------------------------*/
// ---------- Navigation Bar  ---------- */
// --------------------------------------*/

$(document).ready(function () {
    $("#navbar-mobile").hide();
    $(".navbar-dropdown-toggle").click(function ()
    {
        $("#navbar-mobile").slideToggle(500);
    });
});
