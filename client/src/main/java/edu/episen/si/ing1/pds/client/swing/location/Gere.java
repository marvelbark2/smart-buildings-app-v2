package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gere implements Way {
	 int nb_reserv= 16;
	 JButton[] bouton = new JButton[nb_reserv];
	 JLabel[] lab = new JLabel[nb_reserv];
	
	
	
	public void begin(LocationMenu men) {
		JPanel pangere = men.getApp().getContext();
		JPanel pg = new JPanel( );
		
		
		
		
	      for (int r = 0; r < 16; r++) {  		
	    	  lab[r]=new JLabel("pos");// le texte change en fonction du bâtiment
	    	  lab[r].setText("pos");
			  pg.add(lab[r]);
	          bouton[r]= new JButton("annuler reservation"); 
	          pg.add(bouton[r]);
	          
	}
	      pg.setLayout(new GridLayout(16,2));
	     
	      pg.invalidate();
	      pg.validate();
	      pg.repaint();
	      pangere.add(pg);
	

}}
