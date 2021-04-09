package edu.episen.si.ing1.pds.client.testswing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.ItemSelectable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class MaPremiereFenetre extends JFrame {
	
        final String labels[] = { "Selectionner une entreprise","Apple", "Nike", "Auchan", "Microsoft", "YounessCorp" };
        private JPanel pan =new JPanel();
        private JButton button = new JButton("Valider");
        JComboBox<String> comboBox = new JComboBox<>(labels);
       
        public MaPremiereFenetre() {
        	
        	this.setTitle("Bienvenu chez SmartBuild-Team");
        	this.setSize(400, 200);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	       
	       // Ajout du boutton 
	        
	        pan.add(button);
	        this.setContentPane(pan); 
	        this.setVisible(true);   
        }
        
        public static void main(String[] args) {
        	
        	 final String labels[] = { "Selectionner une entreprise","Apple", "Nike", "Auchan", "Microsoft", "YounessCorp" };
        	 JComboBox<String> comboBox = new JComboBox<>(labels);
        	 MaPremiereFenetre fenetre =new MaPremiereFenetre();
        	 fenetre.add(comboBox);
			
		}
}


