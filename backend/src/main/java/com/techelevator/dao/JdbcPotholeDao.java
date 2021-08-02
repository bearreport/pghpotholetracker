package com.techelevator.dao;

import com.techelevator.model.Pothole;
import com.techelevator.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcPotholeDao implements PotholeDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcPotholeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
   public List<Pothole> getAllPotholes(){
        List<Pothole> potholes = new ArrayList<>();
        String sql = "select * from potholes";

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
        String sql = "SELECT * FROM potholes WHERE submitter_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userID);
       while(results.next()) {
           Pothole pothole = mapRowToPothole(results);
           potholes.add(pothole);
       }
       return potholes;
    }

    @Override
    public Pothole getPotholeById(Long potholeId) {
        String sql = "SELECT * FROM potholes WHERE pothole_id = ?";
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
        String sql = "SELECT * FROM potholes WHERE neighborhood = ?";
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
      String sql = "SELECT * FROM potholes WHERE date_created = ?";
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
        String sql = "SELECT * FROM potholes WHERE dimension = ?";
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
       String sql = "SELECT * FROM potholes WHERE current_status = ?";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentStatus);
       while(results.next()) {
           Pothole pothole = mapRowToPothole(results);
           potholes.add(pothole);
       }
       return potholes;

   }
    @Override
    public boolean createPothole(Pothole pothole){
        // create user
        String sql = "INSERT INTO potholes (pothole_id, submitter_id, lat, lon, addr, neighborhood, date_created, " +
                "date_inspected, date_repaired, current_status, severity, dimensions, notes) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING pothole_id";
        Integer newPotholeID;
        try {
            newPotholeID = jdbcTemplate.update(sql, pothole.getSubmitterId(),
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
                    pothole.getNotes());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }




    private Pothole mapRowToPothole(SqlRowSet rs) {
        Pothole pothole = new Pothole();
        pothole.setPotholeId(rs.getLong("pothole_id"));
        pothole.setSubmitterId(rs.getInt("submitter_id"));
        pothole.setLon(rs.getString("lon"));
        pothole.setLat(rs.getString("lat"));
        pothole.setAddr(rs.getString("addr"));
        pothole.setDateInspected(rs.getDate("date_inspected").toLocalDate());
        pothole.setDateRepaired(rs.getDate("date_repaired").toLocalDate());
        pothole.setSeverity(rs.getString("severity"));
        pothole.setCurrentStatus(rs.getString("current_status"));
        pothole.setDimensions(rs.getString("dimensions"));
        pothole.setDateCreated(rs.getDate("date_created").toLocalDate());
        return pothole;
    }

}
