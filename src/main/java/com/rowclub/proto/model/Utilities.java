package com.rowclub.proto.model;

import com.rowclub.proto.controller.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

public class Utilities {
    //denne klasse skal indeholde metoder til udregning af data

    public int seasonDistance(int seasonId, int memberId) {

        //i need a sql database to actually work on this method REEEEEEEEEEEEEEEEEEEE-

        return 1;
    }

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


}
