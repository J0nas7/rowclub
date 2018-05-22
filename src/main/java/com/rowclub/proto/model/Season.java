package com.rowclub.proto.model;

import java.util.Date;

public class Season {

    private int seasonID;
    private Date startDate;
    private Date endDate;

    public Season(int seasonID, Date startDate, Date endDate) {
        this.seasonID = seasonID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
