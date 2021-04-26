package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;

public class LocationMenu implements Navigate {

	private Main app;
	private Map<JButton, Way> frames = new HashMap<>();
	private JButton mapCity = new JButton("carte");
	private JButton locat = new JButton("louer");
	private JButton gere = new JButton("gerer");
	private Way currentPane = null;
    
    
    public LocationMenu(Main app) {
    	this.app=app;
    }



	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton chosenButton = (JButton) e.getSource();
			Way way = frames.get(chosenButton);
			currentPane = way;

			app.getContext().removeAll();
			way.begin(this);
			app.getContext().invalidate();
			app.getContext().validate();
			app.getContext().repaint();
			app.getContext().setVisible(true);

		}

	}

	public void start() {
		JPanel panMenuLocat=app.getContext();
		JPanel MenuLocat = new JPanel(new FlowLayout(FlowLayout.CENTER));

		mapCity.addActionListener(this);
		locat.addActionListener(this);
		gere.addActionListener(this);

		frames.put(mapCity, new MapCity());
		frames.put(locat, new Locat());
		frames.put(gere, new Gere());

		MenuLocat.add(mapCity);
		MenuLocat.add(locat);
		MenuLocat.add(gere);

		panMenuLocat.add(MenuLocat, BorderLayout.CENTER);
	}


}
