package com.rowclub.proto.controller;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.repository.IBoatTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProtocolController {

    public static DatabaseController DBconn = new DatabaseController();
    List<BoatTrip> BoatTripList = new ArrayList<>();

    // Dependence injection
    // Design pattern: Strategy pattern
    @Autowired
    private IBoatTripRepository boatTripRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("boattripAttr", BoatTripList);
        return "welcome";
    }
}
