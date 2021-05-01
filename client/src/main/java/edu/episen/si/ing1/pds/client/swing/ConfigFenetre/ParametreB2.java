package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParametreB2 extends JFrame implements ActionListener  {
	private static final long serialVersionUID = 1L;

	
	
	JTextField TemperatureExterieureField1 = new JTextField();
	JTextField TemperatureInterieureField1 = new JTextField();
	JTextField BaisserStoreField = new JTextField();
	JTextField TeintureVitreField = new JTextField();
	JTextField EclairageDirectField = new JTextField();
	JTextField EclairageIndirectField = new JTextField();

	
	JLabel TemperatureExterieureField = new JLabel("TemperatureExterieure");

	JLabel TemperatureInterieureField = new JLabel("TemperatureInterieure");

	JLabel BaisserStore = new JLabel("BaisserStore");

	JLabel TeintureVitre = new JLabel("TeintureVitreField");

	JLabel EclairageDirect = new JLabel("EclairageDirectField");

	JLabel EclairageInDirect = new JLabel("EclairageIndirectField");



	private Component TemperatureInterieure;



	private Component TemperatureExterieure;

	public ParametreB2() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		 
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(labelparametreB2(), BorderLayout.CENTER);
		contentPane.add(fieldparametreB2(), BorderLayout.EAST);
		

	}

	public JPanel labelparametreB2() {
		JPanel lab1 = new JPanel(new GridLayout(7, 1));
		lab1.add(TemperatureExterieure);
		lab1.add(TemperatureInterieure);
		lab1.add(BaisserStore);
		lab1.add(EclairageInDirect);
		lab1.add(EclairageDirect);
		lab1.add(TeintureVitre);
		return lab1;
	}

	public JPanel fieldparametreB2() {
		JPanel field1 = new JPanel(new GridLayout(7, 1, 20, 20));
		field1.setPreferredSize(new Dimension(200, 200));

		field1.add(TemperatureExterieureField1);
		field1.add(TemperatureInterieureField1);
		field1.add(BaisserStoreField);
		field1.add(EclairageDirectField);
		field1.add(EclairageIndirectField);
		field1.add(TeintureVitreField);
		
		JButton bouton = new JButton( "SUBMIT");
		field1.add(bouton);
		bouton.addActionListener(this);
		return field1;
	}
	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(TemperatureExterieureField1.getText());
		System.out.println(TemperatureInterieureField1.getText());
		System.out.println(BaisserStoreField.getText());
		System.out.println(EclairageDirectField.getText());
		System.out.println(EclairageIndirectField.getText());
		System.out.println(TeintureVitreField.getText());
		
		// Jquery
		
		
	}

}


