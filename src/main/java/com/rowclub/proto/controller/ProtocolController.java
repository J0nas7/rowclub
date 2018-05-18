package com.rowclub.proto.controller;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.rowclub.proto.repository.IBoatTripRepository;
import com.rowclub.proto.repository.IPreDetTripsRepository;
import com.rowclub.proto.repository.IUtilitiesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.sql.Types.NULL;

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
    @Autowired
    private IUtilitiesRepository UtilitiesRepository;
    @Autowired
    private IPreDetTripsRepository PreDetTripsRepository;

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
        model.addAttribute("Mateys", UtilitiesRepository.findAllMateys());
        model.addAttribute("PreDetTrips", PreDetTripsRepository.readAllPreDetTripss());
        return "new_boattrip";
    }

    @PostMapping("/form_boattrip")
    public String form_boattrip(@RequestParam("boattrip_distance") String distance,
                                @RequestParam("boattrip_estduration") String estDuration,
                                @RequestParam("boattrip_location") String location,
                                @RequestParam("boattrip_datestamp") String datestamp,
                                @RequestParam("submit") String whattodo,
                                @RequestParam("boattrip_guests[]") String[] guests
    ) throws ParseException {
        boatTripRepository.createBoatTrip(boatTripRepository.getBoatTripListSize(), 1, distance, estDuration, location, datestamp, NULL, Instant.now().getEpochSecond(), whattodo, guests);
        return "redirect:/welcome";
    }
}
