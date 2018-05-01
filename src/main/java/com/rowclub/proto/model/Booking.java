package com.rowclub.proto.model;

public class Booking {
    private int booking_id;
    private boolean booking_out;
    private int booking_count_passengers;
    private int booking_id_captain;

    public Booking(int BookingList, boolean booking_out, int booking_count_passengers, int booking_id_captain) {
        this.booking_id = BookingList+1;
        this.booking_out = booking_out;
        this.booking_count_passengers = booking_count_passengers;
        this.booking_id_captain = booking_id_captain;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public boolean isBooking_out() {
        return booking_out;
    }

    public void setBooking_out(boolean booking_out) {
        this.booking_out = booking_out;
    }

    public int getBooking_count_passengers() {
        return booking_count_passengers;
    }

    public void setBooking_count_passengers(int booking_count_passengers) {
        this.booking_count_passengers = booking_count_passengers;
    }

    public int getBooking_id_captain() {
        return booking_id_captain;
    }

    public void setBooking_id_captain(int booking_id_captain) {
        this.booking_id_captain = booking_id_captain;
    }
}
