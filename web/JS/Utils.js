/**
 * Created by Mafields on 21.11.2016.
 */

function jsonToTable(jsonToBeTable) {
    var jsonAsArray = $.map(jsonToBeTable, function (key) {
        return key;
    });
    console.log(jsonAsArray);
    return jsonAsArray;
}

$("#admin-medias").submit(function (event) {
    console.log("Makkara");
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/medias",
        data: $("#admin-medias").serialize(),
        success: function (data, textStatus, xhr) {
            alert(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});

$.get("http://localhost:8080/MetroShare/webresources/users/matti/activity", function (data) {
    alert("Loaded user: " + data.activity);
});

function onUserListSubmit() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/users",
        data: $("#admin-users").serialize(),
        success: function (data, textStatus, xhr) {
            alert(data);
            return data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
}

$("#login-form").submit(function (event) {
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/login",
        data: $("#login-form").serialize(),
        success: function (data, textStatus, xhr) {
            alert(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});

$("#admin-users").submit(function (event) {
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/MetroShare/webresources/admin/users",
        data: $("#admin-users").serialize(),
        success: function (data, textStatus, xhr) {
            alert(data);
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
            alert(data);
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
            alert(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error: " + errorThrown);
        }
    });
});
