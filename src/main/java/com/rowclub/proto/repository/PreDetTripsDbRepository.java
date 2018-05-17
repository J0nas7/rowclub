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
    public static List<PreDetTrips> PreDetTripsList = new ArrayList<>();
    private ResultSet PreDetTripsQuery;


    public PreDetTripsDbRepository() throws SQLException {

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
                location+"',"+
                distance+","+
                preEstDuration;
        String preDetTripsStatement = "insert into "+DatabaseController.DBprefix+"PredeterminedTrips values ("+statement+");";
        DBconn.dbUpdate(preDetTripsStatement);

        rs = DBconn.dbQuery("SELECT Max(preID) FROM "+DatabaseController.DBprefix+"PredeterminedTrips;");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        PreDetTrips preDetTrips = new PreDetTrips(id,location,distance,preEstDuration);
        System.out.println(id);
        preDetTrips.setPreID(PreDetTripsList.size()+1);
        PreDetTripsList.add(preDetTrips);
    }

    @Override
    public PreDetTrips readPreDetTrips(int preID) {
        return PreDetTripsList.get(preID-1);
    }

    @Override
    public void updatePreDetTrips(int preID,String location,Double distance, int preEstDuration, int seats) {

        String statement = "UPDATE "+DatabaseController.DBprefix+"PredeterminedTrips SET ";

        PreDetTrips preDetTrips = PreDetTripsList.get(preID-1);

        if(location != ""){
            preDetTrips.setLocation(location);
            statement = statement + "Name = '"+location+"',";

        }
        if(distance != 0){
            preDetTrips.setDistance(distance);
            statement = statement + "Type = '"+distance+"',";
        }
        if(preEstDuration != 0){
            preDetTrips.setPreEstDuration(preEstDuration);
            statement = statement + "Status = '"+preEstDuration+"',";
        }

        statement = statement.substring(0,statement.length()-1);

        statement = statement + " WHERE preID = " + preID;

        DBconn.dbUpdate(statement);

        PreDetTripsList.set(preDetTrips.getPreID()-1, preDetTrips);

    }

    @Override
    public void deletePreDetTrips(int preID) {
        PreDetTripsList.remove(preID-1);
        DBconn.dbUpdate("DELETE FROM "+DatabaseController.DBprefix+"PredeterminedTrips WHERE preID ="+preID);
    }
}