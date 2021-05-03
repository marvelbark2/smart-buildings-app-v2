package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models;

import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleRequest;

public class UsersRequest {
    private String userUId;
    private String name;
    private RoleRequest role;

    public String getUserUId() {
        return userUId;
    }

    public void setUserUId(String userUId) {
        this.userUId = userUId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleRequest getRole() {
        return role;
    }

    public void setRole(RoleRequest role) {
        this.role = role;
    }
}
