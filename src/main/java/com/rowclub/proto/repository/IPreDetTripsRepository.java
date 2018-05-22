package com.rowclub.proto.repository;

import com.rowclub.proto.model.PreDetTrips;

import java.sql.SQLException;
import java.util.List;

public interface IPreDetTripsRepository {
        // Interface == contract that other classes can sign and promise to fulfill
        // CRUD methods + readAll

        List<PreDetTrips> readAllPreDetTripss();

        int getPreDetTripsListSize();

        void createPreDetTrips(String location,Double Distance,int preEstDuration) throws SQLException;

        PreDetTrips readPreDetTrips(int preID);

        void updatePreDetTrips(int preDetTripsID,String location,Double distance, int preEstDuration) throws SQLException;

        void deletePreDetTrips(int preID) throws SQLException;
    }


