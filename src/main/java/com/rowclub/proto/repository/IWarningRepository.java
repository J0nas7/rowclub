package com.rowclub.proto.repository;

import com.rowclub.proto.model.Warning;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IWarningRepository {
    // Interface == contract that other classes can sign and promise to fulfill
    // CRUD methods + readAll

    int getWarningListSize();

    List<Warning> readAllWarnings();

    void createWarning(String info, int fkBoatTripID, String DateStamp, int TimeStamp) throws SQLException;

    Warning readWarning(int warningId);

    Warning searchWarning(int warningId);

    int findWarningID(int warningId);

    void updateWarning(int warningId, String info, int fkBoatTripID, String DateStamp, int TimeStamp);

    void deleteWarning(int warningId);



}
