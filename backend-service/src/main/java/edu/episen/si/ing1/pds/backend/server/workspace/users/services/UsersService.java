package edu.episen.si.ing1.pds.backend.server.workspace.users.services;

import edu.episen.si.ing1.pds.backend.server.pool.config.SqlConfig;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.Users;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersService implements Services<UsersRequest, UsersResponse> {
    private final Connection connection;
    private int companyId;
    private SqlConfig sql = SqlConfig.Instance;

    public UsersService(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<UsersResponse> findAll() {
        List<UsersResponse> response = new ArrayList<>();
        try {
            String query = sql.getUserSql("selectAll");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("userid"));
                user.setUserUId(rs.getString("user_uid"));
                user.setName(rs.getString("name"));
                response.add(new UsersResponse(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Optional<UsersResponse> findById(Integer id) {
        Optional<UsersResponse> user = null;
        String query = sql.getUserSql("read");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Users userEntity = new Users();
                userEntity.setUserId(rs.getInt("userid"));
                userEntity.setUserUId(rs.getString("user_uid"));
                userEntity.setName(rs.getString("name"));
                UsersResponse usersResponse = new UsersResponse(userEntity);
                user = Optional.of(usersResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Boolean add(UsersRequest request) {
        int response = 0;
        try {
            String query = sql.getUserSql("insert");

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, request.getUserUId());
            statement.setString(2, request.getName());
            statement.setInt(3, companyId);

            response = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  response > 0;
    }

    @Override
    public Boolean delete(UsersRequest request) {
        return null;
    }

    @Override
    public Boolean update(UsersRequest request, Integer id) {
        return null;
    }

    @Override
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
