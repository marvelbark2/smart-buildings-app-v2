package edu.episen.si.ing1.pds.backend.server.workspace.cards.card.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models.CardRequest;
import edu.episen.si.ing1.pds.backend.server.workspace.shared.Services;

import java.util.List;
import java.util.Map;

public interface ICardService<Req, Res> extends Services<Req, Res> {
    List<Map> getItemAccessList(String serialId);
    Boolean accessByCard(ArrayNode list, Integer card_id);
    Map<Map, List> getAccessList(Integer card_id);
    List<Map> getCardHistory(String serialNumber);
    ArrayNode treeView(CardRequest request);
}
