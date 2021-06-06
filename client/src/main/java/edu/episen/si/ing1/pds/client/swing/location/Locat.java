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


/*public class Locat implements Way {
	JCheckBox bureau_ferme =new JCheckBox("bureau ferme");
    JCheckBox openspace =new JCheckBox("openspace");
    JTextField tailleMax = new JTextField(10);
    JTextField nbBureau = new JTextField(10);
    JTextField nbOpenspace = new JTextField(10);
    JTextField tailleMin = new JTextField(10);
    JTextField persMax = new JTextField(10);
    JTextField prixMax = new JTextField(10);
    JCheckBox screen =new JCheckBox("ecran");
    JCheckBox captor =new JCheckBox("capteur");
    JCheckBox prise =new JCheckBox("prise");
    
    int nbreserv = 0;
    int answer =5;
    JLabel[] proposition = new JLabel[3];
    JButton[] reserv = new JButton[3];
    
    
    JButton valid =new JButton("valider");

	public void begin(LocationMenu men) {
		JPanel panrech = men.getApp().getContext();
		 JPanel p = new JPanel( );
		 
		 JLabel Nbdesk = new JLabel("nombre de bureau fermé",JLabel.LEFT);
		 Nbdesk.setLabelFor(nbBureau);
		 
		 JLabel Nbopenspace = new JLabel("nombre d'openspace",JLabel.LEFT);
		 Nbopenspace.setLabelFor(nbOpenspace);
		
		 JLabel Tmin = new JLabel("taille minimale", JLabel.LEFT);
		    Tmin.setLabelFor(tailleMax);
		    
		    JLabel Tmax = new JLabel("taille maximale", JLabel.LEFT);
		    Tmax.setLabelFor(tailleMin);
		 
		    JLabel PersMax = new JLabel("nombre de personne", JLabel.LEFT);
		    PersMax.setLabelFor(persMax);
		 
		    JLabel Pmax = new JLabel("prix maximum", JLabel.LEFT);
		    Pmax.setLabelFor(prixMax);
		    
		   
		    JButton retButton = new JButton("retour");
		    p.setLayout(new GridLayout(12, 1));
		    
		    
		
		    
		    valid.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//getting the text from the different criteria we ask in the first interface
					String Numbdesk = nbBureau.getText();
					String NumbOp = nbOpenspace.getText();
					int tmin = Integer.valueOf(tailleMin.getText()); 
					int tmax = Integer.valueOf(tailleMax.getText());
					int persmax = Integer.valueOf(persMax.getText());
					int prixmax = Integer.valueOf(prixMax.getText());
					
					
					//initialing some variable which will receive the sums of workspace
					String listesp = "";
					//int totnbdesk = 0;
					//int totnbopensp = 0;
					int totsize = 0;
					int totpers = 0;
					int totprice = 0;
					
					p.removeAll();
					
					for (int i = 0; i<3;i++) {// this loop is made three time, each time is a different offer
						proposition[i]=new JLabel("");
						
						
						
						//as long as the criteria are respected, the workspace are selected one by one  
				while(totsize<tmax && totpers<persmax &&totprice<prixmax ){
					if(bureau_ferme.isSelected() && openspace.isSelected()) {
						
						Request request=new Request();
						request.setEvent("random_offer");
						Response response = Utils.sendRequest(request);
						Map<String,Object> data=(Map<String, Object>) response.getMessage();
						//totnbdesk = totnbdesk + 0;
						//totnbopensp = totnbopensp +  0;
					 	totsize = totsize + Integer.valueOf(data.get("size").toString());
						totpers = totpers + Integer.valueOf(data.get("employee").toString());;
						totprice = totprice + Integer.valueOf(data.get("price").toString());;
						System.out.println(totsize);
						listesp = listesp +" , "+String.valueOf(data.get("id").toString());
						
					} else if(bureau_ferme.isSelected() && !openspace.isSelected()) {
						Request request=new Request();
						request.setEvent("random_offer_desk");
						Response response = Utils.sendRequest(request);
						Map<String,Object> data=(Map<String, Object>) response.getMessage();
						//totnbdesk = totnbdesk + 0;
						//totnbopensp = totnbopensp +  0;
					 	totsize = totsize + Integer.valueOf(data.get("size").toString());
						totpers = totpers + Integer.valueOf(data.get("employee").toString());;
						totprice = totprice + Integer.valueOf(data.get("price").toString());;
						
						listesp = listesp +" , "+String.valueOf(data.get("id").toString());
					} else {
						Request request=new Request();
						request.setEvent("random_offer_openspace");
						Response response = Utils.sendRequest(request);
						Map<String,Object> data=(Map<String, Object>) response.getMessage();
					
					
					//totnbdesk = totnbdesk + 0;
					//totnbopensp = totnbopensp +  0;
				 	totsize = totsize + Integer.valueOf(data.get("size").toString());
					totpers = totpers + Integer.valueOf(data.get("employee").toString());;
					totprice = totprice + Integer.valueOf(data.get("price").toString());;
					
					listesp = listesp +" , "+String.valueOf(data.get("id").toString());
					}
				}
					
					
					
					
					
					
					
					
					
				
					
						// the text is changing about according to the different workspace that are selected
					    	  proposition[i].setText("cette offre contient les salles: "+ listesp+ " dont  x bureau fermé et y"
					    	  		+ " openspace pour une taille de "+
							totsize+" metres carrés et un prix de "+totprice +" euros, il peut contenir "+totpers+" employés ");
					    	  p.add(proposition[i]);
					    	  reserv[i]= new JButton("réserver"); 
					          p.add(reserv[i]);
			
								listesp = "";
								// totnbdesk = 0;
								// totnbopensp = 0;
								totsize = 0;
								totpers = 0;
								totprice = 0;
    
								//adding the button in order to do the reservation
					          reserv[i].addActionListener(new ActionListener(){
					        	  public void actionPerformed(ActionEvent e) {
					        		  nbreserv++;
					        		  
					        		  Request request = new Request();
					        			request.setEvent("done_reservation");
					        			Map<String, Object> hm = new HashMap<>();
					        			//hm.put("list_workspace", listesp);--error
					        			hm.put("reserv_numb",nbreserv);
					        			//hm.put("id_compa",id_compa);--error
					        			//hm.put("id_worksp",listesp);--error
					        			request.setData(hm);
					        			

					        			Response response = Utils.sendRequest(request);

					        			Map<String, Object>  data = (Map<String, Object>) response.getMessage();
					        		  //reserv[i].setText("réservation acceptée !");--error
					        		  
					 
					  
					        		  
					        	  }
					          });
						}
						
					     p.add(retButton) ;
					     p.invalidate();
						 p.validate();
						p.repaint();
						
					
				}
			});
		    
		    
		    
		    retButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {// initialing again the first interface
					p.removeAll();
					
					p.add(bureau_ferme);
				   	p.add(openspace);
				    p.add(Nbdesk);
				    p.add(nbBureau);
				    p.add(Nbopenspace);
				    p.add(nbOpenspace);
				    p.add(Tmin);
				    p.add(tailleMin);
				    p.add(Tmax);
				    p.add(tailleMax);
				    p.add(PersMax);
				    p.add(persMax);
				    p.add(Pmax);
				    p.add(prixMax);  
				    p.add(screen);
				    p.add(prise);
				    p.add(captor);
				    p.add(valid);
					p.invalidate();
					 p.validate();
					 p.repaint();
				}
			});
		    
		 
		    //initialing the first interface     
		        
		    p.add(bureau_ferme);
		   	p.add(openspace);
		    p.add(Nbdesk);
		    p.add(nbBureau);
		    p.add(Nbopenspace);
		    p.add(nbOpenspace);
		    p.add(Tmin);
		    p.add(tailleMin);
		    p.add(Tmax);
		    p.add(tailleMax);
		    p.add(PersMax);
		    p.add(persMax);
		    p.add(Pmax);
		    p.add(prixMax); 
		    p.add(screen);
		    p.add(prise);
		    p.add(captor);
		    p.add(valid);
		    panrech.add(p);
	}

}*/

