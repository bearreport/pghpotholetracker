package com.techelevator.dao;

import com.techelevator.PotholeNotFoundException;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;

import java.security.Principal;
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

    List<Pothole> getPotholesBySeverity(String severity);

    Pothole createPothole(Pothole pothole, String userName);

    Pothole updateBasic(Pothole pothole, int id) throws PotholeNotFoundException;

    Pothole updateFull(Pothole pothole, int id) throws PotholeNotFoundException;

    boolean deletePothole(int pothole_id) throws PotholeNotFoundException;

}
