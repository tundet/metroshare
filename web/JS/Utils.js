// -------------------------------*/
// ------- Page Customizer ------ */
// -------------------------------*/
// 
// Customize the page for the current user:
//
// - Write username and sign-out button to the upper-right corner.
// - Fetch latest media of friends.

$(document).ready(function () {
    if (readSessionIdFromCookie()) {
        var loginName = checkIfLoggedIn();
        $(".login-area").text("");
        
        // Show username in the upper-right corner.
        var a = document.createElement("a");
        a.href = "profile.html?login=" + loginName;
        a.innerHTML = loginName;
        $(".login-area").append(a);
        
        // Show sign-out button in the upper-right corner.
        var signOutButton = document.createElement("button");
        signOutButton.innerHTML = "Sign out";
        signOutButton.setAttribute("onclick", "onSignOut(this)");
        $(".login-area").append(signOutButton);
        
        // Show media of friends instead of page description.
        $(".header-container").text("");
        var h2 = document.createElement("h2");
        h2.innerHTML = "Friends' latest posts";
        $(".header-container").append(h2);
        
        $.ajax({
            type: "GET",
            async: false,
            url: "http://localhost:8080/MetroShare/webresources/users/friends/media/" + loginName,
            success: function (data, textStatus, xhr) {
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

/**
 * Read session ID of the logged-in user from a cookie.
 *
 * @returns string|null Session ID or null if not found
 */
function readSessionIdFromCookie() {
    var cookies = [];
    if (document.cookie.indexOf("; ") > -1) {
        cookies = document.cookie.split("; ");
    } else {
        cookies.push(document.cookie);
    }
    for (i in cookies) {
        var cookie = cookies[i].split("=");
        if (cookie[0] === "SessionID") {
            return cookie[1];
        }
    }
    
    return null;
}

/**
 * Read query parameters from the current URL.
 *
 * @param loc URL to read the parameters from
 * @returns {Array} List of query parameters and their values
 */
function readQParamsToList(loc) {
    var qparams = [];
    
    if (loc.indexOf("?") >= 0 && loc.indexOf("=") >= 0) {
        loc = loc.substring(loc.indexOf("?") + 1);
        if (loc.indexOf("&") > -1) {
            var allqparams = loc.split("&");
            for (i in allqparams) {
                qparams.push(allqparams[i].split("="));
            }
        } else {
            qparams.push(loc.split("="));
        }
    } else {
        qparams = [[0]];
    }
    
    return qparams;
}

/**
 * Get the value of the given query parameter key.
 *
 * @param array Array generated by readQParamsToList()
 * @param slotName Name of the query parameter to get
 */
function returnValueOf(array, slotName) {
    for (i in array) {
        if (array[i][0] === slotName)
            return array[i][1];
    }
    return null;
}

// ------------------------*/
// --------- Media ------- */
// ------------------------*/

/**
 * Unblur an element.
 *
 * @param nsfwobject Element to unblur
 */
function unBlur(nsfwobject) {
    nsfwobject.classList.remove("nsfw");
    setTimeout(function () {
        nsfwobject.closest("a").href = nsfwobject.getAttribute("realhref");
    }, 10);
}

/**
 * Generate media on the page from JSON.
 *
 * @param json string JSON containing media information
 */
function generateMedia(json) {
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

    return col2;
}


/**
 * Convert the given JSON object into an array.
 *
 * @param jsonArray string JSON object
 */
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

/**
 * Convert the given array into an HTML table.
 *
 * @param arrayToBeTable Array Array to convert
 */
function arrayToTable(arrayToBeTable) {
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
// ----------- Sign-in Form  ----------- */
// --------------------------------------*/

/**
 * Sign the user in.
 *
 * @param event Event onsubmit event
 */
function onSignIn(event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/signin",
        data: $(".signin-form").serialize(),
        success: function (data, textStatus, xhr) {
            document.cookie = "SessionID = " + data;
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// --------------------------------------*/
// ------------ Sign-up Form  ----------- */
// --------------------------------------*/

/**
 * Create a new user.
 *
 * @param event Event onsubmit event
 */
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

/**
 * Upload a new medium.
 *
 * @param event Event onsubmit event
 */
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

/**
 * Show or hide friend list based on its current visibility.
 */
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

/**
 * Show or hide mobile navigation bar when the menu button has been pressed.
 */
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

/**
 * Create a new comment.
 *
 * @param event Event onsubmit event
 */
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
}


// --------------------------------------*/
// ----- Get User From Session ID  ----- */
// --------------------------------------*/

/**
 * Check if the user is logged in.
 *
 * @return string JSON result message or null on failure
 */
function checkIfLoggedIn() {
    var json;

    $.ajax({
        type: "GET",
        async: false,
        url: "http://localhost:8080/MetroShare/webresources/users/sessionid/" + readSessionIdFromCookie(),
        success: function (data, textStatus, xhr) {
            json = data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });

    return json;
}

// --------------------------------*/
// ------- Sign-out Button ------- */
// --------------------------------*/

/**
 * Sign the user out.
 *
 * @param event Event onsubmit event
 */
function onSignOut(event) {
    console.log("Logging out");
    document.cookie = "SessionID=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
    window.location = "index.html";
}

// -----------------------------------------------*/
// ------ Like, Unlike And Dislike Buttons --s---- */
// -----------------------------------------------*/

/**
 * Like a medium.
 *
 * @param event Event onsubmit event
 */
function setLikeFunction(event) {
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
}


/**
 * Dislike a medium.
 *
 * @param event Event onsubmit event
 */
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
}

/**
 * Unlike a medium.
 *
 * @param event Event onsubmit event
 */
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
}
