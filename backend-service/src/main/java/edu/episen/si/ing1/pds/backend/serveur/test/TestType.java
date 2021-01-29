package edu.episen.si.ing1.pds.backend.serveur.test;

import java.util.Arrays;

public enum TestType {
    Instance(0),
    Create(1),
    Read(2),
    Update(3),
    Delete(4),
    Loop(5);

    private int type;

    TestType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public TestType getType(int value) {
        return (value > 0) ? Arrays.stream(TestType.values())
                .filter(testType -> testType.getType() == value)
                .findFirst()
                .orElse(null)
                : null;
    }
}
