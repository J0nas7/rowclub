package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;
import static com.rowclub.proto.repository.MemberDbRepository.MemberList;

@Repository
public class UtilitiesDbRepository implements IUtilitiesRepository {

    //denne klasse skal indeholde metoder til udregning af data

    public List<Member> findAllMateys() {
        List<Member> MateyList = new ArrayList<>();
        for (Member matey: MemberList){
            if (matey.isMate()) {
                MateyList.add(matey);
            }
        }
        return MateyList;

    }

    public  List<BoatTrip> seasonTrips(int memberId, int seasonId) throws SQLException {

        List<BoatTrip> BoatTripList;
        ResultSet BoatTripQuery;

        BoatTripList = new ArrayList<>();
        String BoatTripSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTripLink" + " INNER JOIN Protocol_Member ON fkMemberID = MemberID" +
                " INNER JOIN Protocol_BoatTrips ON fkBoatTripID = BoatTrip_ID" + " WHERE MemberID = " + memberId
                + " && BoatTrip_SeasonID = " + seasonId;
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
                    BoatTripQuery.getBoolean("BoatTrip_OnWater"),
                    BoatTripQuery.getInt("BoatTrip_CompletionTime"),
                    BoatTripQuery.getInt("BoatTrip_Timestamp"),
                    BoatTripQuery.getInt("BoatTrip_Passengers")
            ));
        }

        return BoatTripList;
    }

    public  double seasonDistance(int memberId, int seasonId) throws SQLException {

        double distance = 0;
        List<BoatTrip> SeasonList = seasonTrips(memberId, seasonId);

        for (int i = 0; i < SeasonList.size(); i++) {
            distance = distance + SeasonList.get(i).getDistance();
        }
        return distance;
    }

    public List<Member> membersOnTrip(int tripId) throws SQLException {

        List<Member> MemberList;
        ResultSet MemberQuery;

        MemberList = new ArrayList<>();
        String MemberSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTripLink" + " INNER JOIN Protocol_Member ON fkMemberID = MemberID" +
                " INNER JOIN Protocol_BoatTrips ON fkBoatTripID = BoatTrip_ID" + " WHERE BoatTrip_ID = " + tripId;
        MemberQuery = DBconn.dbQuery(MemberSql);
        while (MemberQuery.next()) {
            MemberList.add(new Member(
                    MemberQuery.getInt("MemberID"),
                    MemberQuery.getString("FirstName"),
                    MemberQuery.getString("LastName"),
                    MemberQuery.getString("DoB"),
                    MemberQuery.getString("RegDate"),
                    MemberQuery.getString("Phone"),
                    MemberQuery.getBoolean("Admin"),
                    MemberQuery.getBoolean("Matey"),
                    MemberQuery.getString("Type"),
                    MemberQuery.getString("PhotoRef")
            ));

        }
        return MemberList;
    }

    //Laver en int (Antal min) til en string (hh:mm)
    public String calTime(int time){
        int hour = time / 60;
        int min = time % 60;
        String string;
        if(min < 10){
            string = hour + ":0" + min;
        }
        else if(min < 10){
            string = hour + ":0" + min;
        }
        else{
            string = hour + ":" + min;
        }
        return string;
    }

    //Laver en string om til et tlf nr. uden +45 først og uden chars der ikke er tal
    public String tlfCheck(String tlf){

        String a = tlf.charAt(0) + "" + tlf.charAt(1) + "" + tlf.charAt(2);
        int b = ghostBusterStringToInt(a);
        tlf = ghostBusterStringToNum(tlf);
        //Evt. tilføj advarsel hvis linje oven over har brug for at køre

        System.out.println(a);
        if (b == 45) {
            tlf = tlf.substring(2);
        }



        if (tlf.length() > 8) {
            System.out.println("Too long phone number");
        }


        return tlf;

    }

    public  int ghostBusterStringToInt (String boo) {
        int busted = Integer.parseInt(boo.replaceAll("[^\\d]",""));
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }

    public  String ghostBusterStringToNum (String boo) {
        String busted = boo.replaceAll("[^\\d]","");
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }


}