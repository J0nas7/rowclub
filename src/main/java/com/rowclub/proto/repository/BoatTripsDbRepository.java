package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class BoatTripsDbRepository implements IBoatTripRepository {
    private List<BoatTrip> BoatTripList;

    public BoatTripsDbRepository () {
        BoatTripList = new ArrayList<>();
        String BoatTripSql = "SELECT * FROM ProductLines WHERE productLine='Ships'";
        DBconn.dbQuery(BoatTripSql);
        //;
    }

    @Override
    public List<BoatTrip> readAllBoatTrips() {
        return BoatTripList;
    }

    @Override
    public void createBoatTrip(BoatTrip boattrip) {
        boattrip.setBoatTripID(BoatTripList.size()+1);
        BoatTripList.add(boattrip);
    }

    @Override
    public BoatTrip readBoatTrip(int tripID) {
        return BoatTripList.get(tripID-1);
    }

    @Override
    public void updateBoatTrip(BoatTrip boattrip) { BoatTripList.set(boattrip.getBoatTripID()-1, boattrip); }

    @Override
    public void deleteBoatTrip(int tripID) {
        BoatTripList.remove(tripID-1);
    }
}