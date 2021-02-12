package edu.episen.si.ing1.pds.client.roles;

import java.util.Arrays;
import java.util.Optional;

public enum RoleType {
    Instance(0),
    ADMIN(1),
    EMPLOYEE(2),
    COMPANY(3),
    SG(4),
    GUEST(5);
    private int degree;

    private RoleType(int degree){
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }
    public RoleType findRole(int id) {
        return (id != 0)
                ? Arrays.stream(RoleType.values())
                        .filter(role -> role.getDegree() == id)
                        .findFirst()
                        .get()
                : null;
    }
}
