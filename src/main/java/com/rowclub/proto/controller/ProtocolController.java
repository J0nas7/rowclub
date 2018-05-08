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
    public static String ProtocolPageTimestamp;

    public static void MainConfig() {
        ProtocolPageTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }


    // Dependence injection
    // Design pattern: Strategy pattern
    @Autowired
    private IBoatTripRepository boatTripRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("boattripList", boatTripRepository.readAllBoatTrips());
        model.addAttribute("boattripOut", boatTripRepository.getBoatTripOnWaterCount());
        model.addAttribute("ProtocolPageTimestamp", ProtocolPageTimestamp);
        ProtocolController.MainConfig();
        return "welcome";
    }
}
