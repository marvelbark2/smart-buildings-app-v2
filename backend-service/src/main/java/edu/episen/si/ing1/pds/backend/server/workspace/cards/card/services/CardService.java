package edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.episen.si.ing1.pds.backend.server.db.pool.config.SqlConfig;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service.AccessService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service.IAccessService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.Cards;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardsResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.Users;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.UsersRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/*
* DAO class
* Interaction with db
* */

public class CardService implements ICardService<CardRequest, CardsResponse> {
    private final Logger logger = LoggerFactory.getLogger(CardService.class.getName());
    private final Connection connection;
    private int companyId;
    private SqlConfig sql = SqlConfig.Instance;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Optional<CardsResponse> findById(Integer id) {
        CardsResponse response = null;
        try {
            Cards card = new Cards();
            String query = sql.getCardSql("read");
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
                card.setActive(rs.getBoolean("active"));

                Users user = new Users();
                user.setUserId(rs.getInt("userid"));
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
            String query = sql.getCardSql("selectAll");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cards card = new Cards();
                card.setCardId(rs.getLong("cardid"));
                card.setCardUId(rs.getString("carduid"));
                card.setExpirable(rs.getBoolean("expirable"));
                card.setActive(rs.getBoolean("active"));

                if (rs.getDate("expired_date") != null) {
                    card.setExpiredDate(rs.getDate("expired_date").toLocalDate());
                } else {
                    card.setExpiredDate(null);
                }
                Users user = new Users();
                user.setUserId(rs.getInt("userid"));
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
        try {
            int user_id = findUserId(request);
            String query = sql.getCardSql("insert");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, request.getCardUId());
            statement.setLong(2, user_id);
            statement.setBoolean(3, request.isExpirable());
            if(request.getExpiredDate() != null)
                statement.setDate(4, Date.valueOf(request.getExpiredDate()));
            else
                statement.setDate(4, null);

            statement.setBoolean(5, request.isActive());

            return statement.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(CardRequest request) {
        try {
            String query = sql.getCardSql("delete");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, request.getCardUId());
            statement.setBoolean(2, request.isExpirable());
            statement.setBoolean(3, request.isActive());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(CardRequest request, Integer id) {
        String query = sql.getCardSql("update");
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            int user_id = findUserId(request);
            statement.setString(1, request.getCardUId());
            statement.setInt(2, user_id);
            statement.setBoolean(3, request.isExpirable());
            if(request.getExpiredDate() != null)
                statement.setDate(4, Date.valueOf(request.getExpiredDate()));
            else
                statement.setDate(4, null);
            statement.setBoolean(5, request.isActive());
            statement.setInt(6, id);

            int exec = statement.executeUpdate();
            return exec > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map> getItemAccessList(String serialId) {
        List<Map> list = new ArrayList<>();
        String q = sql.getCardSql("accessibility");
        try {
            PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, serialId);
            statement.setInt(2, companyId);
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

    @Override
    public Boolean accessByCard(ArrayNode list, Integer card_id) {
        List<JsonNode> spaces = new ArrayList<>();
        List<JsonNode> equipments = new ArrayList<>();

        Iterator<JsonNode> iterator = list.iterator();
        while (iterator.hasNext()) {
            JsonNode element = iterator.next();
            if(element.get("edited").asBoolean() && element.get("type").asText().equals("desk")) {
                spaces.add(element);
            } else if(element.get("edited").asBoolean() && element.get("type").asText().equals("equipment")) {
                equipments.add(element);
            }
        }

       try {
           boolean spaceResponse = true;
           for(JsonNode space : spaces) {
               String selectQuery = sql.getCardSql("deskpermission");
               PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
               selectStmt.setInt(1, card_id);
               selectStmt.setInt(2, space.get("id").asInt());

               ResultSet sRS = selectStmt.executeQuery();
               int count = -1;
               if(sRS.next())
                   count = sRS.getInt(1);

               if(count == 0) {
                   String query = sql.getCardSql("adddeskpermission");
                   PreparedStatement statement = connection.prepareStatement(query);
                   statement.setInt(1, card_id);
                   statement.setInt(2, space.get("id").asInt());
                   statement.setBoolean(3, space.get("accessible").asBoolean());
                   int rs = statement.executeUpdate();
                   spaceResponse = spaceResponse && rs > 0;
               } else {
                   String query = sql.getCardSql("updatedeskpermission");
                   PreparedStatement statement = connection.prepareStatement(query);
                   statement.setInt(2, card_id);
                   statement.setInt(3, space.get("id").asInt());
                   statement.setBoolean(1, space.get("accessible").asBoolean());
                   int rs = statement.executeUpdate();
                   spaceResponse = spaceResponse && rs > 0;
               }
           }

           boolean equipmentResponse = true;
           for (JsonNode equipment : equipments ) {
               String selectQuery = sql.getCardSql("equipmentpermission");
               PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
               selectStmt.setInt(1, card_id);
               selectStmt.setInt(2, equipment.get("id").asInt());

               ResultSet sRS = selectStmt.executeQuery();
               int count = -1;
               if(sRS.next())
                   count = sRS.getInt(1);

               if(count == 0) {
                   String query = sql.getCardSql("addequippermission");
                   PreparedStatement statement = connection.prepareStatement(query);
                   statement.setInt(1, card_id);
                   statement.setInt(2, equipment.get("id").asInt());
                   statement.setBoolean(3, equipment.get("accessible").asBoolean());
                   int rs = statement.executeUpdate();
                   equipmentResponse = equipmentResponse && rs > 0;
               } else {
                   String query = sql.getCardSql("updateequipermission");
                   PreparedStatement statement = connection.prepareStatement(query);
                   statement.setInt(2, card_id);
                   statement.setInt(3, equipment.get("id").asInt());
                   statement.setBoolean(1, equipment.get("accessible").asBoolean());
                   int rs = statement.executeUpdate();
                   equipmentResponse = equipmentResponse && rs > 0;
               }
           }

           return spaceResponse && equipmentResponse;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public Map<Map, List> getAccessList(Integer card_id) {
        Map<Map, List> data = new HashMap();

        String query = sql.getCardSql("permissionlist");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, card_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map hm = new HashMap();
                Map<Map, List> floorContainer = new HashMap<>();
                Map<Map, List> workspaceContainer = new HashMap<>();
                hm.put("name", rs.getString("building"));
                hm.put("root", true);

                Map floor = new HashMap();
                floor.put("floor", rs.getString("floor"));
                floor.put("root", false);

                Map workspace = new HashMap();
                workspace.put("workspace_type", rs.getString("workspace_label"));
                workspace.put("access", rs.getBoolean("desk_access"));

                if(!floorContainer.containsKey(floor)) {
                    List<Map> workspaces = new ArrayList<>();
                    workspaces.add(workspace);
                    floorContainer.put(floor, workspaces);
                } else {
                    floorContainer.get(floor).add(workspaceContainer);
                }
                if(!data.containsKey(hm)) {
                    List<Map> floors = new ArrayList<>();
                    floors.add(floorContainer);
                    data.put(hm , floors);
                } else {
                    data.get(hm).add(floorContainer);
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private int findUserId(CardRequest request) throws SQLException {
        int user_id = 0;
        UsersRequest usersRequest = request.getUser();
        String userQuery = sql.getCardSql("usercard");
        PreparedStatement userStmt = connection.prepareStatement(userQuery);
        userStmt.setString(1, usersRequest.getName());
        userStmt.setString(2, usersRequest.getUserUId());
        ResultSet rs = userStmt.executeQuery();
        if(rs.next())
            user_id = rs.getInt(1);
        return user_id;
    }

    public ArrayNode treeView(CardRequest request) {
        IAccessService service = new AccessService(connection);
        service.setCompanyId(companyId);

        ArrayNode buidings = service.buildingList();
        for (JsonNode building : buidings) {
            ArrayNode floors = service.floorList(building.get("building_id").asInt());
            for (JsonNode floor: floors) {
                ArrayNode workspaces = service.workspaceList(floor.get("floor_id").asInt());
                int i = 0;
                if(workspaces.size() > 0) {
                    for (Iterator<JsonNode> it = workspaces.iterator(); it.hasNext();) {
                        JsonNode workspace = it.next();
                        Boolean accessible = service.hasAccessToWorkspace(request, workspace.get("workspace_id").asInt());
                        ArrayNode equipments = service.equipmentList(workspace.get("workspace_id").asInt());
                        if(equipments.size() > 0) {
                            for (JsonNode equipment: equipments) {
                                Boolean accessibleEqui = service.hasAccessToEquipment(request, equipment.get("equipment_id").asInt());
                                ((ObjectNode) equipment).put("accessible", accessibleEqui);
                            }
                        }
                        ((ObjectNode) workspace).put("accessible", accessible);
                        ((ObjectNode) workspace).put("equipments", equipments);
                        //workspaces.remove(i);
                       // workspaces.add(workspace);
                        ++i;
                    }
                }

                ((ObjectNode) floor).put("workspaces", workspaces);
            }
            ((ObjectNode) building).put("floors", floors);
        }
        return buidings;
    }

    @Override
    public JsonNode lostCard(CardRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode accessList = mapper.createArrayNode();
        String queryWs = sql.getCardSql("accessdesk");
        String queryEq = sql.getCardSql("accessequip");
        try {
            int currentId = findCardId(request);
            PreparedStatement statement = connection.prepareStatement(queryWs);
            statement.setInt(1, currentId);
            ResultSet rsWs = statement.executeQuery();
            while (rsWs.next()) {
                Map hm = new HashMap();
                hm.put("type", "desk");
                hm.put("id", rsWs.getInt("deskid"));
                hm.put("edited", true);
                hm.put("accessible", rsWs.getBoolean("accessible"));
                accessList.add(mapper.valueToTree(hm));
            }
            PreparedStatement statement2 = connection.prepareStatement(queryEq);
            statement2.setInt(1, currentId);
            ResultSet rsEq = statement2.executeQuery();
            while (rsEq.next()) {
                Map hm = new HashMap();
                hm.put("type", "equipment");
                hm.put("id", rsEq.getInt("equipmentid"));
                hm.put("edited", true);
                hm.put("accessible", rsEq.getBoolean("accessible"));
                accessList.add(mapper.valueToTree(hm));
            }

            Boolean removed = delete(request);
            if(removed) {
                request.setCardUId(Utils.generateSerialNumber());
                Boolean inserted = add(request);
                if(inserted) {
                    int cardId = findCardId(request);
                    Optional<CardsResponse> newCard = findById(cardId);
                    if(newCard.isPresent()){
                        accessByCard(accessList, cardId);
                        return mapper.valueToTree(newCard.get());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Boolean activeCard(CardRequest request, Boolean action) {
        Boolean response = true;
        String query = sql.getCardSql("activate");
        int cardId = findCardId(request);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(2, cardId);
            statement.setBoolean(1, action);
            return response && statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private int findCardId(CardRequest request) {
        int card_id = 0;
        String query = sql.getCardSql("findcardid");
        try {
            PreparedStatement  statement = connection.prepareStatement(query);
            statement.setString(1, request.getCardUId());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                card_id = rs.getInt("cardid");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card_id;
    }

}
