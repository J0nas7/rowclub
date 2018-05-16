package com.rowclub.proto.model;

import java.time.LocalDate;
import java.util.Date;

public class Member {

    private int memberID;
    private String firstName;
    private String lastName;
    private Date doB;
    private Date regDate;
    private String phone;
    private boolean admin;
    private boolean mate;
    private String type;
    private String photoRef;

    public Member(int memberID, String firstName, String lastName, Date doB, Date regDate, String phone, boolean admin, boolean mate, String type, String photoRef) {
        this.memberID = memberID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.doB = doB;
        this.regDate = regDate;
        this.phone = phone;
        this.admin = admin;
        this.mate = mate;
        this.type = type;
        this.photoRef = photoRef;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDoB() {
        return doB;
    }

    public void setDoB(Date doB) {
        this.doB = doB;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isMate() {
        return mate;
    }

    public void setMate(boolean mate) {
        this.mate = mate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }
}
