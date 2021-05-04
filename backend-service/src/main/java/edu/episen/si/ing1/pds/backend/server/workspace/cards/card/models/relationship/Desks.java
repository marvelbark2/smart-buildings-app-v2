package edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.relationship;

public class Desks {
    private Integer id;
    private String abbreviation;
    private String designation;
    private Boolean enabled;
    private Floors floor;

    public Desks(Integer id, String abbreviation, String designation, Boolean enabled, Floors floor) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
        this.floor = floor;
    }

    public Desks(String abbreviation, String designation, Boolean enabled, Floors floor) {
        this.id = null;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
        this.floor = floor;
    }

    public Desks() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Floors getFloor() {
        return floor;
    }

    public void setFloor(Floors floor) {
        this.floor = floor;
    }
}
