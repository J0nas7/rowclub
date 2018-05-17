package com.rowclub.proto.repository;

import com.rowclub.proto.model.Boat;

import java.sql.SQLException;
import java.util.List;

public interface IBoatRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<Boat> readAllBoats();

    int getBoatListSize();

    void createBoat(String name,String type,String status,int seats) throws SQLException;

    Boat readBoat(int tripID);

    void updateBoat(int boatID,String name,String type, String status, int seats);

    void deleteBoat(int boatID);
}