// File: WebContent/js/main.js

document.addEventListener('DOMContentLoaded', function () {

    // Mobile nav toggle, works against .topnav__links on every role's header
    var toggle = document.querySelector('.topnav__toggle');
    var links = document.querySelector('.topnav__links');
    if (toggle && links) {
        toggle.addEventListener('click', function () {
            links.classList.toggle('is-open');
        });
    }

    // Dismissable alerts
    document.querySelectorAll('.alert[data-dismissible]').forEach(function (alertBox) {
        alertBox.style.cursor = 'pointer';
        alertBox.title = 'Click to dismiss';
        alertBox.addEventListener('click', function () {
            alertBox.style.display = 'none';
        });
    });

    // Confirm before destructive actions (cancel appointment, deactivate account, etc.)
    document.querySelectorAll('form[data-confirm]').forEach(function (form) {
        form.addEventListener('submit', function (event) {
            var message = form.getAttribute('data-confirm') || 'Are you sure?';
            if (!window.confirm(message)) {
                event.preventDefault();
            }
        });
    });
});