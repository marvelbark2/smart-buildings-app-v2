package edu.episen.si.ing1.pds.client.swing.location;


import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MapCity implements Way {
	private List<Map> buildings;
	private List<Map> floors;
	
	public void begin(LocationMenu men) {
		JPanel panelmap = men.getApp().getContext();
		JPanel panm = new JPanel(new BorderLayout());
		JPanel panm_north = new JPanel();
		
		// il me faut une requetes qui vont prendre la liste
		//des bâtiments et la mettre dans un JComboBox et uen qui fait la même avec les étage
		
		
		 
		
		String labelsbat[] = {/*liste des batiments*/};
		String labelsfloors[] = {/*liste des étages*/};
		
		//JComboBox<String> comboBoxbat = new JComboBox<>(labelsbat);
		//JComboBox<String> comboBoxfloors = new JComboBox<>(labelsfloors);
		JButton validplace = new JButton("valider");
		
		
		
			buildings = getBuildingList();
			JComboBox comboBuild= new JComboBox(new Vector(buildings));

	        comboBuild.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if(value instanceof Map) {
	                    setText(((Map) value).get("name").toString());
	                }
	                return this;
	            }
	        });
	        
	        
	        floors = getFloorList();
	        JComboBox comboFloor= new JComboBox(new Vector(floors));
	        comboFloor.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if(value instanceof Map) {
	                    setText(((Map) value).get("number").toString());
	                }
	                return this;
	            }
	        });
	        

	        panm_north.add(comboBuild);
	        panm_north.add(comboFloor);
		
		//panm_north.add(comboBoxbat);
		//panm_north.add(comboBoxfloors);
		panm_north.add(validplace);
		
		panm.add(panm_north,BorderLayout.NORTH);
		
		//il me fait une requete qui récupère tous les workspace et les états de cette selection
		//il me faut une requete qui recupere le nombre d'espace
		int k =4;//nb de salle
		
		validplace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i<2;i++) {
						for (int j = 0; j< k/*nb de salle*/;j++) {
							int x = 1;
							Box box = Box.createVerticalBox();
							//on fait la requete pour recupérer les salles
							String state = "";
							if(state.equals("disponble")) {
								box.setBorder(new LineBorder(Color.green));
							}else {
								box.setBorder(new LineBorder(Color.red));
							}
							box.add(new JLabel("salle "+ x));
							x++;
						}
					}
				}
		});
		
		panelmap.add(panm);
		};
		
		private List<Map> getBuildingList() {
		        Request req = new Request();
		        req.setEvent("buildings_list");
		        Response response = Utils.sendRequest(req);
		        return  (List<Map>) response.getMessage();
		    }
		
		private List<Map> getFloorList() {
	        Request request = new Request();
	        request.setEvent("floors_list");
	        Map<String, Integer> hm = new HashMap<>();
			hm.put("building_id", 2);
			request.setData(hm);
	        Response response = Utils.sendRequest(request);
	        return  (List<Map>) response.getMessage();
	    }
		/*Request request = new Request();
		request.setEvent("location_building_byid");


		Map<String, Integer> hm = new HashMap<>();
		hm.put("building_id", 2);
		request.setData(hm);

		Response response = Utils.sendRequest(request);

		Map<String, Object>  data = (Map<String, Object>) response.getMessage();

		JLabel name = new JLabel(data.get("name").toString());
		JButton id = new JButton(data.get("id").toString());

		panel.add(name);
		panel.add(id);*/
	}


