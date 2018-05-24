package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.BoatTripLink;
import com.rowclub.proto.model.Member;
import com.rowclub.proto.model.Warning;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;
import static com.rowclub.proto.repository.MemberDbRepository.MemberList;
import static com.rowclub.proto.repository.WarningDbRepository.WarningList;
import static com.rowclub.proto.repository.BoatTripLinkDbRepository.BoatTripLinkList;

//Jacob
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

    //Jacob
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
                    BoatTripQuery.getString("BoatTrip_Datestamp"),
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

    //Jacob
    public  double seasonDistance(int memberId, int seasonId) throws SQLException {

        double distance = 0;
        List<BoatTrip> SeasonList = seasonTrips(memberId, seasonId);

        for (int i = 0; i < SeasonList.size(); i++) {
            distance = distance + SeasonList.get(i).getDistance();
        }
        return distance;
    }

    //Jacob
    public List<Member> membersOnTrip(int tripId) throws SQLException {

        List<Member> MemberList;
        ResultSet MemberQuery;

        MemberList = new ArrayList<>();
        String MemberSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTripLink" + " INNER JOIN "+DatabaseController.DBprefix+"Member ON fkMemberID = MemberID" +
                " INNER JOIN "+DatabaseController.DBprefix+"BoatTrips ON fkBoatTripID = BoatTrip_ID" + " WHERE "+DatabaseController.DBprefix+"BoatTrips.BoatTrip_ID = " + tripId;
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

    // METHOD BY JONAS
    public void deleteMembersOnTrip(int tripId) throws SQLException {

        List<Member> MemberList;
        ResultSet MemberQuery;

        MemberList = new ArrayList<>();
        String MemberSql = "SELECT * FROM " + DatabaseController.DBprefix + "BoatTripLink" + " INNER JOIN " + DatabaseController.DBprefix + "Member ON fkMemberID = MemberID" +
                " INNER JOIN " + DatabaseController.DBprefix + "BoatTrips ON fkBoatTripID = BoatTrip_ID" + " WHERE " + DatabaseController.DBprefix + "BoatTrips.BoatTrip_ID = " + tripId;
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

        for (Member ontrip : MemberList) {
            if (!ontrip.isMate()) {
                String DeleteTripLink = "DELETE FROM " + DatabaseController.DBprefix + "BoatTripLink WHERE fkBoatTripID='" + tripId + "' AND fkMemberID='" + ontrip.getMemberID() + "' LIMIT 1";
                DBconn.dbUpdate(DeleteTripLink);
            }
        }
    }

    //Jacob
    public List<Integer> membersOnTripArray() throws SQLException {

        List<Integer> amountList = new ArrayList<>();

        BoatTripsDbRepository boattripdb = new BoatTripsDbRepository();

        for (int i = 0; i < boattripdb.getBoatTripListSize(); i++) {

            amountList.add(countMembersOnTrip(membersOnTrip(boattripdb.BoatTripList.get(i).getBoatTripID())));
        }

        return amountList;
    }

    //Jacob
    public int countMembersOnTrip(List<Member> tripList) {
        //brug den retunerede liste fra membersontrip som parameter til denne her metode for at finde ud af hvor mange der er på en tur
        return tripList.size();
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

    public int ghostBusterStringToInt (String boo) {
        int busted = Integer.parseInt(boo.replaceAll("[^\\d]",""));
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }

    public String ghostBusterStringToNum (String boo) {
        String busted = boo.replaceAll("[^\\d]","");
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }

    //Jacob
    public List<Warning> adminAccessWarnings (int memberId) throws SQLException {
        //checks if a member is an administrator and, if they are, return the warning list
        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        Member member = MemberList.get(index);

        if (member.isAdmin()) {
            return WarningList;
        }
        return null;
    }

    //oprettelse af bådtur links
    //Jacob
    public List<Member> memberOnBoatList;

    //Jacob
    public void createMemberOnBoatList () {

        memberOnBoatList = new ArrayList<>();
        memberOnBoatList.clear();
    }

    //Jacob
    public void addMemberOnBoatList (int memberId) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        Member member = MemberList.get(index);
        memberOnBoatList.add(member);

    }

    //Jacob
    public void deleteMemberOnBoatList (int memberId) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        memberOnBoatList.remove(index);

    }

    //Jacob
    public void assignBoatTripLinks (int boatTripID) throws SQLException {

        for (int i = 0; i < memberOnBoatList.size(); i++) {

            BoatTripLinkDbRepository temp = new BoatTripLinkDbRepository();

            temp.createBoatTripLink(memberOnBoatList.get(i).getMemberID(), boatTripID);
        }

    }







}