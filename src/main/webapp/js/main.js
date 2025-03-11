$(function () {

    /* When a form is submitted, make the spinner visible and disable all submit buttons */
    $('form').submit(function () {
        $(this).find('.spinner').css('visibility', 'visible');
    });

    /* Opt in for tooltips tooltips */
    $("[rel='tooltip']").tooltip({ placement: "auto"});
});