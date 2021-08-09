package com.techelevator.dao;

import com.techelevator.exception.UserDoesNotOwnPotholeException;
import com.techelevator.model.Pothole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class JdbcPotholeDaoTests extends FinalCapstoneDaoTests {

    private static final Pothole POTHOLE_1 = new Pothole(99L,5,"48.18","79.73","105 Road St.","Squirrel Hill",LocalDate.now(),LocalDate.now(),LocalDate.now(),"medium","uninspected","0-1ft","no notes");

    private JdbcPotholeDao sut;
    private Pothole testPothole;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcPotholeDao(jdbcTemplate);
        testPothole = new Pothole(100L,5,"44.38","99.33","104 Road St.","Squirrel Hill",LocalDate.now(),LocalDate.now(),LocalDate.now(),"medium","uninspected","0-1ft","no notes");
    }

    @Test
    public void getPotholeById_returns_correct_pothole_for_id() {
        Pothole pothole = sut.getPotholeById(99);
        Assert.assertNotNull("getPotholesById returned null", pothole);
        assertPotholesMatch("getPotholesById returned wrong or partial data", POTHOLE_1, pothole);
    }

    public void assertPotholesMatch(String message, Pothole expected, Pothole actual) {
        Assert.assertEquals(message, expected.getPotholeId(), actual.getPotholeId());
        Assert.assertEquals(message, expected.getSubmitterId(), actual.getSubmitterId());
        Assert.assertEquals(message, expected.getLat(), actual.getLat());
        Assert.assertEquals(message, expected.getLon(), actual.getLon());
        Assert.assertEquals(message, expected.getAddr(), actual.getAddr());
        Assert.assertEquals(message, expected.getNeighborhood(), actual.getNeighborhood());
        Assert.assertEquals(message, expected.getDateCreated(), actual.getDateCreated());
        Assert.assertEquals(message, expected.getCurrentStatus(), actual.getCurrentStatus());
        Assert.assertEquals(message, expected.getDimensions(), actual.getDimensions());
        Assert.assertEquals(message, expected.getNotes(), actual.getNotes());
        Assert.assertEquals(message, expected.getDateInspected(), actual.getDateInspected());
        Assert.assertEquals(message, expected.getDateRepaired(), actual.getDateRepaired());
        Assert.assertEquals(message, expected.getSeverity(), actual.getSeverity());

    }

    @Test
    public void getAllPotholes_returns_list_of_all_potholes() {
        List<Pothole> potholes = sut.getAllPotholes();

        Assert.assertEquals("getAllPotholes failed to return all potholes", potholes.size(), potholes.size());
        assertPotholesMatch("getAllPotholes returned wrong or partial data", POTHOLE_1, potholes.get(0));

    }

    @Test
    public void createPothole_returns_pothole_with_id_and_expected_values() {
        Pothole createdPothole = sut.createPothole(testPothole, "employee");

        Assert.assertNotNull("createPothole returned null", createdPothole);

        long newId = createdPothole.getPotholeId();
        Assert.assertTrue("createProject failed to return a pothole with an id", newId > 0);

        testPothole.setPotholeId(98L);
        assertPotholesMatch("createPothole returned pothole with wrong or partial data", testPothole, createdPothole);
    }
    
    @Test
    public void deletePotholeFull_deleted_pothole_cant_be_retrieved() {
        sut.deletePotholeFull(98);

        Pothole pothole = sut.getPotholeById(98);
        Assert.assertNull("deleteProject failed to remove project from database", pothole);

        List<Pothole> potholes = sut.getAllPotholes();
        Assert.assertEquals("deletePothole left too many potholes in database", 1, potholes.size());
        assertPotholesMatch("deletePothole deleted wrong pothole", POTHOLE_1, potholes.get(0));
    }

    @Test
    public void deletePotholeBasic_deleted_pothole_cant_be_retrieved() throws UserDoesNotOwnPotholeException {
        sut.deletePotholeBasic(98, "employee");

        Pothole pothole = sut.getPotholeById(98);
        Assert.assertNull("deleteProject failed to remove project from database", pothole);

        List<Pothole> potholes = sut.getAllPotholes();
        Assert.assertEquals("deletePothole left too many potholes in database", 1, potholes.size());
        assertPotholesMatch("deletePothole deleted wrong pothole", POTHOLE_1, potholes.get(0));
    }

}
