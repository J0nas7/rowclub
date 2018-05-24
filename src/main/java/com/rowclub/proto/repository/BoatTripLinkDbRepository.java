package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.BoatTripLink;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rowclub.proto.controller.ProtocolController.DBconn;


@Repository
public class BoatTripLinkDbRepository implements IBoatTripLinkRepository {
        public static List<BoatTripLink> BoatTripLinkList;
        private ResultSet BoatTripLinkQuery;

        // METHOD BY JONAS
        public BoatTripLinkDbRepository() throws SQLException {
            BoatTripLinkList = new ArrayList<>();
            String BoatTripLinkSql = "SELECT * FROM "+DatabaseController.DBprefix+"BoatTripLink";
            BoatTripLinkQuery = DBconn.dbQuery(BoatTripLinkSql);
            while (BoatTripLinkQuery.next()) {
                BoatTripLinkList.add(new BoatTripLink(
                        BoatTripLinkQuery.getInt("BoatTripLinkID"),
                        BoatTripLinkQuery.getInt("fkMemberID"),
                        BoatTripLinkQuery.getInt("fkBoatTripID")
                ));
            }
        }

        public int getBoatTripLinkListSize() { return BoatTripLinkList.size(); }

        @Override
        public List<BoatTripLink> readAllBoatTripLinks() { return BoatTripLinkList; }

        @Override
        public void createBoatTripLink(int fkMemberID,int fkBoatTripID) throws SQLException {
            int id = 0;
            ResultSet rs;
            String statement = "default"+","+
                    fkMemberID+","+
                    fkBoatTripID;
            String boatStatement = "insert into "+DatabaseController.DBprefix+"BoatTripLink values ("+statement+");";
            DBconn.dbUpdate(boatStatement);

            rs = DBconn.dbQuery("SELECT Max(BoatTripLinkID) FROM "+DatabaseController.DBprefix+"BoatTripLink;");

            if (rs.next()){
                id = (rs.getInt(1));
            }

            BoatTripLink boat = new BoatTripLink(id,fkMemberID,fkBoatTripID);
            boat.setBoatTripLinkID(BoatTripLinkList.size()+1);
            BoatTripLinkList.add(boat);
        }

        @Override
        public BoatTripLink readBoatTripLink(int arrayID) {
            return BoatTripLinkList.get(arrayID-1);
        }

        @Override
        public void updateBoatTripLink(int boatTripLinkID,int fkMemberID,int fkBoatTripID) throws SQLException {

            int index = 0;
            ResultSet rs;

            rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "BoatTripLink WHERE BoatTripLinkID <" + boatTripLinkID + ";");
            if (rs.next()) {
                index = (rs.getInt(1));
            }

            String statement = "UPDATE "+DatabaseController.DBprefix+"BoatTripLink SET ";

            BoatTripLink boat = BoatTripLinkList.get(index);

            if(fkMemberID != 0){
                boat.setFkMemberID(fkMemberID);
                statement = statement + "fkMemberID = '"+fkMemberID+"',";

            }
            if(fkBoatTripID != 0){
                boat.setFkBoatTripID(fkBoatTripID);
                statement = statement + "fkBoatTripID = '"+fkBoatTripID+"',";
            }

            statement = statement.substring(0,statement.length()-1);

            statement = statement + " WHERE BoatTripLinkID = " + boatTripLinkID;

            DBconn.dbUpdate(statement);

            BoatTripLinkList.set(index,boat);

        }

        @Override
        public void deleteBoatTripLink(int boatTripLinkID) throws SQLException {
            int index = 1;
            ResultSet rs;

            rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "BoatTripLink WHERE BoatTripLinkID <" + boatTripLinkID + ";");
            if (rs.next()) {
                index = (rs.getInt(1));
            }

            BoatTripLinkList.remove(index);
            DBconn.dbUpdate("DELETE FROM "+DatabaseController.DBprefix+"BoatTripLink WHERE BoatTripLinkID ="+boatTripLinkID);
        }
    }
