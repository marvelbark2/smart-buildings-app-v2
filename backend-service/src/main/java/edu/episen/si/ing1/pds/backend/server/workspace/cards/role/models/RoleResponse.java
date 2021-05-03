package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models;

public class RoleResponse {
    private int roleId;
    private String abbreviation;
    private String designation;
    private boolean enabled;

    public RoleResponse(Role role) {
        roleId = role.getRoleId();
        abbreviation = role.getAbbrevation();
        designation = role.getDesignation();
        enabled = role.isEnabled();
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
