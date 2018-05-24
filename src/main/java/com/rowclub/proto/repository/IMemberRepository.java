package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

//Jack har sat metoder og variabler op
//Jacob har lavet mange redigeringer
public interface IMemberRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<Member> readAllMembers();

    void reduceGuestName() throws SQLException, ParseException;

    int getMemberListSize();

    void createMember(String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException;

    // INTERFACE METHOD BY JONAS
    void createGuest(String FirstName, String LastName) throws SQLException, ParseException;

    Member readMembers(int arrayId);

    void updateMember(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone,Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException;

    void deleteMember(int memberId) throws SQLException;
}
