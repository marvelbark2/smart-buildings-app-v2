package edu.episen.si.ing1.pds.backend.server.workspace.cards.services;

import edu.episen.si.ing1.pds.backend.server.workspace.cards.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.models.Cards;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.models.CardsResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.Users;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CardService implements Services<CardRequest, CardsResponse> {
    private final Connection connection;
    private int companyId;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;

    }

    public Optional<CardsResponse> findById(Long id) {
        CardsResponse response = null;
        try {
            Cards card = new Cards();
            String query = "SELECT * FROM cards c JOIN users u on u.userId = c.userId WHERE cardid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                card.setCardId(rs.getLong(1));
                card.setCardUId(rs.getString(2));
                card.setExpirable(rs.getBoolean(4));
                if (rs.getDate(5) != null) {
                    card.setExpiredDate(rs.getDate(5).toLocalDate());
                } else {
                    card.setExpiredDate(null);
                }
                Users user = new Users();
                user.setUserId(rs.getLong("userid"));
                user.setUserUId(rs.getString("user_uid"));
                user.setName(rs.getString("name"));
                card.setUser(user);
                response = new CardsResponse(card);
            } else
                if(card.getCardId() == null)
                    return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(response);
    }
    public List<CardsResponse> findAll() {
        List<CardsResponse> cards = new ArrayList<>();
        try {
            String query = "SELECT * FROM cards c JOIN users u on u.userId = c.userId and u.company_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cards card = new Cards();
                card.setCardId(rs.getLong("cardid"));
                card.setCardUId(rs.getString("carduid"));
                card.setExpirable(rs.getBoolean("expirable"));

                if (rs.getDate("expired_date") != null) {
                    card.setExpiredDate(rs.getDate("expired_date").toLocalDate());
                } else {
                    card.setExpiredDate(null);
                }
                Users user = new Users();
                user.setUserId(rs.getLong("userid"));
                user.setUserUId(rs.getString("user_uid"));
                user.setName(rs.getString("name"));

                card.setUser(user);

                CardsResponse cardsResponse = new CardsResponse(card);
                cards.add(cardsResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }

    public Boolean add(CardRequest request) {
        Boolean response = false;
        try {
            Long user_id = 0L;
            UsersRequest usersRequest = request.getUser();
            String userQuery = "SELECT userid from users where name LIKE ? and user_uid LIKE ?";
            PreparedStatement userStmt = connection.prepareStatement(userQuery);
            userStmt.setString(1, usersRequest.getName());
            userStmt.setString(2, usersRequest.getUserUId());
            ResultSet rs = userStmt.executeQuery();
            if(rs.next())
                user_id = rs.getLong(1);

            String query = "insert into cards (carduid, userid, expirable, expired_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, request.getCardUId());
            statement.setLong(2, user_id);
            statement.setBoolean(3, request.isExpirable());
            if(request.getExpiredDate() != null)
                statement.setDate(4, Date.valueOf(request.getExpiredDate()));
            else
                statement.setDate(4, null);
            response = statement.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return response;
    }

    @Override
    public Boolean delete(CardRequest request) {
        Boolean response = false;
        try {
            String query = "DELETE FROM cards where cardid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, request.getCardId());
            response = statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public List<Map> getItemAccessList(String serialId) {
        List<Map> list = new ArrayList<>();
        String sql = "SELECT * from getCardAccessibiliy(?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, serialId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(
                        Map.of(
                                "id", rs.getInt("id"),
                                "title", rs.getString("name"),
                                "type", rs.getString("type"),
                                "accessible", rs.getBoolean("access")
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

}
