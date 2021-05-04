package edu.episen.si.ing1.pds.backend.server.workspace.cards.user.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.*;
import edu.episen.si.ing1.pds.backend.server.pool.config.SqlConfig;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardsResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.CardService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.ICardService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.Role;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.role.models.RoleResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.Users;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.UsersRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.UsersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersService implements IUsersService<UsersRequest, UsersResponse> {
    private final Logger logger = LoggerFactory.getLogger(UsersService.class.getName());
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
                Role role = new Role();
                role.setRoleId(rs.getInt("roleid"));
                role.setAbbreviation(rs.getString("abbreviation"));
                role.setDesignation(rs.getString("designation"));
                role.setEnabled(rs.getBoolean("enabled"));

                Users user = new Users();
                user.setUserId(rs.getInt("userid"));
                user.setUserUId(rs.getString("user_uid"));
                user.setName(rs.getString("name"));
                user.setRole(role);
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

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, request.getUserUId());
            statement.setString(2, request.getName());
            statement.setInt(3, companyId);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                PreparedStatement roleAdding = connection.prepareStatement("INSERT INTO roles_users (roleid, userid) values (?, ?)");
                roleAdding.setInt(1, request.getRole().getRoleId());
                roleAdding.setInt(2, rs.getInt(1));

                response = roleAdding.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  response > 0;
    }

    @Override
    public Boolean delete(UsersRequest request) {
        Boolean response = false;
        try {
            String query = "DELETE FROM users WHERE user_uid = ? and name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, request.getUserUId());
            statement.setString(2, request.getName());
            response = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Boolean update(UsersRequest request, Integer id) {
        return null;
    }

    @Override
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public JsonNode findUser(UsersRequest user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ICardService<CardRequest, CardsResponse> cardsService = new CardService(connection);
        cardsService.setCompanyId(companyId);
        List<CardsResponse> cards = cardsService.findAll();
        Optional<CardsResponse> optionalUserCard = cards.stream().filter(card -> card.getUser().getUserUId().equals(user.getUserUId())).findFirst();
        ObjectNode tree = mapper.createObjectNode();
        JsonNode userNode = mapper.valueToTree(user);
        if(optionalUserCard.isPresent()) {
            CardsResponse userCard = optionalUserCard.get();
            JsonNode cardNode = mapper.valueToTree(userCard);
            tree.set("card", cardNode);
        }
        tree.set("user", userNode);
        return tree;
    }

    @Override
    public ArrayNode usersHasCard() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode list = mapper.createArrayNode();
        try {
            String query = "SELECT * FROM cards c JOIN users u ON u.userid = c.userid";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                UsersRequest user = new UsersRequest();
                user.setName(rs.getString("name"));
                user.setUserUId(rs.getString("user_uid"));
                UsersRequest roleAdded = getUserRole(user);
                JsonNode userNode = findUser(roleAdded);
                list.add(userNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private UsersRequest getUserRole(UsersRequest request) {
        List<UsersResponse> users = findAll();
        for (UsersResponse user: users) {
            if(user.getUserUId().equals(request.getUserUId())) {
                RoleRequest role = new RoleRequest();
                RoleResponse roleResponse = user.getRole();
                role.setRoleId(roleResponse.getRoleId());
                role.setAbbreviation(roleResponse.getAbbreviation());
                role.setDesignation(roleResponse.getDesignation());
                role.setEnabled(role.isEnabled());
                request.setRole(role);
            }

        }
        return request;
    }
}
