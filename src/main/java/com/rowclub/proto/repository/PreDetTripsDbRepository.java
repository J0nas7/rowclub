package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.PreDetTrips;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class PreDetTripsDbRepository implements IPreDetTripsRepository {
    private List<PreDetTrips> PreDetTripsList;
    private ResultSet PreDetTripsQuery;

    public PreDetTripsDbRepository() throws SQLException {
        PreDetTripsList = new ArrayList<>();
        String PreDetTripsSql = "SELECT * FROM "+DatabaseController.DBprefix+"PredeterminedTrips";
        PreDetTripsQuery = DBconn.dbQuery(PreDetTripsSql);
        while (PreDetTripsQuery.next()) {
            PreDetTripsList.add(new PreDetTrips(
                    PreDetTripsQuery.getInt("preID"),
                    PreDetTripsQuery.getString("Location"),
                    PreDetTripsQuery.getDouble("distance"),
                    PreDetTripsQuery.getInt("PreEstDuration")
            ));
        }
    }

    public int getPreDetTripsListSize() { return PreDetTripsList.size(); }

    @Override
    public List<PreDetTrips> readAllPreDetTripss() { return PreDetTripsList; }

    @Override
    public void createPreDetTrips(String location,Double distance,int preEstDuration) throws SQLException {
        int id = 0;
        ResultSet rs;
        String statement = "default"+",'"+
                location+"','"+
                distance+"','"+
                preEstDuration;
        String preDetTripsStatement = "insert into "+DatabaseController.DBprefix+"PreDetTrips values ("+statement+");";
        DBconn.dbUpdate(preDetTripsStatement);

        rs = DBconn.dbQuery("SELECT Max(preDetTripsID) FROM "+DatabaseController.DBprefix+"PreDetTrips;");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        PreDetTrips preDetTrips = new PreDetTrips(id,location,distance,preEstDuration);
        System.out.println(id);
        preDetTrips.setPreID(PreDetTripsList.size()+1);
        PreDetTripsList.add(preDetTrips);
    }
/*
    @Override
    public PreDetTrips readPreDetTrips(int preDetTripsID) {
        return PreDetTripsList.get(preDetTripsID-1);
    }

    @Override
    public void updatePreDetTrips(int preDetTripsID,String location,Double distance, String status, int seats) {

        String statement = "UPDATE "+DatabaseController.DBprefix+"PreDetTrips SET ";

        PreDetTrips preDetTrips = PreDetTripsList.get(preDetTripsID-1);

        if(location != ""){
            preDetTrips.setLocation(location);
            statement = statement + "Name = '"+location+"',";

        }
        if(distance != 0){
            preDetTrips.setDistance(distance);
            statement = statement + "Type = '"+distance+"',";
        }
        if(status!= ""){
            preDetTrips.setStatus(status);
            statement = statement + "Status = '"+status+"',";
        }

        statement = statement.substring(0,statement.length()-1);

        statement = statement + " WHERE preDetTripsID = " + preDetTripsID;

        DBconn.dbUpdate(statement);

        PreDetTripsList.set(preDetTrips.getPreDetTripsID()-1, preDetTrips);

    }

    @Override
    public void deletePreDetTrips(int preDetTripsID) {
        PreDetTripsList.remove(preDetTripsID-1);
        DBconn.dbUpdate("DELETE FROM "+DatabaseController.DBprefix+"PredeterminedTrips WHERE preID ="+PredeterminedTrips);
    }*/
}