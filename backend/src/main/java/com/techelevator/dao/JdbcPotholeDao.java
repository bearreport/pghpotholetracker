package com.techelevator.dao;

import com.techelevator.PotholeNotFoundException;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class JdbcPotholeDao implements PotholeDao{

    private JdbcTemplate jdbcTemplate;
    private String potholeTableFields =
            "pothole_id, " +
            "submitter_id, " +
            "lat, " +
            "lon, " +
            "addr, " +
            "neighborhood, " +
            "date_created, " +
            "date_inspected, " +
            "date_repaired, " +
            "current_status, " +
            "severity, " +
            "dimensions, " +
            "notes ";

    public JdbcPotholeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pothole> getAllPotholes(){
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholes.add(pothole);
        }

        return potholes;
    }

    @Override
    public List<Pothole> getPotholesByUserId(Long userID) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes WHERE submitter_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userID);
       while(results.next()) {
           Pothole pothole = mapRowToPothole(results);
           potholes.add(pothole);
       }
       return potholes;
    }

    @Override
    public Pothole getPotholeById(Integer potholeId) {
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes WHERE pothole_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, potholeId);
        if(results.next()) {
            return mapRowToPothole(results);
        } else {
            throw new RuntimeException("userId "+ potholeId +" was not found.");
        }
    }

    @Override
    public List<Pothole> getPotholesByNeighborhood(String neighborhood) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes WHERE neighborhood = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, neighborhood);
        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholes.add(pothole);
        }
        return potholes;
    }

    @Override
    public List<Pothole> getPotholesByDateCreated(LocalDate dateCreated){
      List<Pothole> potholes = new ArrayList<>();
      String sql = "SELECT " +
              potholeTableFields +
              "FROM potholes WHERE DATE_PART('day', date_created) = DATE_PART('day', ?)";
      SqlRowSet results = jdbcTemplate.queryForRowSet(sql, dateCreated);
      while(results.next()) {
          Pothole pothole = mapRowToPothole(results);
          potholes.add(pothole);
      }
      return potholes;
    }

    @Override
    public List<Pothole> getPotholesByDimension(String dimension) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes WHERE dimensions = ?::pothole_dimensions";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, dimension);
        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholes.add(pothole);
        }
        return potholes;
    }

    @Override
    public List<Pothole> getPotholesByStatus(String currentStatus){
       List<Pothole> potholes = new ArrayList<>();
       String sql = "SELECT " +
               potholeTableFields +
               "FROM potholes WHERE current_status = ?::pothole_status";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentStatus);
       while(results.next()) {
           Pothole pothole = mapRowToPothole(results);
           potholes.add(pothole);
       }
       return potholes;
    }

    @Override
    public List<Pothole> getPotholesBySeverity(String severity){
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT " +
                potholeTableFields +
                "FROM potholes WHERE severity = ?::pothole_severity";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, severity);
        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholes.add(pothole);
        }
        return potholes;
    }

    @Override
    public Pothole createPothole(Pothole pothole, String userName){
        String sql = "INSERT INTO potholes (" +
                potholeTableFields +
                ") VALUES (DEFAULT, " + // pothole_id
                "(SELECT user_id FROM users WHERE username = ?), " + // submitter_id
                "?, " + // lat
                "?, " + // lon
                "?, " + // addr
                "?, " + // neighborhood
                "CURRENT_TIMESTAMP, " + // date_created
                "NULL, " + // date_inspected
                "NULL, " + // date_repaired
                "'uninspected'::pothole_status, " + // current_status
                "NULL, " + // severity (needs ::pothole_severity to be cast correctly)
                "?::pothole_dimensions, " + // dimensions
                "?) RETURNING pothole_id";
        Integer newPotholeID;
        try {
            newPotholeID = jdbcTemplate.queryForObject(sql, Integer.class,
                    userName,
                    pothole.getLat(),
                    pothole.getLon(),
                    pothole.getAddr(),
                    pothole.getNeighborhood(),
                    // removed date_created, date_inspected, date_repaired, current_status, and severity from this,
                    // since we are defining default values for them above.
                    pothole.getDimensions(),
                    pothole.getNotes());
            return getPotholeById(newPotholeID);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Pothole updateBasic(Pothole pothole, int id) {
       String sqlUpdate = "UPDATE potholes SET " +
                "lat = ?, " +
                "lon = ?, " +
                "addr = ?, " +
                "neighborhood = ?, " +
                "date_created = ?, " +
                "dimensions = ?::pothole_dimensions, " +
                "notes = ? " +
                "WHERE pothole_id = ?;";
       jdbcTemplate.update(sqlUpdate,
               pothole.getLat(),
               pothole.getLon(),
               pothole.getAddr(),
               pothole.getNeighborhood(),
               pothole.getDateCreated(),
               // removed date_inspected, date_repaired, current_status, and severity from this update method
               // as we do not want to permit all users to update these fields
               pothole.getDimensions(),
               pothole.getNotes(),
               id);
       return getPotholeById(id);
    }

    @Override
    public Pothole updateFull(Pothole pothole, int id) {
        String sqlUpdate = "UPDATE potholes SET " +
                "lat = ?, " +
                "lon = ?, " +
                "addr = ?, " +
                "neighborhood = ?, " +
                "date_created = ?, " +
                "date_inspected = ?, " +
                "date_repaired = ?, " +
                "current_status = ?::pothole_status, " +
                "severity = ?::pothole_severity, " +
                "dimensions = ?::pothole_dimensions, " +
                "notes = ? " +
                "WHERE pothole_id = ?;";
        jdbcTemplate.update(sqlUpdate,
                pothole.getLat(),
                pothole.getLon(),
                pothole.getAddr(),
                pothole.getNeighborhood(),
                pothole.getDateCreated(),
                pothole.getDateInspected(),
                pothole.getDateRepaired(),
                pothole.getCurrentStatus(),
                pothole.getSeverity(),
                pothole.getDimensions(),
                pothole.getNotes(),
                id);
        return getPotholeById(id);
    }

    @Override
    public boolean deletePothole(int potholeId) {
        String sql = "DELETE FROM potholes WHERE pothole_id = ?;";
       return  jdbcTemplate.update(sql, potholeId) == 1;
    }

    private Pothole mapRowToPothole(SqlRowSet rs) {
        Pothole pothole = new Pothole();
        pothole.setPotholeId(rs.getLong("pothole_id"));
        pothole.setSubmitterId(rs.getInt("submitter_id"));
        pothole.setLon(rs.getString("lon"));
        pothole.setLat(rs.getString("lat"));
        pothole.setAddr(rs.getString("addr"));
        pothole.setNeighborhood(rs.getString("neighborhood"));
        try {
            pothole.setDateCreated(rs.getDate("date_created").toLocalDate());
        } catch (NullPointerException e) {
            pothole.setDateInspected(null);
            // This one should never fail since the db field is NOT NULL, but putting it in a try-catch anyway.
        }
        try {
            pothole.setDateInspected(rs.getDate("date_inspected").toLocalDate());
        } catch (NullPointerException e) {
            pothole.setDateInspected(null);
        }
        try {
            pothole.setDateRepaired(rs.getDate("date_repaired").toLocalDate());
        } catch (NullPointerException e) {
            pothole.setDateInspected(null);
        }
        pothole.setSeverity(rs.getString("severity"));
        pothole.setCurrentStatus(rs.getString("current_status"));
        pothole.setDimensions(rs.getString("dimensions"));
        pothole.setNotes(rs.getString("notes"));
        return pothole;
    }

}
