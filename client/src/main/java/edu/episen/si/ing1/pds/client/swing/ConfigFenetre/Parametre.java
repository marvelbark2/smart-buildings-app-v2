package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Parametre extends JFrame {
	JTextField TemperatureExterieureField = new JTextField();
	JTextField TemperatureInterieureField1 = new JTextField();
	JTextField luminositeExterieureField = new JTextField();
	JTextField luminositeInterieureField = new JTextField();
	JTextField StoreLevéField = new JTextField();
	JTextField TeintureVitreField = new JTextField();

	JLabel TemperatureExterieure = new JLabel("Temp�ratureExt�rieure");
	
	JLabel TemperatureInterieure = new JLabel("Temp�ratureInt�rieure");
	JLabel luminositeExterieure = new JLabel("luminositeExterieure");
	JLabel luminositeInterieure1 = new JLabel("luminositeInterieure");
	JLabel StoreLeve = new JLabel("StoreLev�");
	JLabel TeintureVitre = new JLabel("TeintureVitre");
	private Component TemperatureExterieure1;
	private Component TemperatureInterieure1;
	private Component luminositeInterieure;
	private Component luminositExterieure;
	private Component TemperatureInterieureField;
	private Component StoreLeveField;
	private Component luminositInterieure;

	public Parametre() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(labelparametre(),BorderLayout.CENTER);
		contentPane.add(fieldparametre(), BorderLayout.EAST);
		
	} 
	public JPanel labelparametre() {
		JPanel lab=new JPanel(new GridLayout(7,1));
		lab.add(TemperatureExterieure);
		lab.add(luminositeExterieure);
		lab.add(luminositInterieure);
		
		
		lab.add(StoreLeve);
		lab.add(TeintureVitre);
		
		
		
		
		
		return lab;
	}
	public JPanel fieldparametre() {
		JPanel field=new JPanel(new GridLayout(7,1,20,20));
		field.setPreferredSize(new Dimension(200,200));
		
		
		
		field.add(TemperatureInterieureField1);
		field.add(luminositInterieure);
		field.add(luminositeExterieure);
		field.add(StoreLeveField);
		field.add(TeintureVitreField);

		
		return field;
	}
		

}


