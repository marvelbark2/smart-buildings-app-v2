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
		
		//asking for the number of reservation made by a company
		Request request = new Request();
		request.setEvent("nb_reservation_list");
		Map<String, Integer> hm = new HashMap<>();
		//hm.put("company_id", ?);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		Map<String, Object>  data = (Map<String, Object>) response.getMessage();
		
		
		//asking for the list of the reservation
		Request request2 = new Request();
		request.setEvent("reservation_list");
		Map<String, Integer> hm2 = new HashMap<>();
		//hm.put("company_id", ?);--error
		request.setData(hm);
		//Response response = Utils.sendRequest(request);--error
		//Map<String, Object> data = (Map<String, Object>) response.getMessage();--error
		/*SocketConfig.Instance.setEnv(true);
		Request request=new Request();
		request.setEvent("random_offer");
		Response response = Utils.sendRequest(request);
		int data_reserv = response.getMessage();*/
		
		
		
		int data_reserv = 16;//number of rseervation
	      for (int r = 0; r < data_reserv; r++) {  		
	    	  lab[r]=new JLabel("pos");
	    	 // lab[r].setText("l'espace "+esp_reserv+"a été réservé");--error
			  pg.add(lab[r]);
	          bouton[r]= new JButton("annuler reservation"); 
	          pg.add(bouton[r]);
	          
	          bouton[r].addActionListener(new ActionListener(){
	        	  public void actionPerformed(ActionEvent e) {
	        		 
	        		// here should be the request for changing the state to available to available and remove from the table reservation(kill_reservation)
	        		  
	        	  }
	          });
	          
	}
	}
}