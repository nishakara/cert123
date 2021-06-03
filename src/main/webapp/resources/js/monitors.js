
$(document).ready(function(){
    validateUser();
    loadDataToMonitorsTable();
    $('#createMonitorForm').submit(function(event) {
        event.preventDefault();
        submitMonitor();
    });
    $('#LogoutButton').click(function () {
        removeCookie('userName');
    });
});

function submitMonitor() {
    var formData = $('#createMonitorForm').serialize();
    console.log("sending create monitor request");
    $.ajax({
        async: true,
        crossDomain: false,
        url: getContextPath() + "/CreateMonitor",
        method: "POST",
        dataType: 'json',
        data: formData,
        success: function (response) {
            console.log("Response message : " + response.message);
            loadDataToMonitorsTable();
            alert(response.message);
        },
        error: function (er) {
            console.error("Error creating monitor. Error response : " + er.responseText);
            alert(er.responseText);
        }
    });
}

function loadDataToMonitorsTable() {
    console.log("loading data to MonitorsTable");
    $.ajax({
        async: true,
        crossDomain: false,
        url: getContextPath() + "/getMonitors/" + getCookie('userName'),
        method: "GET",
        dataType: 'json',
        success: function (data) {
            console.log(data);
            cleanMonitorsTable();
            populateMonitorsTable(data);
        },
        error: function (er) {
            console.error("Error loading monitors table. Error response : " + er.responseText);
            alert("Error loading monitors table : " + er.responseText);
        }
    });
}

function cleanMonitorsTable() {
    var tableBody = document.getElementById("monitors-table-body");
    var rowCount = tableBody.childElementCount;
    for (var i = 0; i < rowCount; i++) {
        tableBody.deleteRow(0);
    }
}


function populateMonitorsTable(data) {
    if (data.length === 0) {
        console.error("Error loading table data..!!")
    } else {
        for (var i = 0; i < data.length; i++) {
            monitor = data[i];
            var html_text =
                '<tr>\n' +
                '  <th><input type="checkbox" id="check-all" class="flat" value="" ' + getCheckedValue(monitor.enabled) + '></th>\n' +
                '  <td>' + monitor.monitorName + '</td>\n' +
                '  <td>Certificate Validity</td>\n' +
                '  <td>' + getStatus(monitor.status) + '</td>\n' +
                '  <td>' + resolveUrl(monitor) + '</td>\n' +
                '  <td>\n' +
                '      <div class="row">\n' +
                '        <div class="col-md-6">\n' +
                '            <div class="btn-group">\n' +
                '              <button class="btn btn-success" type="button" onclick="runMonitor(\''+ monitor.monitorName +'\');">Run</button>\n' +
                '              <button class="btn btn-warning" type="button" onclick="editMonitor(\''+ monitor.monitorName +'\');">Edit</button>\n' +
                '              <button class="btn btn-danger" type="button" onclick="deleteMonitor(\''+ monitor.monitorName +'\');">Delete</button>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '      </div>\n' +
                '  </td>\n' +
                '</tr>';
            $('#monitors-table-body').append(html_text);
        }
    }
}

function runMonitor(monitorName) {
    console.log('Sending RunMonitor request ' + monitorName);
    // $.ajax({
    //     url: getContextPath() + "/RunMonitor",
    //     async: true,
    //     crossDomain: false,
    //     method: "POST",
    //     data: { "monitorName" : monitorName },
    //     dataType: 'json',
    //     success: function (response) {
    //         loadDataToMonitorsTable();
    //         console.log("Response message : " + response.message);
    //         alert(response.message);
    //     },
    //     error: function (er) {
    //         console.error("Error running monitor. Error response : " + er.responseText);
    //         alert(er.responseText);
    //     }
    // });
}

function deleteMonitor(monitorName) {
    console.log('Sending DeleteMonitor request ' + monitorName);
    $.ajax({
        url: getContextPath() + "/DeleteMonitor",
        async: true,
        crossDomain: false,
        method: "DELETE",
        data: { "monitorName" : monitorName },
        dataType: 'json',
        success: function (response) {
            console.log("Response message : " + response.message);
            loadDataToMonitorsTable();
            alert(response.message);
        },
        error: function (er) {
            console.error("Error deleting monitor. Error response : " + er.responseText);
            alert(er.responseText);
        }
    });
}


function resolveUrl(monitor) {
    if (monitor.port !== null && monitor.port !== "") {
        return monitor.hostName + ":" + monitor.port;
    } else {
        return monitor.hostName;
    }
}

function getStatus(booleanValue) {
    return booleanValue ? "OK" : "Failed";
}

function getCheckedValue(booleanValue) {
    return booleanValue ? "checked" : "";
}

