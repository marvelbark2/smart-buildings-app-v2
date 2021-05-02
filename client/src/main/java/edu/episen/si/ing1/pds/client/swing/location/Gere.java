package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

public class Gere implements Way {
	 int nb_reserv= 16;
	 JButton[] bouton = new JButton[nb_reserv];
	 JLabel[] lab = new JLabel[nb_reserv];
	
	 
	
	
	public void begin(LocationMenu men) {
		JPanel pangere = men.getApp().getContext();
		JPanel pg = new JPanel( );
		
		Request request = new Request();
		request.setEvent("nb_reservation_list");
		Map<String, Integer> hm = new HashMap<>();
		//hm.put("company_id", ?);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		Map<String, Object>  data = (Map<String, Object>) response.getMessage();
		
		
		Request request2 = new Request();
		request.setEvent("reservation_list");
		Map<String, Integer> hm2 = new HashMap<>();
		//hm.put("company_id", ?);--erreur
		request.setData(hm);
		//Response response = Utils.sendRequest(request);--erreur
		//Map<String, Object> data = (Map<String, Object>) response.getMessage();--erreur
		/*SocketConfig.Instance.setEnv(true);
		Request request=new Request();
		request.setEvent("random_offer");
		// va chercher le nombre de reservation d'une entreprise
		Response response = Utils.sendRequest(request);
		int data_reserv = response.getMessage();*/
		
		int data_reserv = 16;
	      for (int r = 0; r < data_reserv; r++) {  		
	    	  lab[r]=new JLabel("pos");// le texte change en fonction du bâtiment
	    	 // lab[r].setText("l'espace "+esp_reserv+"a été réservé");--erreur
			  pg.add(lab[r]);
	          bouton[r]= new JButton("annuler reservation"); 
	          pg.add(bouton[r]);
	          
	          bouton[r].addActionListener(new ActionListener(){
	        	  public void actionPerformed(ActionEvent e) {
	        		 
	        		// change l'etat de indisponible à dispible dans la table workspace
	        		  
	        	  }
	          });
	          
	}
	}
}