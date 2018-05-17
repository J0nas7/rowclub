package com.rowclub.proto.model;

public class PreDetTrips {
    int preID;
    String location;
    Double distance;
    int preEstDuration;

    public PreDetTrips(int preID, String location, Double distance, int preEstDuration) {
        this.preID = preID;
        this.location = location;
        this.distance = distance;
        this.preEstDuration = preEstDuration;
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getPreEstDuration() {
        return preEstDuration;
    }

    public void setPreEstDuration(int preEstDuration) {
        this.preEstDuration = preEstDuration;
    }
}
