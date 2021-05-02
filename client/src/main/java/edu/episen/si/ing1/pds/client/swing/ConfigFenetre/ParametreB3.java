package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParametreB3 extends JFrame implements ActionListener {

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JTextField Teinture_VitreField = new JTextField();
	JTextField Eclairage_DirectField = new JTextField();
	JTextField Eclairage_IndirectField = new JTextField();

	JLabel Teinture_Vitre = new JLabel("Teinture_VitreField");

	JLabel Eclairage_Direct = new JLabel("Eclairage_DirectField");

	JLabel Eclairage_Indirect = new JLabel("Eclairage_IndirectField");

	public ParametreB3() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(labelparametreB3(), BorderLayout.CENTER);
		contentPane.add(fieldparametreB3(), BorderLayout.EAST);

	}

	public JPanel labelparametreB3() {
		JPanel lab1 = new JPanel(new GridLayout(7, 1));
		lab1.add(Teinture_Vitre);
		lab1.add(Eclairage_Direct);
		lab1.add(Eclairage_Indirect);

		return lab1;
	}

	public JPanel fieldparametreB3() {
		JPanel field1 = new JPanel(new GridLayout(7, 1, 20, 20));
		field1.setPreferredSize(new Dimension(200, 200));

		field1.add(Teinture_VitreField);
		field1.add(Eclairage_DirectField);
		field1.add(Eclairage_IndirectField);
		
		JButton bouton2 = new JButton( "Valider");
		field1.add(bouton2);
		bouton2.addActionListener(this);
		JButton bouton3 = new JButton( "terminer");
		field1.add(bouton3);
		bouton3.addActionListener(this);
		return field1;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(Teinture_VitreField.getText());
		System.out.println(Eclairage_DirectField.getText());
		System.out.println(Eclairage_IndirectField.getText());
			
	}

	}




	