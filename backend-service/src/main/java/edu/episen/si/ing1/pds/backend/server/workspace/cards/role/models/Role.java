package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models;

public class Role {
    private int roleId;
    private String abbrevation;
    private String designation;
    private boolean enabled;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
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

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", abbrevation='" + abbrevation + '\'' +
                ", designation='" + designation + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
