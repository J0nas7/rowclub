package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class MemberDbRepository implements IMemberRepository {
    private List<Member> Member;
    private ResultSet MemberQuery;


    public MemberDbRepository () throws SQLException {
        Member = new ArrayList<>();
        String MemberSql = "SELECT * FROM "+DatabaseController.DBprefix+"Member";
        MemberQuery = DBconn.dbQuery(MemberSql);
        while (MemberQuery.next()) {
            Member.add(new Member(
                    MemberQuery.getInt("MemberID"),
                    MemberQuery.getString("FirstName"),
                    MemberQuery.getString("LastName"),
                    MemberQuery.getDate("DoB"),
                    MemberQuery.getDate("RegDate"),
                    //BoatTripQuery.getDate("BoatTrip_Datestamp"),
                    MemberQuery.getString("Phone"),
                    MemberQuery.getBoolean("Admin"),
                    MemberQuery.getBoolean("Matey"),
                    MemberQuery.getString("Type"),
                    MemberQuery.getString("PhotoRef")
            ));
            }
        }

    public int getMemberListSize() { return Member.size(); }

    @Override
    public List<Member> readAllMembers() { return Member; }

    @Override
    public void createMember(String FirstName, String LastName, Date DoB, Date RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) {

        String memberValues =
                "'default" + "','"
                +FirstName +"','"
                +LastName  +"','"
                +DoB  +"','"
                +RegDate +"','"
                +Phone +"','"
                +Admin +"','"
                +Matey +"','"
                +Type +"','"
                +PhotoRef + "'";


        String insertMember = "INSERT INTO " + DatabaseController.DBprefix + "Member" + " VALUES " + (memberValues);

        System.out.println(insertMember);

        //String insertMemberToDb = ""+DatabaseController.DBprefix+insertMember;
        //System.out.println(insertMemberToDb);
        DBconn.dbUpdate(memberValues);

    }

    @Override
    public Member readMembers(int memberId) {
        return Member.get(memberId-1);
    }

    @Override
    public void updateMember(Member member) { Member.set(member.getMemberID()-1, member); }

    @Override
    public void deleteMember(int memberId) {
        Member.remove(memberId-1);
    }
}

