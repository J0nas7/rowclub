package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;

import java.sql.SQLException;
import java.util.List;

public interface IBoatTripRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<BoatTrip> readAllBoatTrips();

    int getBoatTripListSize();

    int getBoatTripOnWaterCount();

    void createBoatTrip(BoatTrip boattrip);

    BoatTrip readBoatTrip(int tripID);

    void updateBoatTrip(BoatTrip boattrip);

    void deleteBoatTrip(int tripID);
}
