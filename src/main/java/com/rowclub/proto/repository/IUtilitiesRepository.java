package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IUtilitiesRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll

    public  Member findMember(int memberId) throws SQLException;

    public  void updateMember(int memberID, String firstName, String lastName, Date doB, Date regDate,
                                    String phone, boolean admin, boolean mate, String type, String photoRef) throws SQLException;

    public List<Member> findAllMateys();

    public  List<BoatTrip> seasonTrips(int memberId, int seasonId) throws SQLException;

    public  double seasonDistance(int memberId, int seasonId) throws SQLException;


    public  List<Member> membersOnTrip(int tripId) throws SQLException;


    //Laver en int (Antal min) til en string (hh:mm)
    public String calTime(int time);

    //Laver en string om til et tlf nr. uden +45 først og uden chars der ikke er tal
    public String tlfCheck(String tlf);

    public  int ghostBusterStringToInt (String boo);

    public  String ghostBusterStringToNum (String boo);
}
