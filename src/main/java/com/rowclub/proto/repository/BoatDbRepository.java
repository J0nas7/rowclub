package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.Boat;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class BoatDbRepository implements IBoatRepository {
    private List<Boat> BoatList;
    private ResultSet BoatQuery;

    public BoatDbRepository() throws SQLException {
        BoatList = new ArrayList<>();
        String BoatSql = "SELECT * FROM "+DatabaseController.DBprefix+"Boat";
        BoatQuery = DBconn.dbQuery(BoatSql);
        while (BoatQuery.next()) {
            BoatList.add(new Boat(
                    BoatQuery.getInt("BoatID"),
                    BoatQuery.getString("Name"),
                    BoatQuery.getString("Type"),
                    BoatQuery.getString("Status"),
                    BoatQuery.getInt("Seats")
            ));
        }
    }

    public int getBoatListSize() { return BoatList.size(); }

    @Override
    public List<Boat> readAllBoats() { return BoatList; }

    @Override
    public void createBoat(String name,String type,String status,int seats) throws SQLException {
        int id = 0;
        ResultSet rs;
        String statement = "default"+",'"+
                name+"','"+
                type+"','"+
                status+"',"+
                seats;
        String boatStatement = "insert into "+DatabaseController.DBprefix+"Boat values ("+statement+");";
        DBconn.dbUpdate(boatStatement);

        //rs = DBconn.dbQuery("SELECT Max(boatID) FROM "+DatabaseController.DBprefix+"Boat;");



        Boat boat = new Boat(id,name,type,status,seats);
        System.out.println(id);
        boat.setBoatID(BoatList.size()+1);
        BoatList.add(boat);
    }

    @Override
    public Boat readBoat(int boatID) {
        return BoatList.get(boatID-1);
    }

    @Override
    public void updateBoat(Boat boat) { BoatList.set(boat.getBoatID()-1, boat); }

    @Override
    public void deleteBoat(int tripID) {
        BoatList.remove(tripID-1);
    }
}