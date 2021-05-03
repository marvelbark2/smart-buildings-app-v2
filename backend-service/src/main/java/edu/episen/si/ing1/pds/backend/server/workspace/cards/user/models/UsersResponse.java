package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models;

import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleResponse;

public class UsersResponse {
    private String userUId;
    private String name;
    private RoleResponse role;

    public UsersResponse(Users user) {
        this.userUId = user.getUserUId();
        this.name = user.getName();

        if(user.getRole() != null)
            this.role = new RoleResponse(user.getRole());
    }

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

    public RoleResponse getRole() {
        return role;
    }

    public void setRole(RoleResponse role) {
        this.role = role;
    }
}
