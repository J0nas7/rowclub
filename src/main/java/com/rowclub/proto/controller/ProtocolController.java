package com.rowclub.proto.controller;

import com.rowclub.proto.model.Booking;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProtocolController {

    //DatabaseController DBconn = new DatabaseController();
    List<Booking> BookingList = new ArrayList<>();
    public ProtocolController() {
        BookingList.add(new Booking(BookingList.size(), true, 5, 1));
        BookingList.add(new Booking(BookingList.size(), false, 2, 27));
        BookingList.add(new Booking(BookingList.size(), true, 4, 133));
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("bookingAttr", BookingList);
        return "welcome";
    }
}
