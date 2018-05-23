package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.rowclub.proto.model.Warning;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IUtilitiesRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll

    List<Member> findAllMateys();

    List<BoatTrip> seasonTrips(int memberId, int seasonId) throws SQLException;

    double seasonDistance(int memberId, int seasonId) throws SQLException;

    List<Member> membersOnTrip(int tripId) throws SQLException;

    List<Integer> membersOnTripArray() throws SQLException;

    int countMembersOnTrip(List<Member> tripList);

    //Laver en int (Antal min) til en string (hh:mm)
    String calTime(int time);

    //Laver en string om til et tlf nr. uden +45 først og uden chars der ikke er tal
    String tlfCheck(String tlf);

    int ghostBusterStringToInt (String boo);

    String ghostBusterStringToNum (String boo);

    List<Warning> adminAccessWarnings (int memberId) throws SQLException;

    //oprettelse af bådtur links

    void createMemberOnBoatList ();

    void addMemberOnBoatList (int memberId) throws SQLException;

    void deleteMemberOnBoatList (int memberId) throws SQLException;

    void assignBoatTripLinks (int boatTripID) throws SQLException;




}
