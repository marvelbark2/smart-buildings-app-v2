package edu.episen.si.ing1.pds.backend.server.workspace.users.services;

import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.Users;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersService implements Services<UsersRequest, UsersResponse> {
    private final Connection connection;

    public UsersService(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<UsersResponse> findAll() {
        List<UsersResponse> response = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getLong("userid"));
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
    public Optional<UsersResponse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean add(UsersRequest request) {
        return null;
    }

    @Override
    public Boolean delete(UsersRequest request) {
        return null;
    }
}