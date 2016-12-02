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

