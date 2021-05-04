package edu.episen.si.ing1.pds.backend.server.workspace.cards.access.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;

public interface IAccessService {
    void setCompanyId(int companyId);
    ArrayNode buildingList();
    ArrayNode floorList(int buildId);
    ArrayNode workspaceList(int floorId);
    ArrayNode equipmentList(int workspaceId);
    Boolean hasAccessToWorkspace(CardRequest request, int workspaceId);
    Boolean hasAccessToEquipment(CardRequest request, int equipmentId);
}
