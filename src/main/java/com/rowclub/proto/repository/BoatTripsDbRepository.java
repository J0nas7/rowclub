package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class BoatTripsDbRepository implements IBoatTripRepository {
    private List<BoatTrip> BoatTripList;
    private ResultSet BoatTripQuery;
    private int BoatTripOnWaterCount = 0;

    public BoatTripsDbRepository () throws SQLException {
        BoatTripList = new ArrayList<>();
        String BoatTripSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTrips";
        BoatTripQuery = DBconn.dbQuery(BoatTripSql);
        while (BoatTripQuery.next()) {
            BoatTripList.add(new BoatTrip(
                    BoatTripQuery.getInt("BoatTrip_ID"),
                    BoatTripQuery.getInt("Boat_ID"),
                    BoatTripQuery.getDouble("BoatTrip_Distance"),
                    BoatTripQuery.getInt("BoatTrip_EstDuration"),
                    BoatTripQuery.getString("BoatTrip_Location"),
                    BoatTripQuery.getDate("BoatTrip_Datestamp"),
                    BoatTripQuery.getInt("BoatTrip_SeasonID"),
                    BoatTripQuery.getInt("BoatTrip_CompletionTime"),
                    BoatTripQuery.getInt("BoatTrip_Timestamp")
            ));
            if (BoatTripQuery.getInt("BoatTrip_CompletionTime") == 0) {
                BoatTripOnWaterCount++;
            }
        }
    }

    public int getBoatTripListSize() { return BoatTripList.size(); }

    public int getBoatTripOnWaterCount() { return BoatTripOnWaterCount; }

    @Override
    public List<BoatTrip> readAllBoatTrips() { return BoatTripList; }

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