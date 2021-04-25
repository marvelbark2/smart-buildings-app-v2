package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.event.ActionEvent;

import javax.swing.*;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;

public class LocationMenu implements Navigate {
	
	private Main app;
	private JButton mapCity = new JButton("carte");
    private JButton locat = new JButton("louer");
    private JButton gere = new JButton("gerer");
    
    
    public LocationMenu(Main app) {
    	this.app=app;
    }

    
    
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	public void start() {
		JPanel panMenuLocat=app.getContext();
		
		panMenuLocat.add(mapCity);
		panMenuLocat.add(locat);
		panMenuLocat.add(gere);
	}


}
