package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.Boat;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

//Jacob har lavet små redigeringer
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

        name = DBconn.res(name);
        type = DBconn.res(type);
        status = DBconn.res(status);

        String statement = "default"+",'"+
                name+"','"+
                type+"','"+
                status+"',"+
                seats;
        String boatStatement = "insert into "+DatabaseController.DBprefix+"Boat values ("+statement+");";
        DBconn.dbUpdate(boatStatement);

        rs = DBconn.dbQuery("SELECT Max(boatID) FROM "+DatabaseController.DBprefix+"Boat;");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        Boat boat = new Boat(id,name,type,status,seats);
        boat.setBoatID(BoatList.size()+1);
        BoatList.add(boat);
    }

    @Override
    public Boat readBoat(int boatID) {
        return BoatList.get(boatID-1);
    }

    @Override
    public void updateBoat(int boatID,String name,String type, String status, int seats) throws SQLException {

        int index = 0;
        ResultSet rs;

        name = DBconn.res(name);
        type = DBconn.res(type);
        status = DBconn.res(status);

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Boat WHERE BoatID <" + boatID + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        String statement = "UPDATE "+DatabaseController.DBprefix+"Boat SET ";

        Boat boat = BoatList.get(index);

        if(name != ""){
            boat.setName(name);
            statement = statement + "Name = '"+name+"',";

        }
        if(type != ""){
            boat.setType(type);
            statement = statement + "Type = '"+type+"',";
        }
        if(status!= ""){
            boat.setStatus(status);
            statement = statement + "Status = '"+status+"',";
        }
        if(seats!= 0){
            boat.setSeats(seats);
            statement = statement + "Seats = '"+seats+"',";
        }

        statement = statement.substring(0,statement.length()-1);

        statement = statement + " WHERE boatID = " + boatID;

        DBconn.dbUpdate(statement);

        BoatList.set(index,boat);

    }

    @Override
    public void deleteBoat(int boatID) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Boat WHERE BoatID <" + boatID + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        BoatList.remove(index);
        DBconn.dbUpdate("DELETE FROM "+DatabaseController.DBprefix+"Boat WHERE boatID ="+boatID);
    }
}