package com.rowclub.proto.model;

import com.rowclub.proto.controller.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

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

    public static void updateMember(int memberID, String firstName, String lastName, Date doB, Date regDate,
    String phone, boolean admin, boolean mate, String type, String photoRef) throws SQLException {

        int newAdmin;
        int newMate;

        if (firstName == null) {
            firstName = Utilities.findMember(memberID).getFirstName(); }

        if (lastName == null) {
            lastName = Utilities.findMember(memberID).getLastName(); }

        if (doB == null) {
            doB = Utilities.findMember(memberID).getDoB(); }

        if (regDate == null) {
            regDate = Utilities.findMember(memberID).getRegDate(); }

        if (phone == null) {
            phone = Utilities.findMember(memberID).getPhone(); }

        if (admin == true) {newAdmin = 1;} else {newAdmin = 0;}

        if (mate == true) {newMate = 1;} else {newMate = 0;}

        if (type == null) {
            type = Utilities.findMember(memberID).getType(); }

        if (photoRef == null) {
            photoRef = Utilities.findMember(memberID).getPhotoRef(); }

        String statement = "UPDATE " + DatabaseController.DBprefix + "Member SET "
                + "FirstName = '" + firstName + "', "
                + "LastName = '" + lastName + "', "
                + "DoB = '" + doB + "', "
                + "RegDate = '" + regDate + "', "
                + "Phone = '" + phone + "', "
                + "Admin = '" + newAdmin + "', "
                + "Matey = '" + newMate + "', "
                + "Type = '" + type + "', "
                + "PhotoRef = '" + photoRef
                + "' WHERE MemberID = " + memberID;

        DBconn.dbUpdate(statement);
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
