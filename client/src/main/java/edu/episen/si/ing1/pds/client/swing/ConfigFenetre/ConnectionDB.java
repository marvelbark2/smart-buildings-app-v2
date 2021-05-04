package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private String url = "jdbc:postgresql://172.31.254.84:5432/dbsmartbuildings";
	private String user = "postgres";
	private String password = "Grp1SmartBuildTeam";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
		
	}

	
}
