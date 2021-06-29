package edu.episen.si.ing1.pds.backend.server.test;

import edu.episen.si.ing1.pds.backend.server.test.models.Companies;

import java.sql.SQLException;
import java.util.List;

public class CompanyController {
    private final Companies model = new Companies();
    public Object index() {
        try {
            return model.all();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
