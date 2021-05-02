package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Fenetres extends JFrame implements ActionListener{
	JPanel seconJPanel = new JPanel();
	JButton b1;
	JButton b2;
	JButton b3;
	
	private JTextField textField;
	private JTextField titleField;
	private JButton submit;
	JPanel pan = new JPanel();

	public Fenetres() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(800, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = (JPanel) this.getContentPane();
		getContentPane().setLayout(new BorderLayout());

		contentPane.add(addingAllMenu(), BorderLayout.CENTER);
		JTextField TempératureExtérieureField = new JTextField();
		JTextField TempératureIntérieureField = new JTextField();
		
		JTextField StatusStoreField = new JTextField();
		JTextField StatusTeintureVitreField = new JTextField();
		JTextField BaisserStoreField = new JTextField();
		JTextField TeintureVitreField1 = new JTextField();
		JTextField EclairageDirectField = new JTextField();
		JTextField EclairageIndirectField = new JTextField();

	}

	private JPanel addingAllMenu() {
		seconJPanel = new JPanel(new BorderLayout());

		seconJPanel.add(optionOFTop(), BorderLayout.NORTH);
		seconJPanel.add(optionOFCentered(), BorderLayout.CENTER);

		return seconJPanel;
	}

	private JPanel optionOFTop() {
		JPanel pane = new JPanel(new FlowLayout());
		b1 = new JButton("choix d'utilisation température");
		pane.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("choix d'utilisation lumiére");
		pane.add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Etats Actuel d'une ou plusieurs Fenetre(s)");
		pane.add(b3);
		b3.addActionListener(this);
		
		
		
		return pane;

	}

	private JPanel optionOFCentered() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		// a new panel for title option
		JPanel titlePane = new JPanel();
		titleField = new JTextField();
		titlePane.add(titleField);
		titleField.setColumns(10);
		mainPanel.add(titlePane, BorderLayout.NORTH);
		return titlePane;

	}

	public static void main(String[] args) {
		new Fenetres();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if ( o == b1) 
			new ParametreB2();
	
		else if(o ==b2)
			new ParametreB3();
	
		else
			new Parametre();
		
			
		}
}


