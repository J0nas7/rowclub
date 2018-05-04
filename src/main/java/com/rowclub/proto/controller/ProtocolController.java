package com.rowclub.proto.controller;

import com.rowclub.proto.model.BoatTrip;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProtocolController {

    DatabaseController DBconn = new DatabaseController();
    List<BoatTrip> BoatTripList = new ArrayList<>();
    public ProtocolController() {
        /*BoatTripList.add(new BoatTrip(BoatTripList.size(), true, 5, 1));
        BoatTripList.add(new BoatTrip(BoatTripList.size(), false, 2, 27));
        BoatTripList.add(new BoatTrip(BoatTripList.size(), true, 4, 133));*/
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("boattripAttr", BoatTripList);
        return "welcome";
    }
}
