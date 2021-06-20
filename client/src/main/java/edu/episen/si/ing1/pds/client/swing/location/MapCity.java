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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;



public class MapCity implements Way {
	private List<Integer> bui_list=  new ArrayList<Integer>();
	private List<Integer> floor_list=  new ArrayList<Integer>();
	private List<String> state_list=  new ArrayList<String>();
	private List<String> type_list=  new ArrayList<String>();
	private List<Integer> nbwsp_list=  new ArrayList<Integer>();
	private Integer nbwp= 0;
	
	public void begin(LocationMenu men) {
		JPanel panelmap = men.getApp().getContext();
		JPanel panm = new JPanel(new BorderLayout());
		JPanel panm_north = new JPanel();
		JPanel panm_center = new JPanel();
		JLabel bld =new JLabel("Batiment : ");
		JLabel flr =new JLabel("Etage : ");
		JTextField build = new JTextField(10);
		JTextField floor = new JTextField(10);
		
		JButton validplace = new JButton("valider");
		
		
		panm_north.add(bld);
		panm_north.add(build);
		panm_north.add(flr);		
		panm_north.add(floor);
		panm_north.add(validplace);
		
		validplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(build.getText().equals("")||floor.getText().equals("")) {
					JOptionPane.showMessageDialog(new JPanel(), "Tous les champs ne sont pas bien renseignes", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else {
				panm_center.removeAll();
				bui_list.clear();
				Integer bui = Integer.valueOf(build.getText());
				//System.out.print(bui);
				Integer floo = Integer.valueOf(floor.getText());
				//System.out.print(floo);
				
				Request request = new Request();
				request.setEvent("buildings_list");
				Response response = Utils.sendRequest(request);
		        List<Map> data =(List<Map>) response.getMessage();
		        for(Map ex:data) {
		        	bui_list.add(Integer.valueOf(ex.get("name").toString()));
		        }
		        //System.out.print(bui_list);
		        
		        if(bui_list.contains(bui)) {
		        	floor_list.clear();
		        	//System.out.print("cake");
		        	
		        	Request req = new Request();
		            req.setEvent("floors_list");
		            Map<String,Object> hmi = new HashMap<>();
		     		hmi.put("building_name", bui);
		     		req.setData(hmi);
		            Response res = Utils.sendRequest(req);
		            List<Map> dat =(List<Map>) res.getMessage();
			        for(Map ex:dat) {
			        	floor_list.add(Integer.valueOf(ex.get("number").toString()));
			        }
			        
			       // System.out.print(floor_list);
		           if(floor_list.contains(floo)) {
		        	   
		        	   Request request2 = new Request();
		        	     request2.setEvent("numb_workspace");
		        	     Map<String, Object> rnb = new HashMap<>();
		        	     rnb.put("building_nb",bui);
		        	     rnb.put("floor_nb",floo);
		        	     request2.setData(rnb);
		        	     Response response2 = Utils.sendRequest(request2);
		        			List<Map> data2=(List<Map>) response2.getMessage();
		        			for(Map ex:data2) {
		        				int k = Integer.valueOf(ex.get("nb_wp").toString());
		        				nbwp=k;
		        			}
		        	   
		        			panm_center.setLayout(new GridLayout(nbwp/2, 1,20,20));
		        			state_list.clear();
		        			nbwsp_list.clear();
		        			type_list.clear();
		        			Request request3 = new Request();
		        			request3.setEvent("wp_esp");
		        			Map<String, Object> rwp = new HashMap<>();
			        	     rwp.put("building_nb",bui);
			        	     rwp.put("floor_nb",floo);
			        	     request3.setData(rwp);
			        	     Response response3 = Utils.sendRequest(request3);
			        	     List<Map> data3=(List<Map>) response3.getMessage();
			        			for(Map ex:data3) {
			        				state_list.add(ex.get("state").toString());
			        				nbwsp_list.add(Integer.valueOf(ex.get("numb").toString()));
			        				type_list.add(ex.get("type").toString());
			        			}
			        	  
			        	     
		        			int x = 0;
		        			for (int i = 0; i<1;i++) {
								for (int j = 0; j< nbwp;j++) {
									
									Box box = Box.createVerticalBox();
									//on fait la requete pour recupérer les salles
									String state = state_list.get(x);
									if(state.equals("disponible")) {
										box.setBorder(new LineBorder(Color.green));
										box.setBackground(Color.green);
									}else {
										box.setBorder(new LineBorder(Color.red));
										box.setBackground(Color.red);
									}
									box.add(new JLabel("salle "+ nbwsp_list.get(x)+ " : "+ type_list.get(x)));
									x++;
									panm_center.add(box);
								}
							}
		        			
		        	   //System.out.print(nbwp);
		        	   panm_center.invalidate();
		        	      panm_center.validate();
		        	      panm_center.repaint();
		        	  
		           } else {
		        	   JOptionPane.showMessageDialog(new JPanel(), "les étages possibles pour le bâtiment "+ bui+" sont : "+ floor_list, "Erreur", JOptionPane.ERROR_MESSAGE);
		           }
		        	
		        	
		        	
		        } else {
		        	JOptionPane.showMessageDialog(new JPanel(), "les batiments possibles sont : "+ bui_list, "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
			}catch (NumberFormatException f) {
				JOptionPane.showMessageDialog(new JPanel(), "Tous les champs ne sont pas bien renseignes, mauvais type", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
			}});
		
		
		
		panm.add(panm_center,BorderLayout.CENTER);
        panm.add(panm_north,BorderLayout.NORTH);
           
   
	panelmap.add(panm);
		
	}
	
}
