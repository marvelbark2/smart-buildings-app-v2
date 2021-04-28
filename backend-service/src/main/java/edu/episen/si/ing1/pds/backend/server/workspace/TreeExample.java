package edu.episen.si.ing1.pds.backend.server.workspace;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.pool.config.DBConfig;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class TreeExample {
    public static void main(String[] args) throws Exception {
        DBConfig.Instance.setEnv(false);
        Connection connection = new DataSource(20).getConnection();
        String sql = "SELECT b.id_buildings, b.name, f.floor_number,workspace.id_workspace, workspace.workspace_type FROM workspace\n" +
                "join floors f on f.id_floor = workspace.floor_number\n" +
                "join buildings b on b.id_buildings = f.building_number";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        Map<Map, List> data = new HashMap();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("r");
//        DefaultMutableTreeNode first = new DefaultMutableTreeNode("first");
//        DefaultMutableTreeNode second = new DefaultMutableTreeNode("second");
//        DefaultMutableTreeNode third = new DefaultMutableTreeNode("third");
//        DefaultMutableTreeNode fourth = new DefaultMutableTreeNode("fourth");
//        root.add(first);
//        first.add(second);
//        first.add(third);
//        third.add(fourth);




        while (rs.next()) {
            Map hm = new HashMap();
            hm.put("id_buildings", rs.getInt("id_buildings"));
            hm.put("name", rs.getString("name"));


            Map<Map, List> floorContainer = new HashMap<>();
            Map floor = new HashMap();
            floor.put("floor_number", rs.getInt("floor_number"));


            Map workspace = new HashMap();
            workspace.put("id_workspace", rs.getInt("id_workspace"));
            workspace.put("workspace_type", rs.getString("workspace_type"));


            if(!floorContainer.containsKey(floor)) {
                List<Map> workspaces = new ArrayList<>();
                workspaces.add(workspace);
                floorContainer.put(floor, workspaces);
            } else {
                floorContainer.get(floor).add(workspace);
            }
            if(!data.containsKey(hm)) {
                List<Map> floors = new ArrayList<>();
                floors.add(floorContainer);
                data.put(hm , floors);
            } else {
                data.get(hm).add(floorContainer);
            }
        };

        for (Map building: data.keySet()) {
            DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(building.get("name"));
            for (Object floor: data.get(building)) {
                Map<Map, List> floorN = (Map) floor;
                for (Map<Map, List> works: floorN.keySet()) {
                    DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode("Etage " + works.get("floor_number"));
                    buildNode.add(floorNode);
                    for (List<Map> dataN: floorN.values()) {
                        for (Map workspace: dataN) {
                            DefaultMutableTreeNode workNode = new DefaultMutableTreeNode(workspace.get("workspace_type"));
                            floorNode.add(workNode);
                        }

                    }
                }
            }
            root.add(buildNode);
        }
        JTree tree = new JTree(root);

        tree.setRootVisible(false);

        JFrame frame = new JFrame("Tree Test");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        frame.add(tree);
        frame.pack();


    }

}
