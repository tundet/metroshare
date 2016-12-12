// -------------------------------*/
// ------- Page Customizer ------ */
// -------------------------------*/
// 
// Customize the page for the current user:
//
// - Write username and sign-out button to the upper-right corner.
// - Fetch latest media of friends.

$(document).ready(function () {
//Stuff to do on everysite:
    navbarhide();
    var cookiesessionid = readSessionIdFromCookie();

    //Stuff to do in spesific site when not logged in
    var loc = document.location.toString();
    loc = loc.substring(loc.indexOf("MetroShare/") + 11);
    if (loc.startsWith("index.html") || loc === "") {
        getrandompics(12);
    } else if (loc.startsWith("media.html")) {
        if ((cookiesessionid === "undefined" || cookiesessionid === null)) {
            window.location = "signup.html";
        }
    } else if (loc.startsWith("profile.html")) {
//        console.log("yay in profile");
        if ((cookiesessionid === "undefined" || cookiesessionid === null)) {
            window.location = "signup.html";
        }
    } else if (loc.startsWith("singup.html")) {
//        console.log("yay in singup");
    } else if (loc.startsWith("statistics.html")) {
//        console.log("yay in statistics");
    } else if (loc.startsWith("upload.html")) {
//        console.log("yay in upload");
        if ((cookiesessionid === "undefined" || cookiesessionid === null)) {
            window.location = "signup.html";
        }
    } else if (loc.startsWith("about.html")) {
//        console.log("yay in about");
    } else if (loc.startsWith("admin.html")) {
//        console.log("yay in admin");
        if ((cookiesessionid === "undefined" || cookiesessionid === null)) {
            window.location = "signup.html";
        }
    } else if (loc.startsWith("browse.html")) {
        if ((cookiesessionid === "undefined" || cookiesessionid === null)) {
            window.location = "signup.html";
        } else {
            loadNlatestMedia(6);
            getrandompics(12);
        }
    }

    if (cookiesessionid !== "undefined" && cookiesessionid !== null) {
        console.log("cookie session id: " + cookiesessionid);
        var loginName = null;
        checkIfLoggedIn().then(function (value) {
            loginName = value;
        }).then(function () {
            if (loginName !== "undefined" && loginName !== null) {
                console.log("user found as: " + loginName);
                if (loginName !== null) {
                    loggedincheck(loginName);
                }

//Stuff to do in spesific site
                var loc = document.location.toString();
                loc = loc.substring(loc.indexOf("MetroShare/") + 11);
                if (loc.startsWith("index.html") || loc === "") {
                    getfriendpics();
                } else if (loc.startsWith("media.html")) {
                    var qparams = readQParamsToList(loc);
                    var qparam = returnValueOf(qparams, "id");
                    loadmedia(qparam);
                } else if (loc.startsWith("profile.html")) {
                    var qparams = readQParamsToList(loc);
                    var qparam = returnValueOf(qparams, "login");
                    loadProfile(qparam);
                } else if (loc.startsWith("singup.html")) {
                    console.log("yay in singup");
                } else if (loc.startsWith("statistics.html")) {
                    console.log("yay in statistics");
                } else if (loc.startsWith("upload.html")) {
                    console.log("yay in upload");
                } else if (loc.startsWith("about.html")) {
                    console.log("yay in about");
                } else if (loc.startsWith("admin.html")) {
                    console.log("yay in admin");
                    getAdminTools();
                } else if (loc.startsWith("browse.html")) {
                    console.log("yay in browse");
                    var qparams = readQParamsToList(loc);
                    var qparam = returnValueOf(qparams, "search");
                    if (qparam !== null) {
                        loadBrowse();
                    }
                }
            }
        });
    }
});
// -------------------------------*/
// ------- Tools ---------------- */
// -------------------------------*/

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
    for (var i in cookies) {
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
            for (var i in allqparams) {
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
    for (var i in array) {
        if (array[i][0] === slotName)
            return array[i][1];
    }
    return null;
}

// -------------------------------*/
// ------- All page loads ------- */
// -------------------------------*/

function loggedincheck(loginName) {
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
    return fetch('http://localhost:8080/MetroShare/webresources/users/get/sessionid/', {
        method: 'GET',
        async: false,
        credentials: 'same-origin'
    }).then(function (response) {
        return response.json();
    }).then(function (j) {
        return j.login;
    });
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
            jsondata = JSON.parse(data);
            document.cookie = "SessionID = " + jsondata.sessionid;
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
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

// -------------------------------*/
// ------ index.html loads ------ */
// -------------------------------*/

function getrandompics(num) {
    var headin = document.querySelector("#random-media-headin");
    if (headin) {
        headin.innerHTML = num + " random media of the moment!";
    }
    var media = document.querySelector("#random-media");
    fetch("http://localhost:8080/MetroShare/webresources/media/get/" + num, {
        method: 'GET'
    }).then(function (response) {
        return response.json();
    }).then(function (j) {
        for (var i in j) {
            media.append(generateMedia(j[i]));
        }
    }).catch(function () {
        media.inneHTML = "Error getting random pics!";
    });
}

function getfriendpics() {
    // Show media of friends instead of page description.
    var headerContainer = $(".header-container");
    headerContainer.text("");
    var h2 = document.createElement("h2");
    h2.innerHTML = "Friends' latest posts";
    headerContainer.append(h2);
    fetch("http://localhost:8080/MetroShare/webresources/users/friends/media/", {
        method: 'GET',
        async: false,
        credentials: 'same-origin'
    }).then(function (response) {
        return response.json();
    }).then(function (j) {
        for (var i in j)
            headerContainer.append(generateMedia(j[i]));
    });
}

// ------------------------------*/
// --------- Media tools ------- */
// ------------------------------*/

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
function generateMedia(json, num) {
    if (typeof num === "undefined") {
        num = 2;
    }
    var col2 = document.createElement("DIV");
    col2.setAttribute("class", "col-" + num);
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
    if (json.nsfw === true) {
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
    var mediaid = document.querySelector('input[name="mediaid"]').value;
    var comment = document.querySelector('textarea[name="comment"]').value;
    var data = "mediaid=" + mediaid + "&comment=" + comment;
    console.log(data);
    fetch("http://localhost:8080/MetroShare/webresources/media/comment/", {
        method: 'POST',
        async: false,
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
        },
        body: data
    }).then(function (response) {
        return response.json();
    }).then(function (j) {
        location.reload();
    });
}

function loadNlatestMedia(num) {
    console.log("loading latests!");
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/MetroShare/webresources/media/latest/" + num,
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            var headin = jsondata.length + " Latest media";
            document.querySelector("#latest-media-headin").innerHTML = headin;
            // Adding latest media
            for (i = 0; i < jsondata.length; i++) {
                document.querySelector("#latest-media").append(generateMedia(jsondata[i])); // where to
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}


// -----------------------------------------------*/
// ------ Like, Unlike And Dislike Buttons ------ */
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
    //console.log("setLikeFunction start");
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


// -------------------------------*/
// ------ media.html loads ------ */
// -------------------------------*/

function loadmedia(qparam) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/MetroShare/webresources/media/" + qparam,
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            var a = [];
            var b = [];
            for (var data in jsondata[0]) {
                b.push(data);
            }
            a.push(b);
            for (var json in jsondata) {
                var b = [];
                for (var data in jsondata[json]) {
                    b.push(jsondata[json][data]);
                }
                a.push(b);
            }

            console.log(a);
            //setup likes
            $("input[name='mediaid']").val(a[1][0]);
            $("input[name='hasopinion']").val(a[1][7].likeid);
            if (a[1][7].userhasopinion) {
                if (a[1][7].useropinion) {
                    //liked
                    $("button[name='like'").attr("disabled", true);
                    $("button[name='remove'").removeAttr("disabled");
                    $("button[name='dislike'").removeAttr("disabled");
                } else {
                    //disliked
                    $("button[name='dislike'").attr("disabled", true);
                    $("button[name='remove'").removeAttr("disabled");
                    $("button[name='like'").removeAttr("disabled");
                }
            } else {
                //no opinion
                $("button[name='remove'").attr("disabled", true);
                $("button[name='like'").removeAttr("disabled");
                $("button[name='dislike'").removeAttr("disabled");
            }

            $("button[name='like'").prepend("<span>" + a[1][7].likes + "</span> ");
            $("button[name='dislike'").prepend("<span>" + a[1][7].dislikes + "</span> ");
            $(".windows8").remove();
            // make media title
            var title = $(".img-title");
            title.text(a[1][3]);
            var uploaderdiv = $(".img-uploader");
            var aUploader = document.createElement("a");
            aUploader.href = "profile.html?login=" + a[1][1];
            aUploader.style.textDecoration = "none";
            var h5 = document.createElement("h5");
            h5.append("By " + a[1][1]);
            aUploader.append(h5);
            uploaderdiv.append(aUploader);
            // make media figure
            var figure = $(".show-media-figure");
            figure.text("");
            var img = document.createElement("img");
            img.alt = a[1][3];
            // TODO real image path
            //console.log("/images" + a[1][2]);
            //img.src = "http://nemanjakovacevic.net/wp-content/uploads/2013/07/placeholder.png";
            img.src = a[1][2];
            img.style = "border: 5px solid #fff;";
            figure.append(img);
            var figcaption = document.createElement("figcaption");
            //figcaption.innerHTML = a[1][3];
            for (var i in a[1][6]) {
                var tag = document.createElement("a");
                tag.href = "browse.html?tag=1&search=" + a[1][6][i].tag;
                tag.innerHTML = "#" + a[1][6][i].tag;
                figcaption.append(tag);
            }
            figure.append(figcaption);
            var comments = $(".comments");
            comments.text("");
            // comment make area
            var cookiesessionid = readSessionIdFromCookie();
            if (cookiesessionid !== "undefined" && cookiesessionid !== null) {
                var makeCommentDiv = document.createElement("div");
                var commentForm = document.createElement("form");
                commentForm.addEventListener("submit", makeCommentFunction);
                commentForm.id = "make-comment-form";
                commentForm.method = "POST";
                var media = document.createElement("input");
                media.type = "hidden";
                media.value = qparam;
                media.name = "mediaid";
                commentForm.append(media);
                var commentText = document.createElement("textarea");
                commentText.rows = "2";
                commentText.name = "comment";
                commentText.placeholder = "Type comment here!";
                commentText.maxlength = "255";
                commentText.autofocus = "autofocus";
                commentText.required = "required";
                commentText.wrap = "soft";
                commentForm.append(commentText);
                var summaryDiv = document.createElement("div");
                summaryDiv.classList.add("comment-summary");
                var aRules = document.createElement("a");
                aRules.href = "about.html";
                aRules.innerHTML = "Remember our community rules";
                summaryDiv.append(aRules);
                var sendButton = document.createElement("button");
                sendButton.type = "submit";
                sendButton.classList.add("redbtn");
                sendButton.classList.add("redbtn-search");
                sendButton.innerHTML = "<i class=\"fa fa-commenting-o\"></i>";
                summaryDiv.append(sendButton);
                commentForm.append(summaryDiv);
                makeCommentDiv.append(commentForm);
                comments.append(makeCommentDiv);
            }

            console.log(a[1][5].length);
            if (a[1][5].length > 0) {
                for (var i in a[1][5]) {
                    console.log(a[1][5][i].date);
                    var contdiv = document.createElement("div");
                    contdiv.classList.add("comment");
                    var leftColumn = document.createElement("div");
                    leftColumn.classList.add("col-3");
                    contdiv.append(leftColumn);
                    var rightColumn = document.createElement("div");
                    rightColumn.classList.add("col-9");
                    contdiv.append(rightColumn);
                    var alink = document.createElement("a");
                    alink.href = "profile.html?login=" + a[1][5][i].userName;
                    alink.style.textDecoration = "none";
                    var unameh2 = document.createElement("h2");
                    unameh2.classList.add("comment-sender-name");
                    unameh2.innerHTML = a[1][5][i].userName;
                    alink.append(unameh2);
                    leftColumn.append(alink);
                    var datespan = document.createElement("span");
                    datespan.classList.add("comment-date");
                    datespan.innerHTML = a[1][5][i].date;
                    leftColumn.append(datespan);
                    var messagep = document.createElement("p");
                    messagep.classList.add("comment-message");
                    messagep.innerHTML = a[1][5][i].message;
                    rightColumn.append(messagep);
                    comments.append(contdiv);
                }
            } else {
                var h2 = document.createElement("h2");
                h2.innerHTML = "No comments yet!";
                comments.append(h2);
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// -------------------------------*/
// ------ profile.html loads ------ */
// -------------------------------*/

function loadProfile(qparam) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/MetroShare/webresources/users/" + qparam,
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            //console.log(jsondata[0]);

            // Example how to insert user name to page after load 
            var h3 = document.createElement("H3");
            h3.innerHTML = jsondata[0].login;
            h3.style.textAlign = "center";
            var loginArea = $('#login');
            loginArea.append(h3);
            // Adding user's photos
            for (i = 0; i < jsondata[0].media.length; i++) {
                document.querySelector("#media").appendChild(generateMedia(jsondata[0].media[i]));
            }

            // Friends
            var frul = document.createElement("UL");
            for (i = 0; i < jsondata[0].friends.length; i++) {
                var frli = document.createElement("LI");
                frli.innerText = jsondata[0].friends[i].friend;
                frul.appendChild(frli);
            }
            document.querySelector("#friends").appendChild(frul);
            // Example how to modify existing data
            console.log($("#loginname").text()); // first it is johndoe
            $("#loginname").text(jsondata[0].login);
            console.log($("#loginname").text()); // then changed to json value login 

            //arrayToTable(jsondata[0].friends[0]);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

// -------------------------------*/
// ------ browse.html loads ------ */
// -------------------------------*/

function loadBrowse() {
    var loc = document.location.toString();
    var qparamsl = readQParamsToList(loc);
    console.log(qparamsl);
    var tag = returnValueOf(qparamsl, "tag");
    var user = returnValueOf(qparamsl, "user");
    var title = returnValueOf(qparamsl, "title");
    var search = returnValueOf(qparamsl, "search");
    if (!tag && !title && !user) {
        tag = true;
        user = true;
        title = true;
    }

    if (title === null) {
        $("#titles").toggleClass("checked");
        $("#titles").removeAttr("checked");
    }
    if (!user) {
        $("#users").toggleClass("checked");
        $("#users").removeAttr("checked");
    }
    if (!tag) {
        $("#tags").toggleClass("checked");
        $("#tags").removeAttr("checked");
    }

    if (search !== null) {
        console.log(search);
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/MetroShare/webresources/media/search/" + search,
            success: function (data, textStatus, xhr) {
                //alert(data);
                var jsondata = JSON.parse(data);
                var searchResults = document.querySelector("#latest-media-container");
                searchResults.innerHTML = "";
                var mediacontainer = document.createElement("div");
                mediacontainer.setAttribute("class", "container-fluid");
                if (jsondata[0].length < 1 && jsondata[1].length < 1 && jsondata[2].length < 1) {
                    var col12 = document.createElement("div");
                    col12.setAttribute("class", "col-12");
                    var h1 = document.createElement("h1");
                    h1.innerHTML = "No media found!";
                    col12.append(h1);
                    mediacontainer.append(col12);
                }

                if (jsondata[0].length > 0 && title) {
                    var col12 = document.createElement("div");
                    col12.setAttribute("class", "col-12");
                    var h1 = document.createElement("h1");
                    h1.innerHTML = jsondata[0].length + " found by media title";
                    col12.append(h1);
                    mediacontainer.append(col12);
                    // Adding Media found by title
                    for (i = 0; i < jsondata[0].length; i++) {
                        mediacontainer.append(generateMedia(jsondata[0][i]));
                    }
                }

                if (jsondata[1].length > 0 && user) {
                    console.log("by uploader");
                    var col12 = document.createElement("div");
                    col12.setAttribute("class", "col-12");
                    var h1 = document.createElement("h1");
                    h1.innerHTML = jsondata[1].length + " found by uploaders user name";
                    col12.append(h1);
                    mediacontainer.append(col12);
                    // Adding Media found by uploader login name
                    for (i = 0; i < jsondata[1].length; i++) {
                        mediacontainer.append(generateMedia(jsondata[1][i]));
                    }
                }

                if (jsondata[2].length > 0 && tag) {
                    var col12 = document.createElement("div");
                    col12.setAttribute("class", "col-12");
                    var h1 = document.createElement("h1");
                    h1.innerHTML = jsondata[2].length + " found by tag";
                    col12.append(h1);
                    mediacontainer.append(col12);
                    // Adding Media found by tag
                    for (i = 0; i < jsondata[2].length; i++) {
                        mediacontainer.append(generateMedia(jsondata[2][i]));
                    }
                }
                searchResults.append(mediacontainer);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Error: " + errorThrown);
            }
        });
    }
}

// -------------------------------*/
// ------ browse tools ------ */
// -------------------------------*/
//give class "checked" to checkbox when clicking on its span
$('span').click(function (event) {
    $(this).parents("label").find("input").toggleClass("checked");
});


function searchMedia(event) {
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/media/search/",
        data: $("#search-media").serialize(),
        success: function (data, textStatus, xhr) {
            //alert(data);
            var jsondata = JSON.parse(data);
            //alert(jsondata[0]);
            //alert(jsondata[1]);
            //alert(jsondata[2]);

            var searchResults = document.querySelector(".search-results");
            searchResults.innerHTML = "";
            if (jsondata[0].length < 1 && jsondata[1].length < 1 && jsondata[2].length < 1) {
                var col12 = document.createElement("div");
                col12.setAttribute("class", "col-12");
                var h1 = document.createElement("h1");
                h1.innerHTML = "No media found!";
                col12.append(h1);
                searchResults.append(col12);
            }

            if (jsondata[0].length > 0 && $("#titles").hasClass("checked")) {
                var col12 = document.createElement("div");
                col12.setAttribute("class", "col-12");
                var h1 = document.createElement("h1");
                h1.innerHTML = jsondata[0].length + " found by media title";
                col12.append(h1);
                searchResults.append(col12);
                // Adding Media found by title
                for (i = 0; i < jsondata[0].length; i++) {
                    searchResults.append(generateMedia(jsondata[0][i]));
                }
            }

            if (jsondata[1].length > 0 && $("#users").hasClass("checked")) {
                console.log("by uploader");
                var col12 = document.createElement("div");
                col12.setAttribute("class", "col-12");
                var h1 = document.createElement("h1");
                h1.innerHTML = jsondata[1].length + " found by uploaders user name";
                col12.append(h1);
                searchResults.append(col12);
                // Adding Media found by uploader login name
                for (i = 0; i < jsondata[1].length; i++) {
                    searchResults.append(generateMedia(jsondata[1][i]));
                }
            }

            if (jsondata[2].length > 0 && $("#tags").hasClass("checked")) {
                var col12 = document.createElement("div");
                col12.setAttribute("class", "col-12");
                var h1 = document.createElement("h1");
                h1.innerHTML = jsondata[2].length + " found by tag";
                col12.append(h1);
                searchResults.append(col12);
                // Adding Media found by tag
                for (i = 0; i < jsondata[2].length; i++) {
                    searchResults.append(generateMedia(jsondata[2][i]));
                }
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
    $("#latest-media-headin").remove();
}

function getAdminTools() {
    fetch("http://localhost:8080/MetroShare/webresources/admin/gettools/", {
        method: 'GET',
        async: false,
        credentials: 'same-origin'
    }).then(function (response) {
        return response.json();
    }).then(function (j) {
        if (j.tools) {
            for (var page in j.pages) {
                var form = document.createElement();
                console.log(page);
                console.log(j.pages[page])
            }

            $("#admin-tools").append(j);
        }
    });
}




// --------------------------------------*/
// ---------- Navigation Bar  ---------- */
// --------------------------------------*/

/**
 * Show or hide mobile navigation bar when the menu button has been pressed.
 */
function navbarhide() {
    $("#navbar-mobile").hide();
    $(".navbar-dropdown-toggle").click(function ()
    {
        $("#navbar-mobile").slideToggle(500);
    });
}
;

/**
 * Convert the given JSON object into an array.
 *
 * @param jsonArray string JSON object
 */
function jsonArrayToArray(jsonArray) {
    var a = [];
    var b = [];
    // First line titles
    for (var key in jsonArray[0]) {
        b.push(key);
    }
    a.push(b);
    for (var key in jsonArray) {
        b = [];
        if (typeof jsonArray[key] === "object") {
            console.log("2nd stage found");
            for (var value in jsonArray[key]) {
                if (typeof jsonArray[key][value] === "object") {
                    console.log("3nd stage found");
                    var c = [];
                    for (var valueb in jsonArray[key][value]) {
                        console.log(jsonArray[key][value][valueb]);
                        c.push(jsonArray[key][value][valueb]);
                    }
                    b.push(c);
                } else {
                    b.push(jsonArray[key][value]);
                }
            }
        } else {
            b.push(jsonArray[key]);
        }
        a.push(b);
    }
    console.log("jsonArrayToArray2 is returning: ");
    console.log(a);
    return a;
}

/**
 * Convert the given array into an HTML table.
 *
 * @param arrayToBeTable Array Array to convert
 */
function arrayToTable(arrayToBeTable) {
    var table = document.getElementById("tables");
    table.innerHTML = '';
    table.style.display = "table";
    var thead = document.createElement("div");
    thead.style.display = "table-header-group";
    var tr = document.createElement("div");
    tr.style.display = "table-row";
    for (i = 0; i < arrayToBeTable.length; i++) {
        if (i === 0) {
            for (var header in arrayToBeTable[i]) {
                var th = document.createElement("th");
                th.textContent = arrayToBeTable[i][header];
                var longest = 1;
                for (x in arrayToBeTable) {
                    if (typeof arrayToBeTable[x][header] === "object") {
                        longest = Math.max(longest, arrayToBeTable[x][header].length);
                        console.log(longest);
                    }
                }
                th.colSpan = longest;
                tr.appendChild(th);
            }
            var saveTh = document.createElement("th");
            saveTh.textContent = "Save";
            tr.appendChild(saveTh);
            var deleteTh = document.createElement("th");
            deleteTh.textContent = "Remove";
            tr.appendChild(deleteTh);
            thead.appendChild(tr);
            table.appendChild(thead);
        } else {

            tr = document.createElement("form");
            tr.style.display = "table-row";
            for (var value in arrayToBeTable[i]) {
                if (arrayToBeTable[0][value] === "tags") {
                    var longest = 1;
                    for (var x in arrayToBeTable) {
                        if (typeof arrayToBeTable[x][header] === "object") {
                            longest = Math.max(longest, arrayToBeTable[x][header].length);
                            console.log(longest);
                        }
                    }
                    if (arrayToBeTable[i][value].length > 0) {
                        for (var x in arrayToBeTable[i][value]) {
                            var td = document.createElement("TD");
                            var tagCBInput = document.createElement("input");
                            tagCBInput.type = "checkbox";
                            tagCBInput.checked = true;
                            tagCBInput.name = "tags";
                            tagCBInput.value = arrayToBeTable[i][value][x].tagid;
                            td.append(tagCBInput);
                            console.log(arrayToBeTable[i][value][x]);
                            var b = document.createElement("b");
                            b.innerHTML = arrayToBeTable[i][value][x].tagid;
                            td.append(b);
                            var a = document.createElement("a");
                            a.innerHTML = arrayToBeTable[i][value][x].tag;
                            a.href = "browse.html?tag=1&search=" + arrayToBeTable[i][value][x].tag;
                            td.append(a);
                            tr.appendChild(td);
                            longest--;
                        }
                        if (longest > 0) {
                            var tdfiller = document.createElement("td");
                            tdfiller.innerHTML = " ";
                            tdfiller.colSpan = longest;
                            tr.append(tdfiller);
                        }
                    } else {
                        var tdfiller = document.createElement("td");
                        tdfiller.innerHTML = "No tags";
                        tdfiller.colSpan = longest;
                        tr.append(tdfiller);
                    }
                } else {
                    var td = document.createElement("TD");
                    if (arrayToBeTable[0][value] === "nsfw") {
                        var nsfwSelect = document.createElement("select");
                        nsfwSelect.name = "nsfw";
                        var oFalse = document.createElement("option");
                        oFalse.text = "False";
                        nsfwSelect.add(oFalse);
                        var oTrue = document.createElement("option");
                        oTrue.text = "True";
                        nsfwSelect.add(oTrue);
                        console.log(arrayToBeTable[i][value]);
                        if (arrayToBeTable[i][value]) {
                            nsfwSelect.selectedIndex = "1";
                        } else {
                            nsfwSelect.selectedIndex = "0";
                        }
                        td.append(nsfwSelect);
                    } else if (arrayToBeTable[0][value] === "Login") {
                        var a = document.createElement("a");
                        a.href = "profile.html?login=" + arrayToBeTable[i][value];
                        var b = document.createElement("b");
                        b.innerHTML = arrayToBeTable[i][value];
                        a.append(b);
                        td.append(a);
                    } else if (arrayToBeTable[0][value] === "ID") {
                        var idInput = document.createElement("input");
                        idInput.type = "hidden";
                        idInput.name = "id";
                        idInput.value = arrayToBeTable[i][value];
                        td.append(idInput);
                        var b = document.createElement("b");
                        b.innerHTML = arrayToBeTable[i][value];
                        td.append(b);
                    } else if (arrayToBeTable[0][value] === "title") {
                        var titleInput = document.createElement("input");
                        titleInput.type = "text";
                        titleInput.name = "title";
                        titleInput.value = arrayToBeTable[i][value];
                        td.append(titleInput);
                    } else if (arrayToBeTable[0][value] === "medialocation") {
                        var a = document.createElement("a");
                        a.href = "media.html?id=" + arrayToBeTable[i][0];
                        var img = document.createElement("img");
                        img.src = arrayToBeTable[i][value];
                        img.classList.add("col-12");
                        a.append(img);
                        td.width = "200px";
                        td.append(a);
                    } else {
                        td.textContent = arrayToBeTable[i][value];
                    }
                    tr.appendChild(td);
                }
            }
            var savetd = document.createElement("td");
            var saveButton = document.createElement("button");
            saveButton.textContent = "Save";
            saveButton.type = "submit";
            savetd.append(saveButton);
            tr.append(savetd);
            var removetd = document.createElement("td");
            var removeForm = document.createElement("form");
            var removeButton = document.createElement("button");
            removeButton.textContent = "Remove";
            removeButton.type = "submit";
            removeForm.append(removeButton);
            removetd.append(removeForm);
            tr.append(removetd);
            table.appendChild(tr);
        }
    }
}

/**
 * Convert the given array into an HTML table.
 *
 * @param arrayToBeTable Array Array to convert
 */
function adminMediaToTable(arrayToBeTable) {
    var tables = document.getElementById("tables");
    tables.innerHTML = '';
    tables.classList.add("container-fluid");
    for (var i = 1; i < arrayToBeTable.length; i++) {
        var wrap = document.createElement("div");
        wrap.style.display = "flex";
        wrap.style.alignItems = "stretch";
        wrap.style.marginBottom = "10px";
        wrap.style.background = "rgb(48, 63, 159)";
        wrap.style.padding = "5px";
        wrap.style.color = "white";
        wrap.classList.add("container");
        var imgtd = document.createElement("td");
        imgtd.width = "400px";
        imgtd.style.border = "5px solid white";
        var a = document.createElement("a");
        a.href = "media.html?id=" + arrayToBeTable[i][0];
        var img = document.createElement("img");
        img.src = arrayToBeTable[i][2];
        img.classList.add("col-12");
        img.style.padding = "0px";
        a.append(img);
        imgtd.append(a);
        wrap.append(imgtd);
        var tabletd = document.createElement("td");
        tabletd.classList.add("container-fluid");
        tabletd.style.padding = "5px";
        var table = document.createElement("form");
        table.style.display = "table";
        var tr = document.createElement("div");
        tr.style.display = "table-row";
        var td = document.createElement("td");
        td.innerHTML = "<b>Media ID:</b>";
        tr.append(td);
        td = document.createElement("td");
        td.innerHTML = arrayToBeTable[i][0];
        tr.append(td);
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        td.innerHTML = "<b>Uploader:</b>";
        tr.append(td);
        td = document.createElement("td");
        td.innerHTML = arrayToBeTable[i][1];
        tr.append(td);
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        td.innerHTML = "<b>NSFW:</b>";
        tr.append(td);
        td = document.createElement("td");
        var nsfwSelect = document.createElement("select");
        nsfwSelect.name = "nsfw";
        var oFalse = document.createElement("option");
        oFalse.text = "False";
        nsfwSelect.add(oFalse);
        var oTrue = document.createElement("option");
        oTrue.text = "True";
        nsfwSelect.add(oTrue);
        if (arrayToBeTable[i][3]) {
            nsfwSelect.selectedIndex = "1";
        } else {
            nsfwSelect.selectedIndex = "0";
        }
        td.append(nsfwSelect);
        tr.append(td);
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        td.innerHTML = "<b>Date:</b>";
        tr.append(td);
        td = document.createElement("td");
        td.innerHTML = arrayToBeTable[i][4];
        tr.append(td);
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        td.innerHTML = "<b>Title:</b>";
        tr.append(td);
        td = document.createElement("td");
        var titleInput = document.createElement("input");
        titleInput.type = "text";
        titleInput.name = "title";
        titleInput.value = arrayToBeTable[i][5];
        titleInput.style.width = "100%";
        td.style.width = "100%";
        td.append(titleInput);
        tr.append(td);
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        td.innerHTML = "<b>Tags:</b>";
        tr.append(td);
        if (arrayToBeTable[i][6].length > 0) {
            for (var x in arrayToBeTable[i][6]) {
                td = document.createElement("span");
                var tagCBInput = document.createElement("input");
                tagCBInput.type = "checkbox";
                tagCBInput.checked = true;
                tagCBInput.name = "tags";
                tagCBInput.value = arrayToBeTable[i][6][x].tagid;
                td.append(tagCBInput);
                var a = document.createElement("a");
                a.innerHTML = "#" + arrayToBeTable[i][6][x].tag;
                a.href = "browse.html?tag=1&search=" + arrayToBeTable[i][6][x].tag;
                a.style.color = "white";
                td.append(a);
                tr.appendChild(td);
            }
        } else {
            var tdfiller = document.createElement("span");
            tdfiller.innerHTML = "No tags";
            tr.append(tdfiller);
        }
        table.append(tr);
        tr = document.createElement("tr");
        td = document.createElement("td");
        var savetd = document.createElement("td");
        var saveButton = document.createElement("button");
        saveButton.textContent = "Save";
        saveButton.type = "submit";
        savetd.append(saveButton);
        tr.append(savetd);
        var removetd = document.createElement("td");
        var removeForm = document.createElement("form");
        var removeButton = document.createElement("button");
        removeButton.textContent = "Remove";
        removeButton.type = "submit";
        removeForm.append(removeButton);
        removetd.append(removeForm);
        tr.append(removetd);
        table.append(tr);
        tabletd.append(table);
        wrap.append(tabletd);
        tables.append(wrap);
    }
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
            window.location = "index.html";
            console.log(JSON.parse(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            window.location = "index.html";
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


// -------------------------------*/
// ------ admin.html loads ------ */
// -------------------------------*/

$("#admin-users").submit(function (event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/users",
        data: $("#admin-users").serialize(),
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            arrayToTable(jsonArrayToArray(jsondata));

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});

$("#admin-comments").submit(function (event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/comments",
        data: $("#admin-comments").serialize(),
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            arrayToTable(jsonArrayToArray(jsondata));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});

$("#admin-medias").submit(function (event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/medias",
        data: $("#admin-medias").serialize(),
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            //arrayToTable(jsonArrayToArray(jsondata));
            adminMediaToTable(jsonArrayToArray(jsondata));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});

$("#admin-tags").submit(function (event) {
    event.preventDefault();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/tags",
        data: $("#admin-tags").serialize(),
        success: function (data, textStatus, xhr) {
            var jsondata = JSON.parse(data);
            arrayToTable(jsonArrayToArray(jsondata));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});