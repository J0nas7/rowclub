package com.rowclub.proto.model;

import java.time.LocalDate;

public class BoatTrip {
    private int BoatTripID;
    private int BoatID;
    private double Distance;
    private String EstDuration;
    private String Location;
    private LocalDate Datestamp;
    private int SeasonID;
    private String CompletionTime;
    private String Timestamp;

    public BoatTrip(int boatTripID, int boatID, double distance, String estDuration, String location, LocalDate datestamp, int seasonID, String completionTime, String timestamp) {
        BoatTripID = boatTripID;
        BoatID = boatID;
        Distance = distance;
        EstDuration = estDuration;
        Location = location;
        Datestamp = datestamp;
        SeasonID = seasonID;
        CompletionTime = completionTime;
        Timestamp = timestamp;
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

    public String getEstDuration() {
        return EstDuration;
    }

    public void setEstDuration(String estDuration) {
        EstDuration = estDuration;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public LocalDate getDatestamp() {
        return Datestamp;
    }

    public void setDatestamp(LocalDate datestamp) {
        Datestamp = datestamp;
    }

    public int getSeasonID() {
        return SeasonID;
    }

    public void setSeasonID(int seasonID) {
        SeasonID = seasonID;
    }

    public String getCompletionTime() {
        return CompletionTime;
    }

    public void setCompletionTime(String completionTime) {
        CompletionTime = completionTime;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
