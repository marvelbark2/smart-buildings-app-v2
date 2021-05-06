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
	private List<Map> numbw;
	
	public void begin(LocationMenu men) {
		JPanel panelmap = men.getApp().getContext();
		JPanel panm = new JPanel(new BorderLayout());
		JPanel panm_north = new JPanel();
		
		
	
		
		
		
		
		
		JButton validplace = new JButton("valider");
		
		
		//using the method getBuildingList and then put the answer in the JCombobox
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
	        
	        
	        
	       //sending the request in orderto get the List of the floors of the building we have already selected
	        Request request = new Request();
	        request.setEvent("floors_list");
	        Map<String, Object> hm = new HashMap<>();
			hm.put("building_id", comboBuild.getSelectedItem());
			request.setData(hm);
	        Response response = Utils.sendRequest(request);
	          
	    
	        //putting the floor in a JComboBox
	        floors = (List<Map>) response.getMessage();
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
	        
	        
	        	//asking the request for the number of workspace depending the floor and the buillding
		        Request request1 = new Request();
		        request1.setEvent("numb_workspace");
		        Map<String, Object> hm1 = new HashMap<>();
				hm1.put("building_id", comboBuild.getSelectedItem());
				hm1.put("floor_id",comboFloor.getSelectedItem());
				request1.setData(hm1);
		        Response response1 = Utils.sendRequest(request1);
		        numbw= (List<Map>) response1.getMessage();
	        
	        String x= numbw.get(0).toString();
	        int k = Integer.parseInt(x);//putting the answer in this variable
	        
	        JPanel panm_center = new JPanel(new GridLayout(k,2,20,20));

	        panm_north.add(comboBuild);
	        panm_north.add(comboFloor);
		
		
		panm_north.add(validplace);
		
		panm.add(panm_north,BorderLayout.NORTH);
		
		//here should be the request for the different state of each workspace
		
		
		//putting the color on the different workspace depending their state
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
							panm_center.add(box);
						}
					}
				}
		});
		panm.add(panm_center);
		panelmap.add(panm);
		};
		
		//method to get send the request for the list of building
		private List<Map> getBuildingList() {
		        Request req = new Request();
		        req.setEvent("buildings_list");
		        Response response = Utils.sendRequest(req);
		        return  (List<Map>) response.getMessage();
		    }
		
		
		
	    }
		
	


