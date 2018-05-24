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
