package com.rowclub.proto.repository;

import com.rowclub.proto.model.BoatTripLink;

import java.sql.SQLException;
import java.util.List;

public interface IBoatTripLinkRepository {

        // Interface == contract that other classes can sign and promise to fulfill
        // CRUD methods + readAll
        List<BoatTripLink> readAllBoatTripLinks();

        int getBoatTripLinkListSize();

        void createBoatTripLink(int fkMemberID,int fkBoatTripID) throws SQLException;

        BoatTripLink readBoatTripLink(int boatTripLinkID);

        void updateBoatTripLink(int boatTripLinkID,int fkMemberID,int fkBoatTripid) throws SQLException;

        void deleteBoatTripLink(int boatTripLinkID) throws SQLException;

}
