package com.rowclub.proto.controller;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.repository.IBoatTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ProtocolController {

    public static DatabaseController DBconn = new DatabaseController();

    public static String ProtocolPageDatestamp;
    public static String[] MonthsShortName = {"", "Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};
    public static void MainConfig() {
        int MonthKey = Integer.parseInt(new SimpleDateFormat("M").format(Calendar.getInstance().getTime()));
        ProtocolPageDatestamp = new SimpleDateFormat("dd ").format(Calendar.getInstance().getTime());
        ProtocolPageDatestamp += MonthsShortName[MonthKey];
        ProtocolPageDatestamp += new SimpleDateFormat(" yy HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    // Dependence injection
    // Design pattern: Strategy pattern
    @Autowired
    private IBoatTripRepository boatTripRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        ProtocolController.MainConfig();
        model.addAttribute("boattripList", boatTripRepository.readAllBoatTrips());
        model.addAttribute("boattripOut", boatTripRepository.getBoatTripOnWaterCount());
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);
        return "welcome";
    }

    @GetMapping("/new_boattrip")
    public String new_boattrip(Model model) {
        ProtocolController.MainConfig();
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);
        return "new_boattrip";
    }
}
