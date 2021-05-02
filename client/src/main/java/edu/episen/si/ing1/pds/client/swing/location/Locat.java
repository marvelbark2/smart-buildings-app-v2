package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
					String Numbdesk = nbBureau.getText();
					String NumbOp = nbOpenspace.getText();
					int tmin = Integer.valueOf(tailleMin.getText()); 
					int tmax = Integer.valueOf(tailleMax.getText());
					int persmax = Integer.valueOf(persMax.getText());
					int prixmax = Integer.valueOf(prixMax.getText());
					
					String listesp = "";
					//int totnbdesk = 0;
					//int totnbopensp = 0;
					int totsize = 0;
					int totpers = 0;
					int totprice = 0;
					
					p.removeAll();
					
					for (int i = 0; i<3;i++) {
						proposition[i]=new JLabel("");
						
						
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
						listesp = listesp + data.get("id").toString();
						
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
						
						listesp = listesp + data.get("id").toString();
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
					
					listesp = listesp +" , "+ data.get("id").toString();
					}
				}
					
					
					// va chercher les données d'un espace et les mets dans un tableau dans l'ordre id,size,maxpers,price,type
					//si je deviens chaud je récupère le nom de la salle, so étage et son bâtiment pour en faire une liste
					
					
					
					
					
					
				
					
						// le texte change en fonction du bâtiment
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
    
					          reserv[i].addActionListener(new ActionListener(){
					        	  public void actionPerformed(ActionEvent e) {
					        		  nbreserv++;
					        		  
					        		  Request request = new Request();
					        			request.setEvent("done_reservation");
					        			Map<String, Object> hm = new HashMap<>();
					        			//hm.put("list_workspace", listesp);--erreur
					        			hm.put("reserv_numb",nbreserv);
					        			//hm.put("id_compa",id_compa);--erreur
					        			//hm.put("id_worksp",listesp);--erreur
					        			request.setData(hm);
					        			

					        			Response response = Utils.sendRequest(request);

					        			Map<String, Object>  data = (Map<String, Object>) response.getMessage();
					        		  //reserv[i].setText("réservation acceptée !");--erreur
					        		  
					 
					        		// change l'etat de disponible à indispible dans la table workspace
					        		  
					        	  }
					          });
						}
						
					     p.add(retButton) ;
					     p.invalidate();
						 p.validate();
						p.repaint();
						
					
					
					//ajouter la fonction sql qui va chercher toutes les données de la table en fonction de la demnde et qui en fait une liste*/
				}
			});
		    
		    
		    
		    retButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
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
