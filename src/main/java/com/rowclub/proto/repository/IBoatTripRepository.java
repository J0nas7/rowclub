package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IBoatTripRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<BoatTrip> readAllBoatTrips();

    int getBoatTripListSize();

    int getBoatTripOnWaterCount();

    void createBoatTrip(int boatID, String distance, String estDuration, String location, String datestamp, int completionTime, long timestamp, String whattodo, String[] guests) throws ParseException;

    BoatTrip readBoatTrip(int tripID);

    void actionBoatTrip(int tripID, String action);

    void updateBoatTrip(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException;

    void deleteBoatTrip(int tripID);
}
