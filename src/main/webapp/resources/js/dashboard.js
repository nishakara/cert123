$(document).ready(function () {
    validateUser();
    $('#LogoutButton').click(function () {
        removeCookie('userName');
    });
});