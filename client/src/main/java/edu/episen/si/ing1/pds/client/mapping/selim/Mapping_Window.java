package edu.episen.si.ing1.pds.client.mapping.selim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Mapping_Window extends JFrame {
	
	public Mapping_Window() {
		super("Smart Building APP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(1200, 800);
		this.setPreferredSize(null);
		
		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		
		
		JPanel logo = new JPanel(new FlowLayout());
		JLabel label = new JLabel("Logo");
		logo.add(label, BorderLayout.CENTER);
		contentPane.add(logo, BorderLayout.PAGE_START);
		
	
		
		contentPane.add(menuScroll(), BorderLayout.WEST);
	
		contentPane.add(carte(), BorderLayout.CENTER);
		
		contentPane.add(bloc_equipement(), BorderLayout.EAST);
		
		contentPane.add(createToolBar(), BorderLayout.SOUTH);
		
	}
	
	private JScrollPane menuScroll() {
		
		JScrollPane menu = new JScrollPane(new JTree());
		menu.setPreferredSize(new Dimension(200,0));
		
		return menu;
		
	}
	
	private JToolBar createToolBar() {
		
		JToolBar toolBar = new JToolBar();
		
		JButton retour = new JButton("Retour");
		toolBar.add(retour);
		
		return toolBar;
	}
	
	private JPanel carte() {
		
		JPanel carte = new JPanel(new GridLayout(3,3,10,10));
		carte.setBorder(new LineBorder(Color.black));
		
		JTextField emplacement1 = new JTextField("Deposer un équipement");
		emplacement1.setBorder(new LineBorder(Color.GREEN));
		carte.add(emplacement1);
		
		JTextField emplacement2 = new JTextField("Deposer un équipement");
		emplacement2.setBorder(new LineBorder(Color.GREEN));
		carte.add(emplacement2);
		
		JTextField emplacement3 = new JTextField("Deposer un équipement");
		emplacement3.setBorder(new LineBorder(Color.RED));
		carte.add(emplacement3);
		
		JTextField emplacement4 = new JTextField("Deposer un équipement");
		emplacement4.setBorder(new LineBorder(Color.GREEN));
		carte.add(emplacement4);
		
		JTextField emplacement5 = new JTextField("Deposer un équipement");
		emplacement5.setBorder(new LineBorder(Color.RED));
		carte.add(emplacement5);
		
		
		return carte;
		
	}

	private JPanel bloc_equipement() {
		
		JPanel bloc = new JPanel(new GridLayout(3,1));
		
		ImageIcon icon_ecran = new ImageIcon("/Users/selim/smart-buildings-app/client/src/main/resources/icon/capteur.png");
		JLabel ecran = new JLabel("Ecran");
		ecran.setIcon(new ImageIcon(new ImageIcon("/Users/selim/smart-buildings-app/client/src/main/resources/icon/capteur.png").getImage()));
		ecran.setPreferredSize(new Dimension(100,30));
		JLabel prise = new JLabel("Prise");
		prise.setPreferredSize(new Dimension(100,30));
		JLabel capteur = new JLabel("Capteur");
		capteur.setPreferredSize(new Dimension(100,30));
		
		bloc.add(ecran);
		bloc.add(capteur);
		bloc.add(prise);
		
		
		return bloc;
	}
	
	public static void main(String[] args) {
		TestCanvas frame = new TestCanvas();
		frame.setVisible(true);
	
	}
	
}

