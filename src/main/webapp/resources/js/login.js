
$(document).ready(function() {
    $('#SubmitCredentialsForm').submit(function (event) {
        event.preventDefault();
        submitCredentials();
    });
    $('#CreateAccountForm').submit(function (event) {
        event.preventDefault();
        createAccount();
    });
});

function submitCredentials() {
    var formData = $('#SubmitCredentialsForm').serialize();
    // var username = formData.userName;
    console.log("Submitting credentials for login");
    $.ajax({
        async: true,
        crossDomain: false,
        url: getContextPath() + "/SubmitCredentials",
        method: "POST",
        dataType: 'json',
        data: formData,
        success: function (response) {
            console.log("Response message : " + response.message);
            document.cookie = "userName=" + response.username + ";path=/";
            window.location = getContextPath() + '/';
        },
        error: function (er) {
            console.error("Error with user login. Error response : " + er.responseText);
            alert(er.responseText);
        }
    });
}

function createAccount() {
    var formData = $('#CreateAccountForm').serialize();
    // var username = formData.userName;
    console.log("Submitting credentials to create user account");
    $.ajax({
        async: true,
        crossDomain: false,
        url: getContextPath() + "/CreateAccount",
        method: "POST",
        dataType: 'json',
        data: formData,
        success: function (response) {
            console.log("Response message : " + response.message );
            document.cookie = "userName=" + response.username + ";path=/";
            window.location = getContextPath() + '/';
        },
        error: function (er) {
            console.error("Error creating user account. Error response : " + er.responseText);
            alert(er.responseText);
        }
    });
}
