package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models;

import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.Role;

/*
* Entity Class
* */

public class Users {
    // Private key
    private Integer userId;
    // Public Key
    private String userUId;
    private String name;
    private Role role;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
