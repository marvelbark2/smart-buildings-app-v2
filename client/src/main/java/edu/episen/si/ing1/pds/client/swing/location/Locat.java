package edu.episen.si.ing1.pds.client.swing.location;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Locat implements Way {
	JCheckBox bureau_ferme =new JCheckBox("bureau ferme");
    JCheckBox openspace =new JCheckBox("openspace");
    JTextField tailleMax = new JTextField(10);
    JTextField nbBureau = new JTextField(10);
    JTextField nbOpenspace = new JTextField(10);
    JTextField tailleMin = new JTextField(10);
    JTextField prixMin = new JTextField(10);
    JTextField prixMax = new JTextField(10);
    JCheckBox screen =new JCheckBox("ecran");
    JCheckBox captor =new JCheckBox("capteur");
    JCheckBox prise =new JCheckBox("prise");
    
    int answer =5;
    JLabel[] proposition = new JLabel[5];
    JButton[] reserv = new JButton[5];
    
    
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
		    PersMax.setLabelFor(prixMin);
		 
		    JLabel Pmax = new JLabel("prix maximum", JLabel.LEFT);
		    Pmax.setLabelFor(prixMax);
		    
		   
		    JButton retButton = new JButton("retour");
		    p.setLayout(new GridLayout(12, 1));
		
		    
		    valid.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
						p.removeAll();
						
						for (int i = 0; i<5;i++) {
							proposition[i]=new JLabel("pos");// le texte change en fonction du bâtiment
					    	  proposition[i].setText("cette offre contient x bureau fermé et y openspace pour une taille de z metre carré et un prix de x euros ");
					    	  p.add(proposition[i]);
					    	  reserv[i]= new JButton("réserver"); 
					          p.add(reserv[i]);
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
				    p.add(prixMin);
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
		    p.add(prixMin);
		    p.add(Pmax);
		    p.add(prixMax); 
		    p.add(screen);
		    p.add(prise);
		    p.add(captor);
		    p.add(valid);
		    panrech.add(p);
	}

}
