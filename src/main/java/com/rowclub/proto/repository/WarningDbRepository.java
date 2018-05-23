package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.Warning;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.rowclub.proto.controller.ProtocolController.DBconn;

@Repository
public class WarningDbRepository implements IWarningRepository {
    private ResultSet WarningQuery;
    public static List<Warning> WarningList;

    public WarningDbRepository() throws SQLException {
        //warnings bliver sorteret efter dato, så den nyeste warning bliver pladseret forest i listen
        WarningList = new ArrayList<>();
        String WarningSql = "SELECT * FROM " + DatabaseController.DBprefix + "Warning ORDER BY DateStamp desc, TimeStamp desc";
        WarningQuery = DBconn.dbQuery(WarningSql);
        while (WarningQuery.next()) {
            WarningList.add(new Warning(
                    WarningQuery.getInt("WarningID"),
                    WarningQuery.getString("Info"),
                    WarningQuery.getInt("fkBoatTripID"),
                    WarningQuery.getDate("DateStamp"),
                    WarningQuery.getInt("TimeStamp")
            ));
        }
    }

    @Override
    public int getWarningListSize() {
        return WarningList.size();
    }

    @Override
    public List<Warning> readAllWarnings() {
        return WarningList;
    }

    @Override
    public void createWarning(String info, int fkBoatTripID, String DateStamp, int TimeStamp) throws SQLException, ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(DateStamp);

        int id = 0;
        ResultSet rs;
        //constructs sql statement and injects it into the database
        String warningValues =
                "default" + ",'"
                    +   info + "','"
                    +   fkBoatTripID + "','"
                    +   DateStamp + "','"
                    +   TimeStamp + "'";

        String insertWarning = "INSERT INTO " + DatabaseController.DBprefix + "Warning VALUES ("+warningValues+");";
        DBconn.dbUpdate(insertWarning);

        //System.out.println(insertWarning);

        //add a new object into the WarningList arraylist matching the inserted data
        rs = DBconn.dbQuery("SELECT Max(WarningID),DateStamp FROM "+DatabaseController.DBprefix+"Warning;");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        //because the warnings are sorted by date (most recent first) we insert the new warning object at the front of the arraylist
        Warning warning = new Warning(id,info,fkBoatTripID,date,TimeStamp);
        WarningList.add(0,warning);
    }

    @Override
    public Warning readWarning(int arrayId) {
        //finds the object at a specific slot in the arraylist
        return WarningList.get(arrayId-1);
    }

    @Override
    public void updateWarning(int warningId, String info, int fkBoatTripID, String DateStamp, int TimeStamp) throws SQLException, ParseException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Warning WHERE WarningID <" + warningId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        String statement = "UPDATE "+DatabaseController.DBprefix+"Warning SET ";

        Warning warning = WarningList.get(index);

        if (info != "") {
            warning.setInfo(info);
            statement = statement + "Info = '"+info+"',";
        }
        if (fkBoatTripID != 0) {
            warning.setBoatTripId(fkBoatTripID);
            statement = statement + "fkBoatTripID = '"+fkBoatTripID+"',";
        }
        if (DateStamp != "") {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(DateStamp);
            warning.setDateStamp(date);
            statement = statement + "DateStamp = '"+DateStamp+"',";
        }
        if (TimeStamp != 0) {
            warning.setTimeStamp(TimeStamp);
            statement = statement + "TimeStamp = '"+TimeStamp+"',";
        }

        statement = statement.substring(0,statement.length()-1);
        statement = statement + " WHERE WarningID = " + warningId;

        DBconn.dbUpdate(statement);

        WarningList.set(index,warning);
    }

    @Override
    public void deleteWarning(int warningId) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Warning WHERE WarningID <" + warningId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        WarningList.remove(index);
        DBconn.dbUpdate("DELETE FROM "+DatabaseController.DBprefix+"Warning WHERE WarningID ="+warningId);
    }

}
