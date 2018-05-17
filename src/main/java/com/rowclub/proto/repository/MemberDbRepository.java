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
    private ResultSet MemberQuery;
    public static List<Member> MemberList = new ArrayList<>();


    public MemberDbRepository() throws SQLException {
        Member = new ArrayList<>();
        String MemberSql = "SELECT * FROM " + DatabaseController.DBprefix + "Member";
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
    }


    public int getMemberListSize() {
        return MemberList.size();
    }

    @Override
    public List<Member> readAllMembers() {
        return MemberList;
    }

    @Override
    public void createMember(String FirstName, String LastName, Date DoB, Date RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) {

        String memberValues =
                "'default" + "','"
                        + FirstName + "','"
                        + LastName + "','"
                        + DoB + "','"
                        + RegDate + "','"
                        + Phone + "','"
                        + Admin + "','"
                        + Matey + "','"
                        + Type + "','"
                        + PhotoRef + "'";


        String insertMember = "INSERT INTO " + DatabaseController.DBprefix + "Member" + " VALUES " + (memberValues);

        System.out.println(insertMember);

        @Override
        public List<Member> readAllMembers () {
            return Member;
        }

        @Override
        public void createMember (Member member){
            member.setMemberID(MemberList.size() + 1);
            MemberList.add(member);
        }

        @Override
        public Member readMembers (int memberId){
            return Member.get(memberId - 1);
        }

        @Override
        public void updateMember (Member member){
            MemberList.set(member.getMemberID() - 1, member);
        }

        @Override
        public void deleteMember (int memberId){
            Member.remove(memberId - 1);
        }
    }
}

