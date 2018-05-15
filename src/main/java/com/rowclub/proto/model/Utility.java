package com.rowclub.proto.model;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utility{

    //-----SEASON VARIABLES-----//
    private int seasonId;
    private Date year;
    private DateTimeFormatter startDate;
    private Date endDate;


    //-----WARNING VARIABLES-----//
    private int warningId;
    private String overtimeWar;


    //-----UTILITY CONSTRUCTOR-----//

    public Utility(int seasonId, DateTimeFormatter startDate) {

        setSeasonId(seasonId);
        this.year = getYear();
        setStartDate(startDate);
        //setEndDate(endDate);

    }

//-----SEASON GETTERS & SETTERS-----//

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getSeasonId() {

        return seasonId;
    }

    public void setYear(Date year) {

        Calendar.getInstance().get(Calendar.YEAR);
        this.year = year;
    }

    public Date getYear() {

        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
        return year;
    }

    public void setStartDate(DateTimeFormatter startDate) {

        startDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = startDate;
    }

    public DateTimeFormatter getStartDate() {

        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {

        return endDate;
    }

    public int getWarningId() {

        return warningId;
    }

//-----WARNING GETTERS & SETTERS-----//

    public void setWarningId(int warningId) {
        this.warningId = warningId;
    }

    public void setOvertimeWar(String overtimeWar) {
        this.overtimeWar = overtimeWar;
    }


//-----SEASON DISTANCE-----//

    public void seasonDistance(int seasonId, int memberId) {


    }
}

