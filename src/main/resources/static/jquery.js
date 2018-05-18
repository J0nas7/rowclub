$(document).ready(function() {
    if ($(".boattrip_form").length) {
        $(".moreGuests .cta").click(function() {
            $(".moreGuests input").clone().appendTo(".theGuests");
        });

        $('.boattrip_form .boattrip_predetermined').change(function() {
           var boattrip_location = $('option:selected', this).attr('location');
           var boattrip_distance = $('option:selected', this).attr('distance');
           var boattrip_duration = $('option:selected', this).attr('duration');
           $(".boattrip_form input#boattrip_distance").val(boattrip_distance);
           $(".boattrip_form input#boattrip_estduration").val(boattrip_duration);
           $(".boattrip_form input#boattrip_location").val(boattrip_location);
        });
    }
});