package com.rowclub.proto.controller;

import javax.xml.transform.Result;
import java.sql.*;

// ALL BY JONAS
public class DatabaseController {
    private static Connection DBconnect;
    private static Statement statement;
    private static ResultSet resultSet;

    private static String DriverName = "com.mysql.jdbc.Driver";
    private static String DBurl = "jdbc:mysql://mysql12.unoeuro.com:3306/dotweb_nu_db2?autoReconnect=true&useSSL=false&characterEncoding=utf8";
    private static String DBuser = "dotweb_nu";
    private static String DBpassword = "Header01Body02Footer93";
    public static String DBprefix = "Protocol_";

    // Database configuration start
    public DatabaseController() {
        try {
            Class.forName(DriverName).newInstance();

            DBconnect = DriverManager.getConnection(DBurl, DBuser, DBpassword);
            statement = DBconnect.createStatement();
            DBurl = ""; DBuser = ""; DBpassword = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // CREATE DATABASE krdatabase /*!40100 DEFAULT CHARACTER SET utf8 */;
        /*
        USE krdatabase;

        CREATE TABLE protokol_boat (
                BoatID int(11) NOT NULL AUTO_INCREMENT,
                Name varchar(45) NOT NULL,
        Type varchar(45) NOT NULL,
        status varchar(45) NOT NULL DEFAULT 'Idle',
                seats int(11) NOT NULL,
        PRIMARY KEY (BoatID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_boattrip (
                BoatTripID int(11) NOT NULL AUTO_INCREMENT,
                fkBoatID int(11) NOT NULL,
        Distance double NOT NULL DEFAULT '0',
                EstDuration int(11) DEFAULT NULL,
        DateStamp date NOT NULL,
        fkSeasonID int(11) DEFAULT NULL,
        CompletionTime int(11) DEFAULT NULL,
        TimeStamp int(11) NOT NULL,
        PRIMARY KEY (BoatTripID),
                KEY BoatID_idx (fkBoatID),
                KEY SeasonID_idx (fkSeasonID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_member (
                MemberID int(11) NOT NULL AUTO_INCREMENT,
                FirstName varchar(45) DEFAULT NULL,
        LastName varchar(45) DEFAULT NULL,
        DoB date DEFAULT NULL,
        RegDate date DEFAULT NULL,
        Phone varchar(45) DEFAULT NULL,
        Admin tinyint(4) NOT NULL DEFAULT '0',
                Matey tinyint(4) NOT NULL DEFAULT '0',
                Type varchar(45) DEFAULT 'guest',
                PhotoRef varchar(45) DEFAULT NULL,
        PRIMARY KEY (MemberID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_predeterminedtrips (
                PreID int(11) NOT NULL AUTO_INCREMENT,
                Location varchar(45) NOT NULL,
        Distance double NOT NULL,
                PreEstDuration int(11) NOT NULL,
        PRIMARY KEY (PreID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_season (
                SeasonID int(11) NOT NULL AUTO_INCREMENT,
                Year int(11) NOT NULL,
        StartDate date DEFAULT NULL,
        EndDate date DEFAULT NULL,
        PRIMARY KEY (SeasonID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_warning (
                WarningID int(11) NOT NULL AUTO_INCREMENT,
                Info varchar(45) NOT NULL,
        fkBoatTripID int(11) NOT NULL,
        PRIMARY KEY (WarningID),
                KEY BoatTripID_idx (fkBoatTripID),
                KEY BoatTripID2_idx (fkBoatTripID),
                CONSTRAINT BoatTripID2 FOREIGN KEY (fkBoatTripID) REFERENCES protokol_boattrip (BoatTripID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        CREATE TABLE protokol_boattriplink (
                BoatTripLinkID int(11) NOT NULL AUTO_INCREMENT,
                fkMemberID int(11) NOT NULL,
        fkBoatTripID int(11) NOT NULL,
        PRIMARY KEY (BoatTripLinkID),
                KEY MemberID_idx (fkMemberID),
                KEY BoatTripID_idx (fkBoatTripID),
                KEY BoatTripID_idx2 (fkBoatTripID),
                CONSTRAINT BoatTripID FOREIGN KEY (fkBoatTripID) REFERENCES protokol_boattrip (BoatTripID) ON DELETE NO ACTION ON UPDATE NO ACTION,
                CONSTRAINT MemberID FOREIGN KEY (fkMemberID) REFERENCES protokol_member (MemberID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
         */
    }
    // Database configuration end

    // Outputs a query from a sql string
    public ResultSet dbQuery(String SQLstring) {
        try {
            resultSet = statement.executeQuery(SQLstring);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Performs a query from a sql string
    public void dbUpdate(String SQLstring) {
        try {
            statement.executeUpdate(SQLstring);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns the number of rows in a ResultSet
    public int resultCount(ResultSet set) throws SQLException {
        int rowCount;
        int currentRow = set.getRow();              // Get current row
        rowCount = set.last() ? set.getRow() : 0;   // Determine number of rows
        if (currentRow == 0) {                      // If there was no current row
            set.beforeFirst();                      // We want next() to go to first row
        } else {                                    // If there WAS a current row
            set.absolute(currentRow);               // Restore it
        }

        return rowCount;
    }

    // Securing a string before using in sql string
    public String res(String content) {
        try {
            content = content.replaceAll("\n","\\n")
                    .replaceAll("\r", "\\r")
                    .replaceAll("\t", "\\t")
                    .replaceAll("\00", "\\0")
                    .replaceAll("'", "\\'")
            /* replaceAll("\", "\\\\") */
            /*.replaceAll("\\"", "\\\"")*/;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return content;
    }
}
