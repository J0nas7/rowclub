package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
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

    }

    @Override
    public Member readMembers(int memberId) {

        return MemberList.get(memberId-1);
    }

    @Override
    public void updateMember(int memberId, String FirstName, String LastName, Date DoB, Date RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) {

        String updateMember = "UPDATE " + DatabaseController.DBprefix + "Member SET ";

        Member member = MemberList.get(memberId-1);

        if(FirstName != ""){

            member.setFirstName(FirstName);
            updateMember = updateMember + "FirstName ='" +FirstName+ "',";
        }

        if(LastName != ""){

            member.setLastName(LastName);
            updateMember = updateMember + "LastName ='" +LastName+ "',";
        }

        if(DoB != null){

            member.setDoB(DoB);
            updateMember = updateMember + "DoB ='" +DoB+ "',";

        }
        if(RegDate != null){

            member.setRegDate(RegDate);
            updateMember = updateMember + "RegDate ='" +RegDate+ "',";

        }
        if(Phone != ""){

            member.setPhone(Phone);
            updateMember = updateMember + "Phone ='" +Phone+ "',";

        }

        if(Admin != null){

            member.setAdmin(Admin);
            updateMember = updateMember + "Admin ='" +Admin+ "',";

        }
        if(Matey != null){

            member.setMate(Matey);
            updateMember = updateMember + "Matey ='" +Matey+ "',";

        }
        if(Type != ""){

           member.setType(Type);
           updateMember = updateMember + "Type ='" +Type+ "',";

        }
        if(PhotoRef != ""){

            member.setPhotoRef(PhotoRef);
            updateMember = updateMember + "PhotoRef ='" +PhotoRef+ "',";

        }

        updateMember = updateMember.substring(0,updateMember.length()-1);

        updateMember = updateMember + " WHERE memberId = " + memberId;

        DBconn.dbUpdate(updateMember);

        MemberList.set(member.getMemberID()-1, member);
    }

    @Override
    public void deleteMember(int memberId) {

        MemberList.remove(memberId-1);
        DBconn.dbUpdate("DELETE FROM " + DatabaseController.DBprefix + "Member WHERE memberId="+memberId);
    }
}

