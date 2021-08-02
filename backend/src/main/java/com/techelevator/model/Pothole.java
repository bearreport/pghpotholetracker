package com.techelevator.model;
import java.time.LocalDate;

public class Pothole {

    private long potholeId;
    private int submitterId;
    private String lat;
    private String lon;
    private String addr;
    private String neighborhood;
    private LocalDate dateCreated;
    private String currentStatus;
    private String dimensions;
    private String notes;
    private LocalDate dateInspected;
    private LocalDate dateRepaired;
    private String severity;

 public Pothole() {}

 public Pothole(long potholeId, int submitterId, String lat, String lon, String addr, String neighborhood, LocalDate dateCreated,
                LocalDate dateInspected, LocalDate dateRepaired, String severity, String currentStatus, String dimensions, String notes) {
     this.potholeId = potholeId;
     this.submitterId = submitterId;
     this.lat = lat;
     this.lon = lon;
     this.addr = addr;
     this.neighborhood = neighborhood;
     this.dateCreated = dateCreated;
     this.currentStatus = currentStatus;
     this.dimensions = dimensions;
     this.notes = notes;
     this.dateInspected = dateInspected;
     this.dateRepaired = dateRepaired;
     this.severity = severity;
 }

    public LocalDate getDateInspected() {
        return dateInspected;
    }

    public void setDateInspected(LocalDate dateInspected) {
        this.dateInspected = dateInspected;
    }

    public LocalDate getDateRepaired() {
        return dateRepaired;
    }

    public void setDateRepaired(LocalDate dateRepaired) {
        this.dateRepaired = dateRepaired;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public long getPotholeId() {
        return potholeId;
    }

    public void setPotholeId(long potholeId) {
        this.potholeId = potholeId;
    }


    public int getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(int submitterId) {
        this.submitterId = submitterId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
