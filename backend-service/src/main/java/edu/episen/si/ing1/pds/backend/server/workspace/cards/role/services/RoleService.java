package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.services;

import edu.episen.si.ing1.pds.backend.server.pool.config.SqlConfig;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.Role;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleService implements IRoleService<RoleRequest, RoleResponse> {
    private final Connection connection;
    private final Logger logger = LoggerFactory.getLogger(RoleService.class.getName());
    private int companyId;
    private final SqlConfig sql = SqlConfig.Instance;

    public RoleService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RoleResponse> findAll() {
        List<RoleResponse> roleList = new ArrayList<>();
        try {
            String query = sql.getRoleSql("selectAll");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("roleid"));
                role.setAbbreviation(rs.getString("abbreviation"));
                role.setDesignation(rs.getString("designation"));
                role.setEnabled(rs.getBoolean("enabled"));
                RoleResponse item = new RoleResponse(role);
                roleList.add(item);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return roleList;
    }

    @Override
    public Optional<RoleResponse> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Boolean add(RoleRequest request) throws SQLException {
        return null;
    }

    @Override
    public Boolean delete(RoleRequest request) {
        return null;
    }

    @Override
    public Boolean update(RoleRequest request, Integer id) {
        return null;
    }

    @Override
    public void setCompanyId(int companyId) {

    }
}
