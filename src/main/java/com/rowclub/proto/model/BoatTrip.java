package com.rowclub.proto.model;

import java.text.DateFormat;
import java.util.Date;

public class BoatTrip {
    private int BoatTripID;
    private int BoatID;
    private double Distance;
    private int EstDuration;
    private String Location;
    private String Datestamp;
    private Date Date;
    private int SeasonID;
    private boolean OnWater;
    private int CompletionTime;
    private int Timestamp;
    private int Passengers;

    public BoatTrip(int boatTripID, int boatID, double distance, int estDuration, String location, String datestamp, java.util.Date date, int seasonID, boolean onWater, int completionTime, int timestamp, int passengers) {
        BoatTripID = boatTripID;
        BoatID = boatID;
        Distance = distance;
        EstDuration = estDuration;
        Location = location;
        Datestamp = datestamp;
        Date = date;
        SeasonID = seasonID;
        OnWater = onWater;
        CompletionTime = completionTime;
        Timestamp = timestamp;
        Passengers = passengers;
    }

    public int getBoatTripID() {
        return BoatTripID;
    }

    public void setBoatTripID(int boatTripID) {
        BoatTripID = boatTripID;
    }

    public int getBoatID() {
        return BoatID;
    }

    public void setBoatID(int boatID) {
        BoatID = boatID;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public int getEstDuration() {
        return EstDuration;
    }

    public void setEstDuration(int estDuration) {
        EstDuration = estDuration;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDatestamp() {
        return Datestamp;
    }

    public void setDatestamp(String datestamp) {
        Datestamp = datestamp;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public int getSeasonID() {
        return SeasonID;
    }

    public void setSeasonID(int seasonID) {
        SeasonID = seasonID;
    }

    public boolean isOnWater() {
        return OnWater;
    }

    public void setOnWater(boolean onWater) {
        OnWater = onWater;
    }

    public int getCompletionTime() {
        return CompletionTime;
    }

    public void setCompletionTime(int completionTime) {
        CompletionTime = completionTime;
    }

    public int getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(int timestamp) {
        Timestamp = timestamp;
    }

    public int getPassengers() {
        return Passengers;
    }

    public void setPassengers(int passengers) {
        Passengers = passengers;
    }
}
