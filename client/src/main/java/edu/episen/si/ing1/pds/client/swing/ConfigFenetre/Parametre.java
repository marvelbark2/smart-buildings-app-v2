package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Parametre extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField TempératureExtérieureField = new JTextField();
	JTextField TempératureIntérieureField1 = new JTextField();
	JTextField EclairageDirectField = new JTextField();
	JTextField EclairageIndirectField = new JTextField();
	JTextField StatusstoresField = new JTextField();
	JTextField StatusTeintureVitreField = new JTextField();

	JLabel TemperatureExterieure = new JLabel("TempératureExtérieure");
	JLabel TempératureIntérieure = new JLabel("TempératureIntérieure");
	JLabel EclairageDirect = new JLabel("EclairageDirect");
	JLabel EclairageIndirect = new JLabel("EclairageIndirect");
	JLabel StatusStores = new JLabel("StatusStores");
	JLabel StatusTeintureVitre = new JLabel("StatusTeintureVitre");
	
	


	public Parametre() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(labelparametre(), BorderLayout.CENTER);
		contentPane.add(fieldparametre(), BorderLayout.EAST);

	}

	public JPanel labelparametre() {
		JPanel lab = new JPanel(new GridLayout(7, 1));
		lab.add(TemperatureExterieure);
		lab.add(EclairageDirect);
		lab.add(EclairageIndirect);
		lab.add(TempératureIntérieure);
		lab.add( StatusStores);
		lab.add( StatusTeintureVitre);
		
		return lab;
	}

	public JPanel fieldparametre() {
		JPanel field = new JPanel(new GridLayout(7, 1, 20, 20));
		field.setPreferredSize(new Dimension(200, 200));
		field.add(TempératureIntérieureField1);
		field.add(TempératureExtérieureField);
		field.add(EclairageDirectField);
		field.add(EclairageIndirectField);
		field.add(StatusstoresField);
		field.add(StatusTeintureVitreField);
		JButton bouton4 = new JButton( "refresh");
		field.add(bouton4);
		bouton4.addActionListener(this);
		return field;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
