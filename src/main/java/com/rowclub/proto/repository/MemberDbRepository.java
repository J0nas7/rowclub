package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public int getMemberId(int memberId) {
        return memberId;
    }

    @Override
    public String getFirstName(String firstName) {
        return firstName;
    }

    @Override
    public List<Member> readAllMembers() { return Member; }

    @Override
    public void createMember(Member member) {
        member.setMemberID(Member.size()+1);
        Member.add(member);
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

