package edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.pool.config.SqlConfig;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardsResponse;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.CardService;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Class to handle SQL queries and grabbing data from db for accessing
* Class act like a Repository / DAO
* */

public class AccessService implements IAccessService {
    private final Logger logger = LoggerFactory.getLogger(AccessService.class.getName());
    private final Connection connection;
    private int companyId;
    private SqlConfig sql = SqlConfig.Instance;
    private final ObjectMapper mapper = new ObjectMapper();

    public AccessService(Connection connection) {
        this.connection = connection;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public ArrayNode buildingList() {
        ArrayNode list = mapper.createArrayNode();
        String query = "SELECT distinct b.id_buildings, b.name  FROM buildings b\n" +
                "    join floors f on b.id_buildings = f.building_number\n" +
                "    join workspace w on f.id_floor = w.floor_number\n" +
                "    join reservations r on w.id_workspace = r.id_workspace\n" +
                "    join companies c on r.id_companies = c.id_companies\n" +
                "where c.id_companies = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> building = new HashMap<>();
                building.put("building_id", rs.getInt("id_buildings"));
                building.put("name", rs.getString("name"));
                list.add(mapper.valueToTree(building));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        return list;
    }

    @Override
    public ArrayNode floorList(int buildId) {
        ArrayNode list = mapper.createArrayNode();
        String query = "SELECT DISTINCT f.id_floor, concat('Etage ', f.floor_number) as floor  FROM buildings b\n" +
                "    join floors f on b.id_buildings = f.building_number\n" +
                "    join workspace w on f.id_floor = w.floor_number\n" +
                "    join reservations r on w.id_workspace = r.id_workspace\n" +
                "    join companies c on r.id_companies = c.id_companies\n" +
                "where c.id_companies = ? and b.id_buildings = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            statement.setInt(2, buildId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> floor = new HashMap<>();
                floor.put("floor_id", rs.getInt("id_floor"));
                floor.put("name", rs.getString("floor"));
                list.add(mapper.valueToTree(floor));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return list;
    }

    @Override
    public ArrayNode workspaceList(int floorId) {
        ArrayNode list = mapper.createArrayNode();
        String query = "SELECT id_workspace, workspace_label FROM workspace where floor_number = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, floorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> workspace = new HashMap<>();
                workspace.put("workspace_id", rs.getInt("id_workspace"));
                workspace.put("name", rs.getString("workspace_label"));
                list.add(mapper.valueToTree(workspace));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return list;
    }

    @Override
    public ArrayNode equipmentList(int workspaceId) {
        ArrayNode list = mapper.createArrayNode();
        String query = "SELECT we.id_workspace_equipments as id_equipments, concat(e.name, '-', we.gridheight, we.gridwidth, we.gridx, we.gridy) as name  FROM buildings b join floors f on b.id_buildings = f.building_number join workspace w on f.id_floor = w.floor_number join reservations r on w.id_workspace = r.id_workspace join companies c on r.id_companies = c.id_companies join workspace_equipments we on w.id_workspace = we.id_workspace join equipments e on we.equipment_id = e.id_equipments where c.id_companies = ? and w.id_workspace = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, companyId);
            statement.setInt(2, workspaceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> equipment = new HashMap<>();
                equipment.put("equipment_id", rs.getInt("id_equipments"));
                equipment.put("name", rs.getString("name"));
                list.add(mapper.valueToTree(equipment));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return list;
    }

    @Override
    public Boolean hasAccessToWorkspace(CardRequest request, int workspaceId) {
        Boolean response = false;
        ICardService cardService = new CardService(connection);
        cardService.setCompanyId(companyId);
        List<CardsResponse> cards = cardService.findAll();
        Integer cardId = 0;
        for (CardsResponse card: cards) {
            if(card.getCardUId().equals(request.getCardUId()) && card.isActive() == request.isActive()) {
                cardId = card.getCardId().intValue();
            }
        }
        try {
            String query = "SELECT * FROM isCardAccessibleToDesk(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cardId);
            statement.setInt(2, workspaceId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                response = rs.getBoolean(1);
                return response;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return response;
    }

    @Override
    public Boolean hasAccessToEquipment(CardRequest request, int equipmentId) {
        Boolean response = false;
        ICardService cardService = new CardService(connection);
        cardService.setCompanyId(companyId);
        List<CardsResponse> cards = cardService.findAll();
        Integer cardId = 0;
        for (CardsResponse card: cards) {
            if(card.getCardUId().equals(request.getCardUId()) && card.isActive() == request.isActive()) {
                cardId = card.getCardId().intValue();
            }
        }
        try {
            String query = "SELECT * FROM isCardAccessibleToEquipment(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cardId);
            statement.setInt(2, equipmentId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                response = rs.getBoolean(1);
                return response;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return response;
    }
}
