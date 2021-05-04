package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Parametre extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel TempératureExtérieureField = new JLabel("TempératureExtérieureField");
	JLabel TempératureIntérieureField1 = new JLabel("TempératureIntérieureField1");
	JLabel EclairageDirectField = new JLabel("EclairageDirectField");
	JLabel EclairageIndirectField = new JLabel("EclairageIndirectField");
	JLabel StatusstoresField = new JLabel("StatusstoresField");
	JLabel StatusTeintureVitreField = new JLabel("StatusTeintureVitreField");

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
		
		ConnectionDB conn = new ConnectionDB() ;

		Statement stmt = null;
		
		try{
			Connection c = conn.connect(); 
			stmt = c.createStatement();
			
			ResultSet rs = stmt.executeQuery(" select * from temperature ORDER BY id DESC LIMIT 1");
			//ResultSet rs2 = stmt.executeQuery(" select * from light WHERE id = " + 1 );
			
			while ( rs.next() ) {

		         int temp_ext = rs.getInt("temperature_interieure");
		         TempératureIntérieureField1.setText(Integer.toString(temp_ext));

		         int temp_int = rs.getInt("temperature_exterieure");
		         TempératureExtérieureField.setText(Integer.toString(temp_int));
		         
		         int storest  = rs.getInt("baisserStore");
		         StatusstoresField.setText(Integer.toString(storest));
		         
		   //      System.out.printf( "baisserStore = "+ storest + "\t\ntemperature_interieure = " + temp_ext+
		   //     		 "\t\ntemperature_exterieure"+ temp_int );

		         System.out.println();

		      }
			
			rs.close();
			
		    stmt.close();

		    c.close() ;
			
		}
		catch( Exception e1 ) {
			System.err.println( e1.getClass().getName()+": "+ e1.getMessage()  );

		      System.exit(0);
		}
		
		System.out.println(" ");
		
		try{
			Connection c = conn.connect(); 
			stmt = c.createStatement();
			
			//ResultSet rs = stmt.executeQuery(" select * from temperature WHERE id = " + 1 );
			ResultSet rs2 = stmt.executeQuery(" select * from light ORDER BY id DESC LIMIT 1");
			while ( rs2.next() ) {
		         
		         int teintureV = rs2.getInt("teinture_vitre");
		         StatusTeintureVitreField.setText(Integer.toString(teintureV));
		         
		         boolean  eclairageD = rs2.getBoolean("eclairage_direct");
		         EclairageDirectField.setText(Boolean.toString(eclairageD));
		         
		         int eclairageI = rs2.getInt("eclairage_indirect");
		         EclairageIndirectField.setText(Integer.toString(eclairageI));
		         
		         
		    //     System.out.printf( "\t\nteinture_vitre = "+ teintureV +
		    //    		 "\t\neclairage_direct = " + eclairageD + "\t\neclairage_indirect = "+ eclairageI );

		         System.out.println();
			}
			
			rs2.close();
			
			stmt.close();

		    c.close() ;
				
		}
		catch( Exception e1 ) {
			System.err.println( e1.getClass().getName()+": "+ e1.getMessage()  );

		    System.exit(0);
		}
		
		
	}
		
}
