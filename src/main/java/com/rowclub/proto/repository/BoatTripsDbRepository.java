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

@Repository
public class BoatTripsDbRepository implements IBoatTripRepository {
    public List<BoatTrip> BoatTripList;
    private ResultSet BoatTripQuery;
    private int BoatTripOnWaterCount = 0;

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

    public int getBoatTripListSize() { return BoatTripList.size(); }

    public int getBoatTripOnWaterCount() { return BoatTripOnWaterCount; }

    @Override
    public List<BoatTrip> readAllBoatTrips() { return BoatTripList; }

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

    @Override
    public BoatTrip readBoatTrip(int tripID) {
        return BoatTripList.get(tripID-1);
    }

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

    @Override
    public void updateBoatTrip(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException {
        // (int boatID, String distance, String estDuration, String location, String datestamp, int completionTime, long timestamp, String whattodo, String[] guests)

        /*int index = 0;
        ResultSet rs;

        int AdminAdd;
        if (Admin) {AdminAdd = 1;} else {AdminAdd = 0;}
        int MateyAdd;
        if (Matey) {MateyAdd = 1;} else {MateyAdd = 0;}

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        String updateMember = "UPDATE " + DatabaseController.DBprefix + "Member SET ";
        Member member = MemberList.get(index);

        FirstName = DBconn.res(FirstName);
        LastName = DBconn.res(LastName);
        DoB = DBconn.res(DoB);
        RegDate = DBconn.res(RegDate);
        Phone = DBconn.res(Phone);
        Type = DBconn.res(Type);
        PhotoRef = DBconn.res(PhotoRef);

        if(FirstName != ""){

            member.setFirstName(FirstName);
            updateMember = updateMember + "FirstName ='" +FirstName+ "',";
        }

        if(LastName != ""){

            member.setLastName(LastName);
            updateMember = updateMember + "LastName ='" +LastName+ "',";
        }

        if(DoB != ""){
            Date dateDoB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(DoB);
            member.setDoB(dateDoB);
            updateMember = updateMember + "DoB ='" +DoB+ "',";

        }
        if(RegDate != ""){
            Date dateReg = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(RegDate);
            member.setRegDate(dateReg);
            updateMember = updateMember + "RegDate ='" +RegDate+ "',";

        }
        if(Phone != ""){

            member.setPhone(Phone);
            updateMember = updateMember + "Phone ='" +Phone+ "',";

        }

        if(Admin != null){

            member.setAdmin(Admin);
            updateMember = updateMember + "Admin ='" +AdminAdd+ "',";

        }
        if(Matey != null){

            member.setMate(Matey);
            updateMember = updateMember + "Matey ='" +MateyAdd+ "',";

        }
        if(Type != ""){

            member.setType(Type);
            updateMember = updateMember + "Type ='" +Type+ "',";

        }
        if(PhotoRef != ""){

            member.setPhotoRef(PhotoRef);
            updateMember = updateMember + "PhotoRef ='" +PhotoRef+ "',";

        }

        updateMember = updateMember.substring(0,updateMember.length()-1);
        updateMember = updateMember + " WHERE memberId = " + memberId;

        DBconn.dbUpdate(updateMember);

        MemberList.set(index, member);
        */
    }

    @Override
    public void deleteBoatTrip(int tripID) {
        BoatTripList.remove(tripID);
    }
}