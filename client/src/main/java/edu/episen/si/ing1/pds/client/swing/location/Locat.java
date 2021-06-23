package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.utils.Utils;




public class Locat implements Way {
	

	JCheckBox bureau_ferme =new JCheckBox("bureau ferme");
    JCheckBox openspace =new JCheckBox("openspace");
    JTextField tailleMax = new JTextField(10);
  
    JTextField persMax = new JTextField(10);
    JTextField prixMax = new JTextField(10);
   
    
    int nbreserv = 0;
    int answer =5;
    JLabel[] proposition = new JLabel[3];
    JButton[] reserv = new JButton[3];
    
    
    
    JButton valid =new JButton("valider");
    
    public void begin(LocationMenu men) {
		JPanel panrech = men.getApp().getContext();
		 JPanel p = new JPanel( );
		 
		
		    
		    JLabel Tmax = new JLabel("taille approximative souhaitée :", JLabel.LEFT);
		    Tmax.setLabelFor(tailleMax);
		 
		    JLabel PersMax = new JLabel("nombre d'employés approximatif :", JLabel.LEFT);
		    PersMax.setLabelFor(persMax);
		 
		    JLabel Pmax = new JLabel("prix total approximatif :", JLabel.LEFT);
		    Pmax.setLabelFor(prixMax);
		    
		   
		    JButton retButton = new JButton("retour");
		    p.setLayout(new GridLayout(6, 1));
		    
		    valid.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e) {
		    		try {
		    	if(!bureau_ferme.isSelected() && !openspace.isSelected()){
		    		JOptionPane.showMessageDialog(new JPanel(), "Tous les champs ne sont pas bien renseignes", "Erreur", JOptionPane.ERROR_MESSAGE);
		    		System.out.println("LUDO EST UN CON ");
		    	}else if (tailleMax.getText().equals("")||persMax.getText().equals("")||prixMax.getText().equals("")){
		    		JOptionPane.showMessageDialog(new JPanel(), "Tous les champs ne sont pas bien renseignes", "Erreur", JOptionPane.ERROR_MESSAGE);
		    	}else {
				
					
					
					int tmax = Integer.valueOf(tailleMax.getText());
					int persmax = Integer.valueOf(persMax.getText());
					int prixmax = Integer.valueOf(prixMax.getText());
					
					
					
					
					p.removeAll();
					
					List <Integer> liste0= new ArrayList<Integer>();
			         List <Integer> liste1= new ArrayList<Integer>();
			          List <Integer> liste2= new ArrayList<Integer>();
			          boolean x =true;
			          
					for (int i = 0; i<3;i++) {
						List <Integer> listesp = new ArrayList<Integer>();
						listesp.clear();
						
					int totsize = 0;
					int totpers = 0;
					int totprice = 0;
						
						proposition[i]=new JLabel("");
						reserv[i]= new JButton("réserver");
						
						while(totsize<tmax && totpers<persmax &&totprice<prixmax ){
						valid.setEnabled(x);
						if(bureau_ferme.isSelected() && openspace.isSelected()) {
							
							Request request=new Request();
							request.setEvent("random_offer");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
							
							
								int k = Integer.valueOf(ex.get("id").toString());
						 	if(!listesp.contains(k)) {
								totsize = totsize + Integer.valueOf(ex.get("size").toString());
							totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
							totprice = totprice + Integer.valueOf(ex.get("price").toString());;
							
							
							listesp.add(Integer.valueOf(ex.get("id").toString()));
							break;
						 	}
						}  
						}else if(bureau_ferme.isSelected() && !openspace.isSelected()) {
							Request request=new Request();
							request.setEvent("random_offer_desk");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
							
								int k = Integer.valueOf(ex.get("id").toString());
							 	if(!listesp.contains(k)) {
							totsize = totsize + Integer.valueOf(ex.get("size").toString());
							totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
							totprice = totprice + Integer.valueOf(ex.get("price").toString());;	
							
							listesp.add(Integer.valueOf(ex.get("id").toString()));
							}	}
						} else if(!bureau_ferme.isSelected() && openspace.isSelected()){
							Request request=new Request();
							request.setEvent("random_offer_openspace");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
						
								int k = Integer.valueOf(ex.get("id").toString());
							 	if(!listesp.contains(k)) {
					 	totsize = totsize + Integer.valueOf(ex.get("size").toString());
						totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
						totprice = totprice + Integer.valueOf(ex.get("price").toString());;
						
						listesp.add(Integer.valueOf(ex.get("id").toString()));
						}}
						} 
						
						if(x==true) {
						proposition[i].setText("<html>cette offre contient les salles: "+ listesp+ ""
				    	  		+ " pour une taille totale de "+	totsize+" metres carrés et un prix total de "+totprice +" euros, ils peuvent contenir un total de "+totpers+" employés</html> ");
				    	  p.add(proposition[i]);
				    	   
				          p.add(reserv[i]);
				         
				          
						}
				          }
				        switch (i) {
				          case 0:
				        	  
				        		  liste0 =listesp;
				        		 
				        	  
				        	  break;
				          case 1:
				        	 
				        	 liste1=listesp;
				        	 
				        	  break;
				          case 2:
				        	  liste2=listesp;
				        	  break;
				          }
				       
				         }
					if(x==true) {
							p.add(retButton) ;
					}
				          final List <Integer> listeA= liste0;
				          final List <Integer> listeB= liste1;
				          final List <Integer> listeC= liste2;
				        
				          
				         reserv[0].addActionListener(new ActionListener(){
				        
				        	  public void actionPerformed(ActionEvent e) {
				        		  if(reserv[0].getText().equalsIgnoreCase("réservation acceptée !")){
				  		    		JOptionPane.showMessageDialog(new JPanel(), "déjà réservé", "Erreur", JOptionPane.ERROR_MESSAGE);
				  		    	}else {
				        		
				        		  try {
					        		  Request request = new Request();
					        			request.setEvent("done_reservation");
					        			Map<String, Object> hm = new HashMap<>();
					        		
					        			hm.put("workspace_id", listeA);
					        			
					        			
					        			request.setData(hm);
					        			
					        			
					        			Response response = Utils.sendRequest(request);
					        		  } catch(Exception event) {
				                    	  event.printStackTrace();
				                      }
				        			
				        		  reserv[0].setText("réservation acceptée !");
				          
				          
				        	  }}
				          });
				          reserv[1].addActionListener(new ActionListener(){
				        	  public void actionPerformed(ActionEvent e) {
				        		  if(reserv[1].getText().equalsIgnoreCase("réservation acceptée !")){
					  		    		JOptionPane.showMessageDialog(new JPanel(), "déjà réservé", "Erreur", JOptionPane.ERROR_MESSAGE);
					  		    	}else {
				        	  try {
				        		  Request request = new Request();
				        			request.setEvent("done_reservation");
				        			Map<String, Object> hm = new HashMap<>();
				        		
				        			hm.put("workspace_id", listeB);
				        			
				        			
				        			request.setData(hm);
				        	 
				        			Response response = Utils.sendRequest(request);
				        			  } catch(Exception event) {
				                    	  event.printStackTrace();
				                      }
				        		  
				        		
				        		  reserv[1].setText("réservation acceptée !");
				          
				          
				        	  }}
				          });
				          reserv[2].addActionListener(new ActionListener(){
				        	  
				        	  public void actionPerformed(ActionEvent e) {
				        		  if(reserv[2].getText().equalsIgnoreCase("réservation acceptée !")){
					  		    		JOptionPane.showMessageDialog(new JPanel(), "déjà réservé", "Erreur", JOptionPane.ERROR_MESSAGE);
					  		    	}else {
					        	  try {
					        		  Request request = new Request();
					        			request.setEvent("done_reservation");
					        			Map<String, Object> hm = new HashMap<>();
					        		
					        			hm.put("workspace_id", listeC);
					        			
					        			
					        			request.setData(hm);
					        	 
					        			Response response = Utils.sendRequest(request);
					        			  } catch(Exception event) {
					                    	  event.printStackTrace();
					                      }
					        		  
					        		
					        		  reserv[2].setText("réservation acceptée !");
				          
				          
				        		  }  }
				          });
				           
					
				       	  
					valid.setEnabled(true);
					
				p.invalidate();
				p.validate();
				p.repaint();
				}
		    		}catch (NumberFormatException f) {
						JOptionPane.showMessageDialog(new JPanel(), "Tous les champs ne sont pas bien renseignes, mauvais type", "Erreur", JOptionPane.ERROR_MESSAGE);
		            }
		    	}
				});
		   		
		    
retButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					p.removeAll();
					
					p.add(bureau_ferme);
				   	p.add(openspace);
				 
				    p.add(Tmax);
				    p.add(tailleMax);
				    p.add(PersMax);
				    p.add(persMax);
				    p.add(Pmax);
				    p.add(prixMax);  
				  
				    p.add(valid);
					p.invalidate();
					 p.validate();
					 p.repaint();
				}
			});
    
    p.add(bureau_ferme);
   	p.add(openspace);
  
    p.add(Tmax);
    p.add(tailleMax);
    p.add(PersMax);
    p.add(persMax);
    p.add(Pmax);
    p.add(prixMax); 
   
    p.add(valid);
    panrech.add(p);
}
					
				}
				
		    
					
