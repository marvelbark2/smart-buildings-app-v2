package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

/*public class Gere implements Way {
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
		int data_reserv = response.getMessage();
		
		
		
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
}*/

public class Gere implements Way {
	int nb_reserv= 0;
	
	public void begin(LocationMenu men) {
		JPanel pangere = men.getApp().getContext();
		JPanel pg = new JPanel( );
		
		
		Request request = new Request();
		request.setEvent("nb_reservation_list");
		Response response = Utils.sendRequest(request);
		List<Map> data=(List<Map>) response.getMessage();
		for(Map ex:data) {
			int k = Integer.valueOf(ex.get("count").toString());
			nb_reserv=k;
		}
		
		System.out.println(nb_reserv);
		 JLabel[] proposition = new JLabel[nb_reserv];
		 JButton[] left = new JButton[nb_reserv];
		 int[] id_wsp = new int[nb_reserv];
		 int[] size_wsp = new int[nb_reserv];
		 int[] price_wsp = new int[nb_reserv];
		 int[] maxpers_wsp = new int[nb_reserv];
		 int[] nbres = new int[nb_reserv];
		 int l = 0;
		 
		 for(int i=0;i<nb_reserv;i++) {
			 nbres[i] = l;
			 l++;
		 }
		 final int[] fori = nbres;
		 
		 pg.setLayout(new GridLayout(nb_reserv, 1));
		 
		 
		 
		 int last_id_reserv=0;
		 for(int i=0;i<nb_reserv;i++) {
			 proposition[i]=new JLabel("");
				left[i]= new JButton("rendre espace");
				id_wsp[i] =0;
				size_wsp[i] = 0;
				price_wsp[i] =0;
				maxpers_wsp[i]=0;
				
				
				
				
				Request requestlist = new Request();
			requestlist.setEvent("reservation_list");
			Map<String, Object> rl = new HashMap<>();
			rl.put("last_id_res", last_id_reserv);
			
			requestlist.setData(rl);
			Response responselist = Utils.sendRequest(requestlist);
			List<Map> data_list=(List<Map>) responselist.getMessage();
			for(Map ex:data_list) {
				id_wsp[i] =Integer.valueOf(ex.get("id").toString());
				size_wsp[i] =Integer.valueOf(ex.get("size").toString()) ;
				price_wsp[i] =Integer.valueOf(ex.get("price").toString());
				maxpers_wsp[i]=Integer.valueOf(ex.get("employee").toString());;
				last_id_reserv= Integer.valueOf(ex.get("id_reserv").toString());
			}
				
			 
			 proposition[i].setText("l'espace "+id_wsp[i]+" est réservé, il a une taille de "+size_wsp[i]+" et peut contenir "+maxpers_wsp[i]+" personnes, pour un prix de "+price_wsp[i]+" euros.");
			 pg.add(proposition[i]);
			 pg.add(left[i]);
			
		 
			 int a = i;
			 left[i].addActionListener(new ActionListener() {
				
				 int b =fori[a];
				 public void actionPerformed(ActionEvent e) {
					 Request requestsupp = new Request();
						requestsupp.setEvent("kill_reservation");
						Map<String, Object> rk = new HashMap<>();
						rk.put("idres", id_wsp[b]);
						
						requestsupp.setData(rk);
						Response response = Utils.sendRequest(requestsupp);
					 left[b].setText("annulation effectuée");
					
				 }
			 });
		 }
		 
	pangere.add(pg);
	}
}
