package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.model.Pothole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/potholes")
@CrossOrigin
public class PotholeController {

    private PotholeDao potholeDao;

    public PotholeController(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Pothole> getAllPotholes() {
        return potholeDao.getAllPotholes();
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByUserId(@PathVariable long userId) {
        return potholeDao.getPotholesByUserId(userId);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Pothole getPotholeById(@PathVariable int id) {
        return potholeDao.getPotholeById(id);
    }

    @RequestMapping(path = "/neighborhood/{neighborhood}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByNeighborhood(@PathVariable String neighborhood) {
        return potholeDao.getPotholesByNeighborhood(neighborhood);
    }

    @RequestMapping(path = "/date/{createDate}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByDateCreated(@PathVariable String createDate) {
        return potholeDao.getPotholesByDateCreated(LocalDate.parse(createDate));
    }

    @RequestMapping(path = "/dimensions/{dimension}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByDimension(@PathVariable String dimension) {
        return potholeDao.getPotholesByDimension(dimension);
    }

    @RequestMapping(path = "/status/{status}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByStatus(@PathVariable String status) {
        return potholeDao.getPotholesByStatus(status);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Pothole createPothole(@RequestBody Pothole createdPothole) {
        return potholeDao.createPothole(createdPothole);
    }



}
