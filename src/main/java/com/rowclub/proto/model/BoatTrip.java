package com.rowclub.proto.model;

import java.text.DateFormat;
import java.util.Date;

public class BoatTrip {
    private int BoatTripID;
    private int BoatID;
    private double Distance;
    private int EstDuration;
    private String Location;
    private Date Datestamp;
    private int SeasonID;
    private boolean OnWater;
    private int CompletionTime;
    private int Timestamp;

    public BoatTrip(int boatTripID, int boatID, double distance, int estDuration, String location, Date datestamp, int seasonID, boolean OnWater, int completionTime, int timestamp) {
        BoatTripID = boatTripID;
        BoatID = boatID;
        Distance = distance;
        EstDuration = estDuration;
        Location = location;
        Datestamp = datestamp;
        SeasonID = seasonID;
        this.OnWater = OnWater;
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

    public Date getDatestamp() {
        return Datestamp;
    }

    public void setDatestamp(Date datestamp) {
        Datestamp = datestamp;
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
}
