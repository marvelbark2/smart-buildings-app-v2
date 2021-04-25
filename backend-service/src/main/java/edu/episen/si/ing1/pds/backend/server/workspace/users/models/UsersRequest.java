package edu.episen.si.ing1.pds.backend.server.workspace.users.models;

public class UsersRequest {
    private String userUId;
    private String name;

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
}
