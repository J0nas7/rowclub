package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTrip;
import com.rowclub.proto.model.Member;

import java.util.List;

public interface IMemberRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll
    List<Member> readAllMembers();

    int getMemberListSize();

    int getMemberId(int memberId);

    String getFirstName(String firstName);

    void createMember(Member member);

    Member readMembers(int memberId);

    void updateMember(Member member);

    void deleteMember(int memberId);
}
