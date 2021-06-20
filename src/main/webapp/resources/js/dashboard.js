$(document).ready(function () {
    validateUser();
    loadDataToMonitorsTableInDashboard();
    $('#LogoutButton').click(function () {
        removeCookie('userName');
    });
});

function loadDataToMonitorsTableInDashboard() {
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
            populateMonitorsTableInDashboard(data);
        },
        error: function (er) {
            console.error("Error loading monitors table. Error response : " + er.responseText);
            displayModal('error', 'Error !', "Error loading monitors table : " + er.responseText);
        }
    });
}

function populateMonitorsTableInDashboard(data) {
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
                '              <button class="btn btn-success" type="button" onclick="runMonitorInDashboard(\''+ monitor.monitorName +'\');">Run</button>\n' +
                '              <button class="btn btn-danger" type="button" onclick="deleteMonitorInDashboard(\''+ monitor.id +'\');">Delete</button>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '      </div>\n' +
                '  </td>\n' +
                '</tr>';
            $('#monitors-table-body').append(html_text);
        }
    }
}

function runMonitorInDashboard(monitorName) {
    console.log('Sending RunMonitor request ' + monitorName);
    $.ajax({
        url: getContextPath() + "/RunMonitor",
        async: true,
        crossDomain: false,
        method: "POST",
        data: { "monitorName" : monitorName },
        dataType: 'json',
        success: function (response) {
            loadDataToMonitorsTableInDashboard();
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

function deleteMonitorInDashboard(monitorId) {
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
            loadDataToMonitorsTableInDashboard();
            displayModal('success', 'Success !', response.message);
        },
        error: function (er) {
            console.error("Error deleting monitor. Error response : " + er.responseText);
            displayModal('error', 'Error !', "Message    : " + JSON.parse(er.responseText).message);
        }
    });
}