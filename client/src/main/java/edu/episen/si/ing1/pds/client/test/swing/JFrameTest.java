package edu.episen.si.ing1.pds.client.test.swing;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class JFrameTest extends JFrame {
	
        private JPanel pan =new JPanel();
        private JButton button = new JButton("Valider");
        private JComboBox<String> comboBox = new JComboBox<>();
       
        public JFrameTest() {
			final String labels[] = { "Selectionner une entreprise","Apple", "Nike", "Auchan", "Microsoft", "YounessCorp" };

			this.setTitle("Bienvenu chez SmartBuild-Team");
        	this.setSize(400, 200);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			for (String label: labels) {
				comboBox.addItem(label);
			}

	        pan.add(button);
	        pan.add(comboBox);
	        this.setContentPane(pan); 
	        this.setVisible(true);   
        }

}


