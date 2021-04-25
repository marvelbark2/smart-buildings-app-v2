package edu.episen.si.ing1.pds.backend.server.workspace.cards.models.relationship;

public class Floors {
    private Integer id;
    private String abbreviation;
    private String designation;
    private Boolean enabled;
    private Buildings building;

    public Floors(Integer id, String abbreviation, String designation, Boolean enabled, Buildings building) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
        this.building = building;
    }

    public Floors(String abbreviation, String designation, Boolean enabled, Buildings building) {
        this.id = null;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
        this.building = building;
    }

    public Floors() {
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

    public Buildings getBuilding() {
        return building;
    }

    public void setBuilding(Buildings building) {
        this.building = building;
    }
}
