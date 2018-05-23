package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IMemberRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<Member> readAllMembers();

    int getMemberListSize();

    void createMember(String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException;

    void createGuest(String FirstName, String LastName) throws SQLException, ParseException;

    Member readMembers(int arrayId);

    void updateMember(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone,Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException;

    void deleteMember(int memberId) throws SQLException;
}
