package com.rowclub.proto.model;

public class BoatTripLink {

    int boatTripLinkID;
    int fkMemberID;
    int fkBoatTripID;

    public BoatTripLink(int boatTripLinkID, int fkMemberID, int fkBoatTripID) {
        this.boatTripLinkID = boatTripLinkID;
        this.fkMemberID = fkMemberID;
        this.fkBoatTripID = fkBoatTripID;
    }

    public int getBoatTripLinkID() {
        return boatTripLinkID;
    }

    public void setBoatTripLinkID(int boatTripLinkID) {
        this.boatTripLinkID = boatTripLinkID;
    }

    public int getFkMemberID() {
        return fkMemberID;
    }

    public void setFkMemberID(int fkMemberID) {
        this.fkMemberID = fkMemberID;
    }

    public int getFkBoatTripID() {
        return fkBoatTripID;
    }

    public void setFkBoatTripID(int fkBoatTripID) {
        this.fkBoatTripID = fkBoatTripID;
    }
}