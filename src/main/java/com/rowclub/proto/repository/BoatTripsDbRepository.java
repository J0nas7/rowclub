package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoatTripsDbRepository implements IBoatTripRepository {
    private List<BoatTrip> BoatTripList;

    public BoatTripsDbRepository () {
        BoatTripList = new ArrayList<>();

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