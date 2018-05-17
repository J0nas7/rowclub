package com.rowclub.proto.model;

import com.rowclub.proto.controller.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

import static com.rowclub.proto.repository.MemberDbRepository.MemberList;

public class Utilities {
    //denne klasse skal indeholde metoder til udregning af data

    public static Member findMember(int memberId) throws SQLException {

        //Finder en medlem i databasen og returnerer den
        Member member = null;
        ResultSet MemberQuery;

        String MemberSql = "SELECT * FROM "+DatabaseController.DBprefix+"Member" + " WHERE MemberID = " + memberId;
        MemberQuery = DBconn.dbQuery(MemberSql);
        if (MemberQuery.next()) {
            member = new Member(MemberQuery.getInt("MemberID"),
                    MemberQuery.getString("FirstName"),
                    MemberQuery.getString("LastName"),
                    MemberQuery.getDate("DoB"),
                    MemberQuery.getDate("RegDate"),
                    MemberQuery.getString("Phone"),
                    MemberQuery.getBoolean("Admin"),
                    MemberQuery.getBoolean("Matey"),
                    MemberQuery.getString("Type"),
                    MemberQuery.getString("PhotoRef"));
        }
        return member;
    }

    public List<Member> findAllMateys() {
        List<Member> MateyList = new ArrayList<>();
        for (Member matey: MemberList){
            if (matey.isMate()) {
                MateyList.add(matey);
            }
        }
        return MateyList;
    }

    public static List<BoatTrip> seasonTrips(int memberId, int seasonId) throws SQLException {

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
                    BoatTripQuery.getInt("BoatTrip_CompletionTime"),
                    BoatTripQuery.getInt("BoatTrip_Timestamp")
            ));
        }
        return BoatTripList;
    }

    public static double seasonDistance(int memberId, int seasonId) throws  SQLException {

        double distance = 0;
        List<BoatTrip> SeasonList = seasonTrips(memberId, seasonId);

        for (int i = 0; i < SeasonList.size(); i++) {
            distance = distance + SeasonList.get(i).getDistance();
        }
        return distance;
    }

    public static List<Member> membersOnTrip(int tripId) throws SQLException {

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
                    MemberQuery.getDate("DoB"),
                    MemberQuery.getDate("RegDate"),
                    MemberQuery.getString("Phone"),
                    MemberQuery.getBoolean("Admin"),
                    MemberQuery.getBoolean("Matey"),
                    MemberQuery.getString("Type"),
                    MemberQuery.getString("PhotoRef")
            ));

        }
        return MemberList;
    }


}
