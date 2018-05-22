package com.rowclub.proto.repository;

import com.rowclub.proto.model.Season;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ISeasonRepository {

    int getSeasonListSize();

    List<Season> readAllSeasons();

    void createSeason(String startDate) throws ParseException, SQLException;

    Season readSeason(int arrayId);

    void updateSeason(int seasonId, String startDate, String endDate) throws SQLException, ParseException;

    void autoCompleteSeasons() throws SQLException;

    void deleteSeason(int seasonId) throws SQLException;

}
