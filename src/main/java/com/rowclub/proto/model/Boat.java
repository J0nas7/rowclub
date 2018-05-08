package com.rowclub.proto.model;

public class Boat {
    int boatID;
    String name;
    int type;
    String status;
    int seats;

    public Boat(int boatID, String name, int type, String status, int seats) {
        this.boatID = boatID;
        this.name = name;
        this.type = type;
        this.status = status;
        this.seats = seats;
    }

    public int getBoatID() {
        return boatID;
    }

    public void setBoatID(int boatID) {
        this.boatID = boatID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    
}