public class Locat implements Way {
	

	JCheckBox bureau_ferme =new JCheckBox("bureau ferme");
    JCheckBox openspace =new JCheckBox("openspace");
    JTextField tailleMax = new JTextField(10);
    JTextField nbBureau = new JTextField(10);
    JTextField nbOpenspace = new JTextField(10);
    JTextField tailleMin = new JTextField(10);
    JTextField persMax = new JTextField(10);
    JTextField prixMax = new JTextField(10);
    JCheckBox screen =new JCheckBox("ecran");
    JCheckBox captor =new JCheckBox("capteur");
    JCheckBox prise =new JCheckBox("prise");
    
    int nbreserv = 0;
    int answer =5;
    JLabel[] proposition = new JLabel[3];
    JButton[] reserv = new JButton[3];
    
    
    
    JButton valid =new JButton("valider");
    
    public void begin(LocationMenu men) {
		JPanel panrech = men.getApp().getContext();
		 JPanel p = new JPanel( );
		 
		 JLabel Nbdesk = new JLabel("nombre de bureau fermé",JLabel.LEFT);
		 Nbdesk.setLabelFor(nbBureau);
		 
		 JLabel Nbopenspace = new JLabel("nombre d'openspace",JLabel.LEFT);
		 Nbopenspace.setLabelFor(nbOpenspace);
		
		 JLabel Tmin = new JLabel("taille minimale", JLabel.LEFT);
		    Tmin.setLabelFor(tailleMax);
		    
		    JLabel Tmax = new JLabel("taille maximale", JLabel.LEFT);
		    Tmax.setLabelFor(tailleMin);
		 
		    JLabel PersMax = new JLabel("nombre de personne", JLabel.LEFT);
		    PersMax.setLabelFor(persMax);
		 
		    JLabel Pmax = new JLabel("prix maximum", JLabel.LEFT);
		    Pmax.setLabelFor(prixMax);
		    
		   
		    JButton retButton = new JButton("retour");
		    p.setLayout(new GridLayout(12, 1));
		    
		    valid.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//getting the text from the different criteria we ask in the first interface
					String Numbdesk = nbBureau.getText();
					String NumbOp = nbOpenspace.getText();
					int tmin = Integer.valueOf(tailleMin.getText()); 
					int tmax = Integer.valueOf(tailleMax.getText());
					int persmax = Integer.valueOf(persMax.getText());
					int prixmax = Integer.valueOf(prixMax.getText());
					
					
					//initialing some variable which will receive the sums of workspace
					
					
					p.removeAll();
					
					List <Integer> liste0= new ArrayList<Integer>();
			         List <Integer> liste1= new ArrayList<Integer>();
			          List <Integer> liste2= new ArrayList<Integer>();
			          
					for (int i = 0; i<3;i++) {// this loop is made three time, each time is a different offer
						List <Integer> listesp = new ArrayList<Integer>();
						listesp.clear();
						//String listesp = "";
					//int totnbdesk = 0;
					//int totnbopensp = 0;
					int totsize = 0;
					int totpers = 0;
					int totprice = 0;
						
						proposition[i]=new JLabel("");
						reserv[i]= new JButton("réserver");
						while(totsize<tmax && totpers<persmax &&totprice<prixmax ){
						
						if(bureau_ferme.isSelected() && openspace.isSelected()) {
							
							Request request=new Request();
							request.setEvent("random_offer");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
							
							//Map<String,Object> data=(Map<String, Object>) response.getMessage();
							//totnbdesk = totnbdesk + 0;
							//totnbopensp = totnbopensp +  0;
						 	
								totsize = totsize + Integer.valueOf(ex.get("size").toString());
							totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
							totprice = totprice + Integer.valueOf(ex.get("price").toString());;
							System.out.println(totsize);
							/*if (listesp=="") {
								listesp=String.valueOf(ex.get("id").toString());
							} else {
							listesp = listesp +","+String.valueOf(ex.get("id").toString());
								}*/
							listesp.add(Integer.valueOf(ex.get("id").toString()));
						}  
						}else if(bureau_ferme.isSelected() && !openspace.isSelected()) {
							Request request=new Request();
							request.setEvent("random_offer_desk");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
							
								//Map<String,Object> data=(Map<String, Object>) response.getMessage();
							//totnbdesk = totnbdesk + 0;
							//totnbopensp = totnbopensp +  0;
						 	
							totsize = totsize + Integer.valueOf(ex.get("size").toString());
							totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
							totprice = totprice + Integer.valueOf(ex.get("price").toString());;	
							/*if (listesp=="") {
								listesp=String.valueOf(ex.get("id").toString());
							} else {
							listesp = listesp +","+String.valueOf(ex.get("id").toString());
								}*/
							listesp.add(Integer.valueOf(ex.get("id").toString()));
							}	
						} else {
							Request request=new Request();
							request.setEvent("random_offer_openspace");
							Response response = Utils.sendRequest(request);
							List<Map> data=(List<Map>) response.getMessage();
							for(Map ex:data) {
						//totnbdesk = totnbdesk + 0;
						//totnbopensp = totnbopensp +  0;
					 	totsize = totsize + Integer.valueOf(ex.get("size").toString());
						totpers = totpers + Integer.valueOf(ex.get("employee").toString());;
						totprice = totprice + Integer.valueOf(ex.get("price").toString());;
						/*if (listesp=="") {
							listesp=String.valueOf(ex.get("id").toString());
							 
						} else {
						listesp = listesp +","+String.valueOf(ex.get("id").toString());
							}*/
						listesp.add(Integer.valueOf(ex.get("id").toString()));
						}
						}
						
						proposition[i].setText("cette offre contient les salles: "+ listesp+ " dont  x bureau fermé et y"
				    	  		+ " openspace pour une taille de "+	totsize+" metres carrés et un prix de "+totprice +" euros, il peut contenir "+totpers+" employés ");
				    	  p.add(proposition[i]);
				    	   
				          p.add(reserv[i]);
				         
				          
				          
				          }
				        switch (i) {
				          case 0:
				        	  
				        		  liste0 =listesp;
				        		 
				        	  
				        	  break;
				          case 1:
				        	 
				        	 liste1=listesp;
				        	 System.out.print(liste1);
				        	  break;
				          case 2:
				        	  liste2=listesp;
				        	  break;
				          }
				       
				         }
				          final List <Integer> listeA= liste0;
				          final List <Integer> listeB= liste1;
				          final List <Integer> listeC= liste2;
				          int lA = listeA.size();
				          int lB = listeB.size();
				          int lC = listeC.size();
				          
				         reserv[0].addActionListener(new ActionListener(){
				        
				        	  public void actionPerformed(ActionEvent e) {
				        		  nbreserv++;
				        		  int b = 0;
				        		  for (int i : listeA) {
				        			  
				        		  Request request = new Request();
				        			request.setEvent("done_reservation");
				        			Map<String, Object> hm = new HashMap<>();
				        		
				        			hm.put("workspace_id", listeA.get(b));
				        			
				        			hm.put("reserv_numb",nbreserv);
				        			hm.put("liste_size", lA);
				        			request.setData(hm);
				        			
				        			b++;
				        			Response response = Utils.sendRequest(request);
				        		  }
				        			//Map<String, Object>  data = (Map<String, Object>) response.getMessage();
				        		  reserv[0].setText("réservation acceptée !");
				          
				          
				        	  }
				          });
				          reserv[1].addActionListener(new ActionListener(){
				        	
				        	  public void actionPerformed(ActionEvent e) {
				        		  nbreserv++;
				        		  int b = 0;
				        		  for (int i : listeB) {
				        		  Request request = new Request();
				        			request.setEvent("done_reservation");
				        			Map<String, Object> hm = new HashMap<>();
				        			hm.put("workspace_id", listeB.get(b));
				        			
				        			hm.put("reserv_numb",nbreserv);
				        			hm.put("liste_size", lB);
				        			request.setData(hm);
				        			
				        			b++;
				        			Response response = Utils.sendRequest(request);
				        		  }
				        			//Map<String, Object>  data = (Map<String, Object>) response.getMessage();
				        		  reserv[1].setText("réservation acceptée !");
				          
				          
				        	  }
				          });
				          reserv[2].addActionListener(new ActionListener(){
				        	  
				        	  public void actionPerformed(ActionEvent e) {
				        		  nbreserv++;
				        		  int b = 0;
				        		  for (int i : listeC) {

				                      try {
				                    	  Request request = new Request();
				                    	  request.setEvent("done_reservation");
				                    	  Map<String, Object> hm = new HashMap<>();
				                    	  hm.put("workspace_id", listeC.get(b));
				                    	  //hm.put("reserv_numb",nbreserv);
				                    	  //hm.put("liste_size", lC);
				                    	  request.setData(hm);
				        			
				                    	  b++;
				                    	  Response response = Utils.sendRequest(request);
				                    	  
				                      } catch(Exception event) {
				                    	  event.printStackTrace();
				                      }
				                      }
				        			//Map<String, Object>  data = (Map<String, Object>) response.getMessage();
				        		  reserv[2].setText("réservation acceptée !");
				          
				          
				        		  }  
				          });
				           
					p.add(retButton) ;
				       	  
					
					
				p.invalidate();
				p.validate();
				p.repaint();
			
				}});
						
		    
retButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {// initialing again the first interface
					p.removeAll();
					
					p.add(bureau_ferme);
				   	p.add(openspace);
				    p.add(Nbdesk);
				    p.add(nbBureau);
				    p.add(Nbopenspace);
				    p.add(nbOpenspace);
				    p.add(Tmin);
				    p.add(tailleMin);
				    p.add(Tmax);
				    p.add(tailleMax);
				    p.add(PersMax);
				    p.add(persMax);
				    p.add(Pmax);
				    p.add(prixMax);  
				    p.add(screen);
				    p.add(prise);
				    p.add(captor);
				    p.add(valid);
					p.invalidate();
					 p.validate();
					 p.repaint();
				}
			});
    
    p.add(bureau_ferme);
   	p.add(openspace);
    p.add(Nbdesk);
    p.add(nbBureau);
    p.add(Nbopenspace);
    p.add(nbOpenspace);
    p.add(Tmin);
    p.add(tailleMin);
    p.add(Tmax);
    p.add(tailleMax);
    p.add(PersMax);
    p.add(persMax);
    p.add(Pmax);
    p.add(prixMax); 
    p.add(screen);
    p.add(prise);
    p.add(captor);
    p.add(valid);
    panrech.add(p);
}
					
				}
				
		    
					
