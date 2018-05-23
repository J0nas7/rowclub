package com.rowclub.proto.model;

import com.rowclub.proto.repository.UtilitiesDbRepository;

import java.util.Date;

public class Warning {

    private int warningId;
    private String info;
    private int boatTripId;
    private Date DateStamp;
    private int TimeStamp;
    private String TimeStampString;

    public Warning(int warningId, String info, int boatTripId, Date dateStamp, int timeStamp) {
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

    public Date getDateStamp() {
        return DateStamp;
    }

    public void setDateStamp(Date dateStamp) {
        DateStamp = dateStamp;
    }

    public int getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getTimeStampString() {
        return TimeStampString;
    }

    public void setTimeStampString(String timeStampString) {
        TimeStampString = timeStampString;
    }
}
