package com.rowclub.proto.repository;

import com.rowclub.proto.model.Warning;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//Jacob ALT
public interface IWarningRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll

    int getWarningListSize();

    List<Warning> readAllWarnings();

    void createWarning(String info, int fkBoatTripID, String DateStamp, int TimeStamp) throws SQLException, ParseException;

    Warning readWarning(int warningId);

    void updateWarning(int warningId, String info, int fkBoatTripID, String DateStamp, int TimeStamp) throws SQLException, ParseException;

    void deleteWarning(int warningId) throws SQLException;



}
