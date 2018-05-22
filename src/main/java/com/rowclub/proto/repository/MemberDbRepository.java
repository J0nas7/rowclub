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
    private static List<Member> MemberList;


    public MemberDbRepository() throws SQLException {
        MemberList = new ArrayList<>();
        String MemberSql = "SELECT * FROM " + DatabaseController.DBprefix + "Member";
        MemberQuery = DBconn.dbQuery(MemberSql);
        while (MemberQuery.next()) {
            MemberList.add(new Member(
                    MemberQuery.getInt("MemberID"),
                    MemberQuery.getString("FirstName"),
                    MemberQuery.getString("LastName"),
                    MemberQuery.getString("DoB"),
                    MemberQuery.getString("RegDate"),
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
    public void createMember(String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException {
        int id = 0;
        ResultSet rs;

        int AdminAdd;
        if (Admin == true) {AdminAdd = 1;} else {AdminAdd = 0;}
        int MateyAdd;
        if (Matey == true) {MateyAdd = 1;} else {MateyAdd = 0;}

        String memberValues =
                "(default" + ",'"
                        + FirstName + "','"
                        + LastName + "','"
                        + DoB + "','"
                        + RegDate + "','"
                        + Phone + "','"
                        + AdminAdd + "','"
                        + MateyAdd + "','"
                        + Type + "','"
                        + PhotoRef + "')";


        String insertMember = "INSERT INTO " + DatabaseController.DBprefix + "Member" + " VALUES " + (memberValues);
        //DBconn.dbUpdate(insertMember);

        System.out.println(insertMember);

        rs = DBconn.dbQuery("SELECT Max(MemberID) FROM "+DatabaseController.DBprefix+"Member");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        Member member = new Member(id,FirstName,LastName,DoB,RegDate,Phone,Admin,Matey,Type,PhotoRef);
        System.out.println(MemberList.size());
        //MemberList.add(MemberList.size()+1,member);

    }

    @Override
    public Member readMembers(int arrayId) {

        return MemberList.get(arrayId-1);
    }

    @Override
    public Member searchMembers(int MemberId) {
        //searches for a MemberId in the arraylist
        int current = 0;

        while (current != MemberList.size()) {

            if (MemberList.get(current).getMemberID() == MemberId) {
                return MemberList.get(current);
            }
            current+=1;
        }
        return null;
    }

    @Override
    public int findMemberID(int MemberId) {
        //finds the spot in the arraylist where the objects Memberid equals the parameter
        int current = 0;

        while (current != MemberList.size()) {

            if (MemberList.get(current).getMemberID() == MemberId) {
                return current;
            }
            current+=1;
        }

        return -1;
    }

    @Override
    public void updateMember(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) {

        String updateMember = "UPDATE " + DatabaseController.DBprefix + "Member SET ";

        Member member = searchMembers(memberId);

        if(FirstName != ""){

            member.setFirstName(FirstName);
            updateMember = updateMember + "FirstName ='" +FirstName+ "',";
        }

        if(LastName != ""){

            member.setLastName(LastName);
            updateMember = updateMember + "LastName ='" +LastName+ "',";
        }

        if(DoB != ""){

            member.setDoB(DoB);
            updateMember = updateMember + "DoB ='" +DoB+ "',";

        }
        if(RegDate != ""){

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

        MemberList.set(findMemberID(memberId), member);
    }

    @Override
    public void deleteMember(int memberId) {

        MemberList.remove(searchMembers(memberId));
        DBconn.dbUpdate("DELETE FROM " + DatabaseController.DBprefix + "Member WHERE memberId="+memberId);
    }
}

