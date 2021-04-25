package edu.episen.si.ing1.pds.backend.server.workspace.cards.models.relationship;

public class Buildings {
    private Integer id;
    private String abbreviation;
    private String designation;
    private Boolean enabled;

    public Buildings(Integer id, String abbreviation, String designation, Boolean enabled) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
    }
    public Buildings(String abbreviation, String designation, Boolean enabled) {
        this.id = null;
        this.abbreviation = abbreviation;
        this.designation = designation;
        this.enabled = enabled;
    }

    public Buildings() {
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
}
