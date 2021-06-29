package edu.episen.si.ing1.pds.backend.server.workspace.cards.role.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.episen.si.ing1.pds.backend.server.db.pool.config.SqlConfig;
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
    private final Logger logger = LoggerFactory.getLogger(RoleService.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    private final Connection connection;
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
            statement.setInt(1, companyId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("roleid"));
                role.setAbbreviation(rs.getString("abbreviation"));
                role.setDesignation(rs.getString("designation"));
                role.setEnabled(rs.getBoolean("enabled"));
                role.setUsersNumber(rs.getInt("users"));
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
        String query = sql.getRoleSql("update");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, request.getAbbreviation());
            statement.setString(2, request.getDesignation());
            statement.setBoolean(3, request.isEnabled());
            statement.setInt(4, id);
            int update = statement.executeUpdate();
            return update > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public ArrayNode accessList(RoleRequest request) {
        ArrayNode response = mapper.createArrayNode();
        String query = sql.getRoleSql("listAccess");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            statement.setInt(2, request.getRoleId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", rs.getInt("id"));
                node.put("name", rs.getString("name"));
                node.put("type", rs.getString("type"));
                node.put("access", rs.getBoolean("access"));
                node.put("modified", rs.getBoolean("modified"));
                response.add(node);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return response;
    }

    @Override
    public Boolean updateAccessList(ArrayNode list, RoleRequest request) {
        String insertDesk = sql.getRoleSql("patchDesk");
        String deleteDesk = sql.getRoleSql("breakRelationDesk");

        String insertEquip = sql.getRoleSql("patchEquip");
        String deleteEquip = sql.getRoleSql("breakRelationEquip");



        boolean response = true;
        try {
            for (JsonNode access: list) {
                if(access.get("modified").asBoolean()) {
                    if(access.get("type").asText().equals("workspace")) {
                        if(access.get("access").asBoolean()) {
                            PreparedStatement statement = connection.prepareStatement(insertDesk);
                            statement.setInt(1, request.getRoleId());
                            statement.setInt(2, access.get("id").asInt());
                            int result = statement.executeUpdate();
                            response = response && result > 0;
                        } else {
                            PreparedStatement statement = connection.prepareStatement(deleteDesk);
                            statement.setInt(1, request.getRoleId());
                            statement.setInt(2, access.get("id").asInt());
                            int result = statement.executeUpdate();
                            response = response && result > 0;
                        }
                    } else if(access.get("type").asText().equals("equipment")) {
                        if(access.get("access").asBoolean()) {
                            PreparedStatement statement = connection.prepareStatement(insertEquip);
                            statement.setInt(2, request.getRoleId());
                            statement.setInt(1, access.get("id").asInt());
                            int result = statement.executeUpdate();
                            response = response && result > 0;
                        } else {
                            PreparedStatement statement = connection.prepareStatement(deleteEquip);
                            statement.setInt(1, request.getRoleId());
                            statement.setInt(2, access.get("id").asInt());
                            int result = statement.executeUpdate();
                            response = response && result > 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return response;
    }
}
