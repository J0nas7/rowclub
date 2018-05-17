$(document).ready(function() {
    if ($(".moreGuests").length) {
        $(".moreGuests .cta").click(function() {
            $(".moreGuests input").clone().appendTo(".theGuests");
        });
    }
});