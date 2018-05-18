package com.rowclub.proto.model;

import java.util.Date;

public class Warning {

    private int warningId;
    private String info;
    private int boatTripId;
    private String DateStamp;
    private int TimeStamp;

    public Warning(int warningId, String info, int boatTripId, String dateStamp, int timeStamp) {
        this.warningId = warningId;
        this.info = info;
        this.boatTripId = boatTripId;
        DateStamp = dateStamp;
        TimeStamp = timeStamp;
    }

    public int getWarningId() {
        return warningId;
    }

    public void setWarningId(int warningId) {
        this.warningId = warningId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getBoatTripId() {
        return boatTripId;
    }

    public void setBoatTripId(int boatTripId) {
        this.boatTripId = boatTripId;
    }

    public String getDateStamp() {
        return DateStamp;
    }

    public void setDateStamp(String dateStamp) {
        DateStamp = dateStamp;
    }

    public int getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        TimeStamp = timeStamp;
    }
}
