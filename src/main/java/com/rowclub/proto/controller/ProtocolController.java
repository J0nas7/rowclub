package com.rowclub.proto.controller;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.rowclub.proto.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
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
    @Autowired
    private IMemberRepository MemberRepository;
    @Autowired
    private IBoatTripLinkRepository boatTripLinkRepository;
    @Autowired
    private IWarningRepository warningRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) throws SQLException {
        ProtocolController.MainConfig();
        model.addAttribute("boattripList", boatTripRepository.readAllBoatTrips());
        model.addAttribute("boattripOut", boatTripRepository.getBoatTripOnWaterCount());
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);
        System.out.println(MemberRepository.getMemberListSize());
        return "welcome";
    }

    @GetMapping("/warning")
    public String warning(Model model) throws SQLException {
        ProtocolController.MainConfig();
        model.addAttribute("boattripList", boatTripRepository.readAllBoatTrips());
        model.addAttribute("boattripOut", boatTripRepository.getBoatTripOnWaterCount());
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);
        model.addAttribute("warningList", warningRepository.readAllWarnings());
        return "warning";
    }

    @GetMapping("/new_boattrip")
    public String new_boattrip(Model model) {
        ProtocolController.MainConfig();
        model.addAttribute("Mateys", UtilitiesRepository.findAllMateys());
        model.addAttribute("PreDetTrips", PreDetTripsRepository.readAllPreDetTripss());
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);
        return "new_boattrip";
    }

    @GetMapping("/read_boattrip")
    public String read_boattrip(@RequestParam("tripID") int tripID, Model model) throws SQLException {
        ProtocolController.MainConfig();
        List<Member> passengersList = UtilitiesRepository.membersOnTrip(tripID);
        List<Member> tempList = new ArrayList<>();
        String mate = null;
        for (Member matey: passengersList){
            if (!matey.isMate()) {
                tempList.add(matey);
            } else {
                mate = matey.getFirstName()+" "+matey.getLastName()+" ("+matey.getMemberID()+")";
            }
        }
        model.addAttribute("BoatTrip", boatTripRepository.readBoatTrip(tripID));
        model.addAttribute("BoatTripLinks", tempList);
        model.addAttribute("Matey", mate);
        model.addAttribute("PreDetTrips", PreDetTripsRepository.readAllPreDetTripss());
        model.addAttribute("ProtocolPageDatestamp", ProtocolPageDatestamp);

        return "read_boattrip";
    }

    @GetMapping("/boattrip_status")
    public String boattrip_status(@RequestParam("tripID") int tripID,
                                    Model model,
                                    @RequestParam("action") String action) {
        boatTripRepository.actionBoatTrip(tripID, action);
        return "redirect:/read_boattrip?tripID="+tripID;
    }

    @PostMapping("/form_boattrip")
    public String form_boattrip(@RequestParam("boattrip_matey") int matey,
                                @RequestParam("boattrip_distance") String distance,
                                @RequestParam("boattrip_estduration") String estDuration,
                                @RequestParam("boattrip_location") String location,
                                @RequestParam("boattrip_datestamp") String datestamp,
                                @RequestParam("submit") String whattodo,
                                @RequestParam("boattrip_guests[]") String[] guests
    ) throws ParseException, SQLException {
        boatTripRepository.createBoatTrip(2, distance, estDuration, location, datestamp, NULL, Instant.now().getEpochSecond(), whattodo, guests);

        boatTripLinkRepository.createBoatTripLink(matey, boatTripRepository.getBoatTripListSize());

        for (int i = 0; i < guests.length; i++) {
            if (!guests[i].trim().isEmpty()) {
                String GuestFirstName;
                String GuestLastName;
                if (guests[i].indexOf(' ') > 0) {
                    String[] SURandLASTname = guests[i].split(" ");
                    GuestFirstName = SURandLASTname[0];
                    GuestLastName = SURandLASTname[1];
                } else {
                    GuestFirstName = guests[i];
                    GuestLastName = " ";
                }

                MemberRepository.createGuest(GuestFirstName, GuestLastName);
                boatTripLinkRepository.createBoatTripLink(MemberRepository.getMemberListSize(), boatTripRepository.getBoatTripListSize());
            }
        }

        return "redirect:/welcome";
    }

    @PostMapping("/edit_boattrip")
    public String edit_boattrip(@RequestParam("boattrip_datestamp") String datestamp,
                                @RequestParam("boattrip_guests[]") String[] guests,
                                @RequestParam("boatrip_id") int tripID,
                                @RequestParam("boattrip_distance") String distance,
                                @RequestParam("boattrip_estduration") String estDuration,
                                @RequestParam("boattrip_location") String location
                                ) throws SQLException, ParseException {
        UtilitiesRepository.deleteMembersOnTrip(tripID);

        int Passengers = 1;
        for (int i = 0; i < guests.length; i++) {
            if (!guests[i].trim().isEmpty()) {
                String GuestFirstName;
                String GuestLastName;
                if (guests[i].indexOf(' ') > 0) {
                    String[] SURandLASTname = guests[i].split(" ");
                    GuestFirstName = SURandLASTname[0];
                    GuestLastName = SURandLASTname[1];
                } else {
                    GuestFirstName = guests[i];
                    GuestLastName = " ";
                }
                Passengers++;

                MemberRepository.createGuest(GuestFirstName, GuestLastName);
                boatTripLinkRepository.createBoatTripLink(MemberRepository.getMemberListSize(), tripID);
            }
        }

        boatTripRepository.updateBoatTrip(tripID, datestamp, distance, estDuration, location, Passengers);

        return "redirect:/welcome";
    }
}
