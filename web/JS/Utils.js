// -------------------------------*/
// --------- check user! -------- */
// -------------------------------*/
$(document).ready(function () {
    if (readSessionIdFromCookie()) {
// user name instead of login area
        var LOGINNAME = checkIfLoggedIn().username;
        $(".login-area").text("");
        var a = document.createElement("a");
        a.href = "profile.html?login=" + LOGINNAME;
        a.innerHTML = LOGINNAME;
        $(".login-area").append(a);
        var signOutButton = document.createElement("button");
        signOutButton.innerHTML = "Sign out";
        signOutButton.setAttribute("onclick", "onSignOut(this)");
        $(".login-area").append(signOutButton);
        // friend pictures instead of page description
        $(".header-container").text("");
        var h2 = document.createElement("h2");
        h2.innerHTML = "Friends' latest posts";
        $(".header-container").append(h2);
        $.ajax({
            type: "GET",
            async: false,
            url: "http://localhost:8080/MetroShare/webresources/users/friends/media/" + LOGINNAME,
            success: function (data, textStatus, xhr) {
                //console.log(JSON.parse(data));
                var json = JSON.parse(data);
                for (i in json) {
                    $(".header-container").append(generateMedia(json[i]));
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Error: " + errorThrown);
            }
        });
    }
});
// ----------------------------------*/
// --------- Cookie cookker! ------- */
// ----------------------------------*/

function readSessionIdFromCookie() {
    //console.log(document.cookie);
    var cookies = [];
    if (document.cookie.indexOf("; ") > -1) {
        cookies = document.cookie.split("; ");
    } else {
        cookies.push(document.cookie);
    }
    for (i in cookies) {
        var cookie = cookies[i].split("=")
        if (cookie[0] == "SessionID") {
            //console.log(cookie[1]);
            return cookie[1];
        }
    }
    return null;
}

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
        console.log(nsfwobject.getAttribute("realhref"));
        nsfwobject.closest("a").href = nsfwobject.getAttribute("realhref");
    }, 10);
}

function generateMedia(json) {
//console.log(json);
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
    //console.log(col2);
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
            //console.log(data);
            document.cookie = "SessionID = " + data;
            location.reload();
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
// --------------------------------------*/
// ----- Make comment to media  -------- */
// --------------------------------------*/

function makeCommentFunction(event) {
    event.preventDefault();
    $.ajax({
        type: "POST",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/media/comment/",
        data: $("#make-comment-form").serialize(),
        success: function (data, textStatus, xhr) {
            if (data) {
                location.reload();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
};


// --------------------------------------*/
// ----- Get User From Session ID  ----- */
// --------------------------------------*/

function checkIfLoggedIn() {
    var json;
    $.ajax({
        type: "GET",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/users/sessionid/" + readSessionIdFromCookie(),
        success: function (data, textStatus, xhr) {
            //console.log(JSON.parse(data));
            json = JSON.parse(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
    //console.log(json);
    return json;
}

// --------------------------------*/
// ------- Sign Out Button ------- */
// --------------------------------*/

function onSignOut(event) {
    console.log("Logging out");
    document.cookie = "SessionID=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
    window.location = "index.html";
};

// -----------------------------------------------*/
// ------- Like/removelike/dislike Button ------- */
// -----------------------------------------------*/

function setLikeFunction(event) {
    console.log("setLikeFunction start");
    event.preventDefault();
    $.ajax({
        type: "POST",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/media/opinion/like",
        data: $("#like-media-form").serialize(),
        success: function (data, textStatus, xhr) {
            if (data) {
                location.reload();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
};

function setDislikeFunction(event) {
    console.log("setLikeFunction start");
    event.preventDefault();
    $.ajax({
        type: "POST",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/media/opinion/dislike",
        data: $("#dislike-media-form").serialize(),
        success: function (data, textStatus, xhr) {
            if (data) {
                location.reload();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
};

function removeLikeFunction(event) {
    console.log("setLikeFunction start");
    event.preventDefault();
    $.ajax({
        type: "POST",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/media/opinion/remove",
        data: $("#removelike-media-form").serialize(),
        success: function (data, textStatus, xhr) {
            if (data) {
                location.reload();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
};