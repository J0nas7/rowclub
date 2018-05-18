package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.controller.ProtocolController;
import com.rowclub.proto.model.BoatTrip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.rowclub.proto.controller.ProtocolController.DBconn;
import static java.sql.Types.NULL;

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

    public void createBoatTrip(BoatTrip boattrip, String PostParam) {
        if (PostParam.equalsIgnoreCase("Opret tur og luk")) {
            int CompletionTime = 1;
        } else if (PostParam.equalsIgnoreCase("Opret tur, check ind og luk")) {
            int CompletionTime = NULL;
        }

        String createBoatTripSql =  "INSERT INTO "+DatabaseController.DBprefix+"BoatTrips (Boat_ID, BoatTrip_Distance, BoatTrip_EstDuration, BoatTrip_Location, BoatTrip_Datestamp, BoatTrip_SeasonID, BoatTrip_CompletionTime, BoatTrip_Timestamp) " +
                                    " VALUES ('3', '10', '120', 'En længere tur rundt i området ved havnen', '2018-05-13', '2', '115', '733');";
        DBconn.dbUpdate(createBoatTripSql);
    }

    public String createBoatTrip(int boatTripID, int boatID, String distance, String estDuration, String location, String datestamp, int seasonID, int completionTime, long timestamp) throws ParseException {
        // (int boatTripID, int boatID, double distance, int estDuration, String location, Date datestamp, int seasonID, int completionTime, int timestamp)
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = format.parse(datestamp);
        BoatTripList.add(new BoatTrip(this.getBoatTripListSize()+1, 27, Double.parseDouble(distance), Integer.parseInt(estDuration), location, date, seasonID, completionTime, Math.toIntExact(timestamp)));
        return "/welcome?c";
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