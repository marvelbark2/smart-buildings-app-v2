package View;

import java.awt.Component;

import javax.swing.*;

public class Vuei extends JFrame {
	
	public Vuei() {
		this.setTitle("My Window");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		JPanel cp =(JPanel) this.getContentPane();
		cp.add(new JButton("Let's Start"));
	}

}
