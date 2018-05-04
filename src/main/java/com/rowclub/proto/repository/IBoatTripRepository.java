package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;

import java.util.List;

public interface IBoatTripRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<BoatTrip> readAllBoatTrips();

    void createBoatTrip(BoatTrip boattrip);

    BoatTrip readBoatTrip(int tripID);

    void updateBoatTrip(BoatTrip boattrip);

    void deleteBoatTrip(int tripID);
}
