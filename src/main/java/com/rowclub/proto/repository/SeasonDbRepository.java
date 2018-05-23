package com.rowclub.proto.repository;

import com.rowclub.proto.controller.DatabaseController;
import com.rowclub.proto.model.Season;
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
public class SeasonDbRepository implements ISeasonRepository {
    private ResultSet SeasonQuery;
    public static List<Season> SeasonList;

    public SeasonDbRepository() throws SQLException {
        SeasonList = new ArrayList<>();
        String SeasonSql = "SELECT * FROM " + DatabaseController.DBprefix + "Season";
        SeasonQuery = DBconn.dbQuery(SeasonSql);
        while (SeasonQuery.next()) {
            SeasonList.add(new Season(
                    SeasonQuery.getInt("SeasonID"),
                    SeasonQuery.getDate("StartDate"),
                    SeasonQuery.getDate("EndDate")
            ));
        }
    }

    @Override
    public int getSeasonListSize() {return SeasonList.size();}

    @Override
    public List<Season> readAllSeasons() {return  SeasonList;}

    @Override
    public void createSeason(String startDate) throws ParseException, SQLException {

        autoCompleteSeasons();

        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(startDate);

        int id = 0;
        ResultSet rs;

        startDate = DBconn.res(startDate);

        String seasonValues =
                "(default" + ",'"
                    + startDate + "',"
                    + "default" + ")";

        String insertSeason = "INSERT INTO " + DatabaseController.DBprefix + "Season" + " VALUES " + (seasonValues);
        DBconn.dbUpdate(insertSeason);

        rs = DBconn.dbQuery("SELECT Max(SeasonID) FROM " + DatabaseController.DBprefix+"Season");

        if (rs.next()){
            id = (rs.getInt(1));
        }

        Season season = new Season(id,date,null);
        SeasonList.add(season);
    }

    @Override
    public Season readSeason(int arrayId) {
        return SeasonList.get(arrayId-1);
    }

    @Override
    public void updateSeason(int seasonId, String startDate, String endDate) throws SQLException, ParseException {
        Date dateStart = null;
        Date dateEnd = null;

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Season WHERE SeasonID <" + seasonId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        String updateSeason = "UPDATE " + DatabaseController.DBprefix + "Season SET ";
        Season season = SeasonList.get(index);

        startDate = DBconn.res(startDate);
        endDate = DBconn.res(endDate);

        if (startDate != "") {
            dateStart = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(startDate);
            season.setStartDate(dateStart);
            updateSeason = updateSeason + "StartDate ='" + startDate + "',";
        }

        if (endDate != "") {
            dateEnd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(endDate);
            season.setEndDate(dateEnd);
            updateSeason = updateSeason + "EndDate ='" + endDate + "',";
        }

        updateSeason = updateSeason.substring(0,updateSeason.length()-1);
        updateSeason = updateSeason + " WHERE SeasonID = " + seasonId;

        DBconn.dbUpdate(updateSeason);

        SeasonList.set(index,season);

    }

    @Override
    public void autoCompleteSeasons() throws SQLException {

        List<Season> tempList = new ArrayList<>();
        ResultSet rs;

        String SeasonSql = "SELECT * FROM " + DatabaseController.DBprefix + "Season"
                + " WHERE EndDate IS NULL";
        rs = DBconn.dbQuery(SeasonSql);
        while (rs.next()) {
            tempList.add(new Season(
                    rs.getInt("SeasonID"),
                    rs.getDate("StartDate"),
                    rs.getDate("EndDate")
            ));
        }

        for (int i = 0; i < tempList.size(); i++) {
            int index = tempList.get(i).getSeasonID();

            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String DateToStr = format.format(curDate);

            DateToStr = DBconn.res(DateToStr);

            DBconn.dbUpdate("UPDATE " + DatabaseController.DBprefix + "Season SET EndDate ='" + DateToStr + "' WHERE SeasonID =" + index);
        }


    }

    @Override
    public void deleteSeason(int seasonId) throws SQLException {

        int index = 0;
        ResultSet rs;

        rs = DBconn.dbQuery("SELECT COUNT(*) FROM " + DatabaseController.DBprefix + "Season WHERE SeasonID <" + seasonId + ";");
        if (rs.next()) {
            index = (rs.getInt(1));
        }

        SeasonList.remove(index);
        DBconn.dbUpdate("DELETE FROM " + DatabaseController.DBprefix + "Season WHERE SeasonID="+seasonId);
    }




}
