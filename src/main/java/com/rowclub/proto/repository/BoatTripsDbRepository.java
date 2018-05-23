package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.controller.ProtocolController;
import com.rowclub.proto.model.BoatTrip;
import javafx.beans.property.SimpleListProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
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
                    BoatTripQuery.getString("BoatTrip_Datestamp"),
                    BoatTripQuery.getDate("BoatTrip_Datestamp"),
                    BoatTripQuery.getInt("BoatTrip_SeasonID"),
                    BoatTripQuery.getBoolean("BoatTrip_OnWater"),
                    BoatTripQuery.getInt("BoatTrip_CompletionTime"),
                    BoatTripQuery.getInt("BoatTrip_Timestamp"),
                    BoatTripQuery.getInt("BoatTrip_Passengers")
            ));
            if (BoatTripQuery.getBoolean("BoatTrip_OnWater")) {
                BoatTripOnWaterCount++;
            }
        }
    }

    public int getBoatTripListSize() { return BoatTripList.size(); }

    public int getBoatTripOnWaterCount() { return BoatTripOnWaterCount; }

    @Override
    public List<BoatTrip> readAllBoatTrips() { return BoatTripList; }

    @Override
    public void createBoatTrip(int boatTripID, int boatID, String distance, String estDuration, String location, String datestamp, int completionTime, long timestamp, String whattodo, String[] guests) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(datestamp);

        double Ddistance = Double.parseDouble(distance);
        int IestDuration = Integer.parseInt(estDuration);
        int Itimestamp = Math.toIntExact(timestamp);

        int seasonID = 2;

        boolean OnWater = false;
        if (whattodo.equalsIgnoreCase("Opret tur og luk")) {
            OnWater = false;
        } else if (whattodo.equalsIgnoreCase("Opret tur, check ind og luk")) {
            OnWater = true;
            BoatTripOnWaterCount++;
        }

        int Passengers = 1;
        for (int i = 0; i < guests.length; i++) {
            if (!guests[i].trim().isEmpty()) {
                Passengers++;
            }
        }

        location = DBconn.res(location);
        datestamp = DBconn.res(datestamp);

        BoatTripList.add(new BoatTrip(this.getBoatTripListSize()+1, boatID, Ddistance, IestDuration, location,datestamp, date, seasonID, OnWater, completionTime, Itimestamp, Passengers));
        String createBoatTripSql =  "INSERT INTO "+DatabaseController.DBprefix+"BoatTrips (Boat_ID, BoatTrip_Distance, BoatTrip_EstDuration, BoatTrip_Location, BoatTrip_Datestamp, BoatTrip_SeasonID, BoatTrip_OnWater, BoatTrip_CompletionTime, BoatTrip_Timestamp, BoatTrip_Passengers) " +
                                    "VALUES ('"+boatID+"', '"+Ddistance+"', '"+IestDuration+"', '"+location+"', '"+datestamp+"', '"+seasonID+"', "+OnWater+", 0, '"+Itimestamp+"', '"+Passengers+"');";
        DBconn.dbUpdate(createBoatTripSql);
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