package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.PatternSyntaxException;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

//Jacob har lavet mange redigeringer
@Repository
public class MemberDbRepository implements IMemberRepository {
    private ResultSet MemberQuery;
    public static List<Member> MemberList;


    public MemberDbRepository() throws SQLException, ParseException {
        MemberList = new ArrayList<>();
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
        reduceGuestName();
    }

    @Override
    public void reduceGuestName() throws SQLException, ParseException {

        for (int i = 0; i < MemberList.size(); i++) {

            if (MemberList.get(i).getType().equalsIgnoreCase("guest")) {

            int index = MemberList.get(i).getMemberID();

            String firstName = MemberList.get(i).getFirstName().substring(0,1);

            String lastName = MemberList.get(i).getLastName().substring(0,1);

            updateMember(
                    index,
                    firstName,
                    lastName,
                    "",
                    "",
                    "-",
                    false,
                    false,
                    "guest",
                    "-");
            }
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
    public void createMember(String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException {
        Date dateDoB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(DoB);
        Date dateReg = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(RegDate);

        int id = 0;
        ResultSet rs;

        int AdminAdd;
        if (Admin) {AdminAdd = 1;} else {AdminAdd = 0;}
        int MateyAdd;
        if (Matey) {MateyAdd = 1;} else {MateyAdd = 0;}

        FirstName = DBconn.res(FirstName);
        LastName = DBconn.res(LastName);
        DoB = DBconn.res(DoB);
        RegDate = DBconn.res(RegDate);
        Phone = DBconn.res(Phone);
        Type = DBconn.res(Type);
        PhotoRef = DBconn.res(PhotoRef);

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
        DBconn.dbUpdate(insertMember);

        System.out.println(insertMember);

        rs = DBconn.dbQuery("SELECT Max(MemberID) FROM "+DatabaseController.DBprefix+"Member");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        Member member = new Member(id,FirstName,LastName,dateDoB,dateReg,Phone,Admin,Matey,Type,PhotoRef);
        //System.out.println(MemberList.size());
        MemberList.add(member);
    }

    @Override
    public void createGuest(String FirstName, String LastName) throws SQLException {
        Date date = new Date();

        int id = 0;
        ResultSet rs;

        FirstName = DBconn.res(FirstName);
        LastName = DBconn.res(LastName);

        String memberValues =
                "(default" + ",'"
                        + FirstName + "','"
                        + LastName + "'," +
                        "'2000-01-01'," +
                        "'2000-01-01'," +
                        "''," +
                        "'0'," +
                        "'0'," +
                        "'guest'," +
                        "''" +
                        ")";


        String insertMember = "INSERT INTO " + DatabaseController.DBprefix + "Member" + " VALUES " + (memberValues);
        DBconn.dbUpdate(insertMember);

        System.out.println(insertMember);

        rs = DBconn.dbQuery("SELECT Max(MemberID) FROM "+DatabaseController.DBprefix+"Member");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        Member member = new Member(id,FirstName,LastName,date,date,"-",false,false,"guest","-");
        //System.out.println(MemberList.size());
        MemberList.add(member);
    }

    @Override
    public Member readMembers(int arrayId) {

        return MemberList.get(arrayId-1);
    }

    @Override
    public void updateMember(int memberId, String FirstName, String LastName, String DoB, String RegDate, String Phone, Boolean Admin, Boolean Matey, String Type, String PhotoRef) throws SQLException, ParseException {

        int index = 0;
        ResultSet rs;

        int AdminAdd;
        if (Admin) {AdminAdd = 1;} else {AdminAdd = 0;}
        int MateyAdd;
        if (Matey) {MateyAdd = 1;} else {MateyAdd = 0;}

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        String updateMember = "UPDATE " + DatabaseController.DBprefix + "Member SET ";
        Member member = MemberList.get(index);

        FirstName = DBconn.res(FirstName);
        LastName = DBconn.res(LastName);
        DoB = DBconn.res(DoB);
        RegDate = DBconn.res(RegDate);
        Phone = DBconn.res(Phone);
        Type = DBconn.res(Type);
        PhotoRef = DBconn.res(PhotoRef);

        if(FirstName != ""){

            member.setFirstName(FirstName);
            updateMember = updateMember + "FirstName ='" +FirstName+ "',";
        }

        if(LastName != ""){

            member.setLastName(LastName);
            updateMember = updateMember + "LastName ='" +LastName+ "',";
        }

        if(DoB != ""){
            Date dateDoB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(DoB);
            member.setDoB(dateDoB);
            updateMember = updateMember + "DoB ='" +DoB+ "',";

        }
        if(RegDate != ""){
            Date dateReg = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(RegDate);
            member.setRegDate(dateReg);
            updateMember = updateMember + "RegDate ='" +RegDate+ "',";

        }
        if(Phone != ""){

            member.setPhone(Phone);
            updateMember = updateMember + "Phone ='" +Phone+ "',";

        }

        if(Admin != null){

            member.setAdmin(Admin);
            updateMember = updateMember + "Admin ='" +AdminAdd+ "',";

        }
        if(Matey != null){

            member.setMate(Matey);
            updateMember = updateMember + "Matey ='" +MateyAdd+ "',";

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

        MemberList.set(index, member);
    }

    @Override
    public void deleteMember(int memberId) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Member WHERE MemberID <" + memberId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        MemberList.remove(index);
        DBconn.dbUpdate("DELETE FROM " + DatabaseController.DBprefix + "Member WHERE MemberID="+memberId);
    }
}

