package com.techelevator.dao;

import com.techelevator.model.Pothole;
import com.techelevator.model.User;

import java.time.LocalDate;
import java.util.List;

public interface PotholeDao {

    List<Pothole> getAllPotholes();

    List<Pothole> getPotholesByUserId(Long userID);

    Pothole getPotholeById(Integer pothole_id);

    List<Pothole> getPotholesByNeighborhood(String neighborhood);

    List<Pothole> getPotholesByDateCreated(LocalDate dateCreated);

    List<Pothole> getPotholesByDimension(String dimension);

    List<Pothole> getPotholesByStatus(String currentStatus);

    Pothole createPothole(Pothole pothole);

}
