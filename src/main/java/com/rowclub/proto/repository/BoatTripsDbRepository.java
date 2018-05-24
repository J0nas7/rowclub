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
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.rowclub.proto.controller.ProtocolController.DBconn;
import static java.sql.Types.NULL;

//Jacob har lavet redigeringer
@Repository
public class BoatTripsDbRepository implements IBoatTripRepository {
    public List<BoatTrip> BoatTripList;
    private ResultSet BoatTripQuery;
    private int BoatTripOnWaterCount = 0;

    // METHOD BY JONAS
    public BoatTripsDbRepository () throws SQLException {
        BoatTripList = new ArrayList<>();
        String BoatTripSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTrips ORDER BY BoatTrip_ID ASC";
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

    // METHOD BY JONAS
    public int getBoatTripListSize() { return BoatTripList.size(); }

    // METHOD BY JONAS
    public int getBoatTripOnWaterCount() { return BoatTripOnWaterCount; }

    // METHOD BY JONAS
    @Override
    public List<BoatTrip> readAllBoatTrips() { return BoatTripList; }

    // METHOD BY JONAS
    @Override
    public void createBoatTrip(int boatID, String distance, String estDuration, String location, String datestamp, int completionTime, long timestamp, String whattodo, String[] guests) throws ParseException {
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
        System.out.println(createBoatTripSql);
        DBconn.dbUpdate(createBoatTripSql);
    }

    // METHOD BY JONAS
    @Override
    public BoatTrip readBoatTrip(int tripID) {
        return BoatTripList.get(tripID-1);
    }

    // METHOD BY JONAS
    @Override
    public void actionBoatTrip(int tripID, String action) {
        ResultSet rs;
        String updateBoatTrip = "UPDATE "+DatabaseController.DBprefix+"BoatTrips";
        BoatTrip boattrip = BoatTripList.get(tripID-1);

        if (action.equalsIgnoreCase("ci")) {
            boattrip.setOnWater(true);
            updateBoatTrip += " SET BoatTrip_OnWater='1'";
        } else if (action.equalsIgnoreCase("co")) {
            boattrip.setOnWater(false);
            boattrip.setCompletionTime(Math.toIntExact(Instant.now().getEpochSecond()));
            updateBoatTrip += " SET BoatTrip_OnWater='0', BoatTrip_CompletionTime='"+Instant.now().getEpochSecond()+"'";
        }

        updateBoatTrip += " WHERE BoatTrip_ID='"+tripID+"' LIMIT 1";
        DBconn.dbUpdate(updateBoatTrip);
        BoatTripList.set(tripID-1, boattrip);
    }

    // METHOD BY JONAS
    @Override
    public void updateBoatTrip(int tripID, String datestamp, String distance, String estDuration, String location, int passengers) {
        double Ddistance = Double.parseDouble(distance);
        int IestDuration = Integer.parseInt(estDuration);
        //int Itimestamp = Math.toIntExact(datestamp);

        String updateBoatTripSql = "UPDATE "+DatabaseController.DBprefix+"BoatTrips SET ";
        BoatTrip boattrip = BoatTripList.get(tripID-1);

        datestamp = DBconn.res(datestamp);
        distance = DBconn.res(distance);
        estDuration = DBconn.res(estDuration);
        location = DBconn.res(location);

        if (datestamp != "") {
            boattrip.setDatestamp(datestamp);
            updateBoatTripSql += "BoatTrip_Datestamp='"+datestamp+"', ";
        }

        if (distance != "") {
            boattrip.setDistance(Ddistance);
            updateBoatTripSql += "BoatTrip_Distance='"+Ddistance+"',";
        }

        if (estDuration != "") {
            boattrip.setEstDuration(IestDuration);
            updateBoatTripSql += "BoatTrip_EstDuration='"+estDuration+"', ";
        }

        if (location != "") {
            boattrip.setLocation(location);
            updateBoatTripSql += "BoatTrip_Location='"+location+"', ";
        }

        boattrip.setPassengers(passengers);
        updateBoatTripSql += "BoatTrip_Passengers='"+passengers+"' ";

        updateBoatTripSql += "WHERE BoatTrip_ID='"+tripID+"'";

        DBconn.dbUpdate(updateBoatTripSql);
        BoatTripList.set(tripID-1, boattrip);
    }

    // METHOD BY JONAS
    @Override
    public void deleteBoatTrip(int tripID) {
        BoatTripList.remove(tripID);
    }

}