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
		
		
		 JLabel[] proposition = new JLabel[nb_reserv];
		 JButton[] left = new JButton[nb_reserv];
		 int[] id_wsp = new int[nb_reserv];
		 int[] size_wsp = new int[nb_reserv];
		 int[] price_wsp = new int[nb_reserv];
		 int[] maxpers_wsp = new int[nb_reserv];
		 int[] nbres = new int[nb_reserv];
		 int[] bln = new int[nb_reserv];
		 int[] fln = new int[nb_reserv];
		 int l = 0;
		 
		 for(int i=0;i<nb_reserv;i++) {
			 nbres[i] = l;
			 l++;
		 }
		 final int[] fori = nbres;
		 
		 pg.setLayout(new GridLayout(nb_reserv, 1,20,20));
		 
		 
		 
		 int last_id_reserv=0;
		 for(int i=0;i<nb_reserv;i++) {
			 proposition[i]=new JLabel("");
				left[i]= new JButton("rendre espace");
				id_wsp[i] =0;
				size_wsp[i] = 0;
				price_wsp[i] =0;
				maxpers_wsp[i]=0;
				bln[i]=0;
				fln[i]=0;
				
				
				
				
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
				bln[i]= Integer.valueOf(ex.get("bln").toString());
				fln[i] =Integer.valueOf(ex.get("fln").toString()) ;
			}
				
			 
			 proposition[i].setText("<html>l'espace "+id_wsp[i]+" se situant dans le batiment "+bln[i]+" à l'étage "+fln[i]+" est réservé, il a une taille de "+size_wsp[i]+" et peut contenir "+maxpers_wsp[i]+" personnes, pour un prix de "+price_wsp[i]+" euros.</html>");
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
