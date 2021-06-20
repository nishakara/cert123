

function submitMonitor() {
    var id = $("#id").val();
    var submitPath;
    if (!id || id.length === 0) {
        submitPath = "/CreateMonitor";
        console.log("sending create monitor request");

    } else {
        submitPath = "/UpdateMonitor";
        console.log("sending update monitor request");
    }
    var formData = $('#createMonitorForm').serialize();
    console.log(formData);
    $.ajax({
        async: true,
        crossDomain: false,
        url: getContextPath() + submitPath,
        method: "POST",
        dataType: 'json',
        data: formData,
        success: function (response) {
            console.log("Response message : " + response.message);
            loadDataToMonitorsTable();
            resetMonitorForm();
            displayModal('success', 'Success !', response.message);
        },
        error: function (er) {
            console.error("Error creating monitor. Error response : " + er.responseText);
            displayModal('error', 'Error !', JSON.parse(er.responseText).message);
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
            displayModal('error', 'Error !', "Error loading monitors table : " + er.responseText);
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
                '  <td><input type="checkbox" id="check-all" class="flat" value="" ' + getCheckedValue(monitor.enabled) + '></td>\n' +
                '  <td>' + monitor.monitorName + '</td>\n' +
                '  <td>Certificate Validity</td>\n' +
                '  <td>' + monitor.status + '</td>\n' +
                '  <td>' + resolveUrl(monitor) + '</td>\n' +
                '  <td>\n' +
                '      <div class="row">\n' +
                '        <div class="col-md-6">\n' +
                '            <div class="btn-group">\n' +
                '              <button class="btn btn-success" type="button" onclick="runMonitor(\''+ monitor.monitorName +'\');">Run</button>\n' +
                '              <button class="btn btn-warning" type="button" onClick="editMonitor(\'' + monitor.id + '\',\'' +monitor.monitorName+ '\',\'' +monitor.hostName+ '\',\'' +monitor.alertDays+ '\',\'' +monitor.groupEmail+ '\',\'' +monitor.port+ '\')">Edit</button>\n' +
                '              <button class="btn btn-danger" type="button" onclick="deleteMonitor(\''+ monitor.id +'\');">Delete</button>\n' +
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
    $.ajax({
        url: getContextPath() + "/RunMonitor",
        async: true,
        crossDomain: false,
        method: "POST",
        data: { "monitorName" : monitorName },
        dataType: 'json',
        success: function (response) {
            loadDataToMonitorsTable();
            console.log("Response message : " + response.status);
            var responseStr = generateResponseStr(response);
            displayModal('success', 'Success !', responseStr);
        },
        error: function (er) {
            console.error("Error running monitor. Error response : " + er.responseText);
            displayModal('error', 'Error !', "Error running monitor.\n" + generateResponseStr(JSON.parse(er.responseText)));
        }
    });
}

function editMonitor(id, monitorName, hostName, alertDays, groupEmail, port){
    $("#id").val(id);
    $("#monitorName").val(monitorName);
    $('#monitorName').prop('readonly', true);
    $("#hostName").val(hostName);
    $("#alertDays").val(alertDays);
    $("#groupEmail").val(groupEmail);
    $("#port").val(port);
}

function deleteMonitor(monitorId) {
    console.log('Sending DeleteMonitor request ' + monitorId);
    $.ajax({
        url: getContextPath() + "/DeleteMonitor",
        async: true,
        crossDomain: false,
        method: "DELETE",
        data: { "monitorId" : monitorId },
        dataType: 'json',
        success: function (response) {
            console.log("Response message : " + response.message);
            loadDataToMonitorsTable();
            displayModal('success', 'Success !', response.message);
        },
        error: function (er) {
            console.error("Error deleting monitor. Error response : " + er.responseText);
            displayModal('error', 'Error !', "Message    : " + JSON.parse(er.responseText).message);
        }
    });
}

function generateResponseStr(response) {
    var responseStr = "Status     : " + response.status + "\n";
    if (response.status === "Success") {
        responseStr = responseStr +
            "Valid From : " + response.valid_from + "\n" +
            "Valid To   : " + response.valid_to + "\n" +
            "Days Left  : " + response.diff_in_days + "\n" +
            "Issuer DN  : " + response.issuer_dn;
    } else {
        responseStr = responseStr +
            "Message    : " + response.message + "\n";
    }
    return responseStr;
}


function resolveUrl(monitor) {
    if (monitor.port !== null && monitor.port !== "") {
        return monitor.hostName + ":" + monitor.port;
    } else {
        return monitor.hostName;
    }
}

function getCheckedValue(booleanValue) {
    return booleanValue ? "checked" : "";
}

function resetMonitorForm(){
    $("#id").val("");
    $("#monitorName").val("");
    $('#monitorName').prop('readonly', false);
    $("#hostName").val("");
    $("#alertDays").val("");
    $("#groupEmail").val("");
    $("#port").val("");
}

function displayModal(type,header,body){
    if(type=="error"){
        color = '#dc3545';
    } else if (type == "success"){
        color = '#28a745';
    }
    $("#monitorModalTitle").text(header);
    $("#monitorModalTitle").css('color', color);
    $("#monitorModalContent").text(body);
    $('#monitorModal').modal('show');
}
