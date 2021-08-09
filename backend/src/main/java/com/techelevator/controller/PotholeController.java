package com.techelevator.controller;

import com.techelevator.PotholeNotFoundException;
import com.techelevator.dao.JdbcPotholeDao;
import com.techelevator.dao.PotholeDao;
import com.techelevator.exception.UserDoesNotOwnPotholeException;
import com.techelevator.model.Pothole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/potholes")
public class PotholeController {

    private PotholeDao potholeDao;

    public PotholeController(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

    // Default get method replaced with getPotholesByParameter
//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public List<Pothole> getAllPotholes() {
//        return potholeDao.getAllPotholes();
//    }

    @RequestMapping(path ="", method = RequestMethod.GET)
    public List<Pothole> getPotholesByParameter(
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam(defaultValue = "") String neighborhood,
            @RequestParam(required = false) LocalDate dateCreated,
            @RequestParam(defaultValue = "") String dimension,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String severity
            ) {

        if (userId > 0) {
            return potholeDao.getPotholesByUserId(userId);
        }
        if (!neighborhood.equals("")) {
            return potholeDao.getPotholesByNeighborhood(neighborhood);
        }
        if (dateCreated != null) {
            return potholeDao.getPotholesByDateCreated(dateCreated);
        }
        if (!dimension.equals("")) {
            return potholeDao.getPotholesByDimension(dimension);
        }
        if (!status.equals("")) {
            return potholeDao.getPotholesByStatus(status);
        }
        if (!severity.equals("")) {
            return potholeDao.getPotholesBySeverity(severity);
        }

        return potholeDao.getAllPotholes();
    }

    // These are all replaced by getPotholesByParameter
//    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesByUserId(@PathVariable long userId) {
//        return potholeDao.getPotholesByUserId(userId);
//    }
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
//    public Pothole getPotholeById(@PathVariable int id) {
//        return potholeDao.getPotholeById(id);
//    }
//
//    @RequestMapping(path = "/neighborhood/{neighborhood}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesByNeighborhood(@PathVariable String neighborhood) {
//        return potholeDao.getPotholesByNeighborhood(neighborhood);
//    }
//
//    @RequestMapping(path = "/date/{createDate}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesByDateCreated(@PathVariable String createDate) {
//        return potholeDao.getPotholesByDateCreated(LocalDate.parse(createDate));
//    }
//
//    @RequestMapping(path = "/dimensions/{dimension}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesByDimension(@PathVariable String dimension) {
//        return potholeDao.getPotholesByDimension(dimension);
//    }
//
//    @RequestMapping(path = "/status/{status}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesByStatus(@PathVariable String status) {
//        return potholeDao.getPotholesByStatus(status);
//    }
//
//    @RequestMapping(path = "/severity/{severity}", method = RequestMethod.GET)
//    public List<Pothole> getPotholesBySeverity(@PathVariable String severity) {
//        return potholeDao.getPotholesBySeverity(severity);
//    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Pothole createPothole(@RequestBody Pothole createdPothole, Principal principal) {
        return potholeDao.createPothole(createdPothole, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Pothole updatePotholeBasic(@RequestBody Pothole pothole, @PathVariable int id) throws PotholeNotFoundException{
        return potholeDao.updateBasic(pothole, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @RequestMapping(path = "/employee/{id}", method = RequestMethod.PUT)
    public Pothole updatePotholeFull(@RequestBody Pothole pothole, @PathVariable int id) throws PotholeNotFoundException{
        return potholeDao.updateFull(pothole, id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public boolean deletePotholeBasic(@PathVariable int id, Principal principal) throws PotholeNotFoundException, UserDoesNotOwnPotholeException {
        return potholeDao.deletePotholeBasic(id, principal.getName());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @RequestMapping(path = "/employee/{id}", method = RequestMethod.DELETE)
    public boolean deletePotholeFull(@PathVariable int id) throws PotholeNotFoundException {
        return potholeDao.deletePotholeFull(id);
    }

}
