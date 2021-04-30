package edu.episen.si.ing1.pds.client.swing.mapping.selim;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.css.Rect;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;
import edu.episen.si.ing1.pds.client.utils.Utils;



public class Mapping_Window implements Navigate {
    Main global;
    private JTree arbre;
    

    public Mapping_Window(Main global) {
        this.global = global;


    }
   

    private void menuScroll(JTree arbre2) {
        JPanel menuPanel = global.getMenu();
        
      
        JScrollPane menu = new JScrollPane(arbre2);

        menuPanel.removeAll();
        menu.setPreferredSize(new Dimension(200,0));

        menuPanel.add(menu);
        menuPanel.invalidate();
        menuPanel.validate();
        menuPanel.repaint();


    }
    
   private JTree buildTree() {
    	
	   	try {
		
			Request request=new Request();
			request.setEvent("building_list");
			Response response = Utils.sendRequest(request);
			List<Map> data=(List<Map>) response.getMessage();
			
			Request request2 = new Request();
			request2.setEvent("floors_list");
			Response response2 = Utils.sendRequest(request2);
			List<Map> data2 = (List<Map>) response2.getMessage();
			
    	DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Racine");    
    	
    	for(Map e:data) {
    		DefaultMutableTreeNode rep = new DefaultMutableTreeNode(e.get("name").toString());
    		for(Map e2: data2) {
    			DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode("Etage" + e2.get("floor_number").toString());
    			rep.add(rep2);
    		}
    		racine.add(rep);
    	}
    	
    	arbre = new JTree(racine); 
    	
    } catch (Exception e) {
		// TODO: handle exception
	}
		return arbre;
	   
	/*   try {
		SocketConfig.Instance.setEnv(true);
		Request request = new Request();
		request.setEvent("tree_list");
		Response response = Utils.sendRequest(request);
		Map<Map, List> dataMap = new HashMap();
		
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("racine");
		
		for (Map building:dataMap.keySet()) {
			DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(building.get("name"));
            for (Object floor: dataMap.get(building)) {
                Map<Map, List> floorN = (Map) floor;
                for (Map<Map, List> works: floorN.keySet()) {
                    DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode("Etage " + works.get("floor_number"));
                    buildNode.add(floorNode);
                    for (List<Map> dataN: floorN.values()) {
                        for (Map workspace: dataN) {
                            DefaultMutableTreeNode workNode = new DefaultMutableTreeNode(workspace.get("workspace_type"));
                            floorNode.add(workNode);
                        }
                    }
                }
            }
            racine.add(buildNode);
		}
		arbre = new JTree(racine);
	} catch (Exception e) {
		// TODO: handle exception
	}
	   return arbre;*/
 }

    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                global.setupFrame();
                global.getFrame().pack();
            }
        });
        toolBar.add(retour);

        return toolBar;
    }
    
    public class DrawMyRect extends JPanel {
    	
	    public void paint(Graphics g) {
	    	g.drawRect(50, 35, 150, 150);
	    	g.setColor(Color.red);

	    }
    }
    private JPanel carte() {
    	
    	JPanel carte = new JPanel(new GridBagLayout());
    	carte.setBorder(new LineBorder(Color.black));
    	
    	 JButton btn1 = new JButton();
    	 btn1.setTransferHandler(new TransferHandler("text"));
		 JButton btn2 = new JButton();
		 btn2.setTransferHandler(new TransferHandler("icon"));
		 JButton btn3 = new JButton();
		 btn3.setTransferHandler(new TransferHandler("icon"));
		 JButton btn4 = new JButton();
		 btn4.setTransferHandler(new TransferHandler("icon"));
		 JButton btn5 = new JButton("Btn 5");
		 btn5.setTransferHandler(new TransferHandler("icon"));
		 JButton btn6 = new JButton("Btn 6");
		 btn6.setTransferHandler(new TransferHandler("icon"));
		 JButton btn7 = new JButton("Btn 7");
		 btn7.setTransferHandler(new TransferHandler("icon"));
		 JButton btn8 = new JButton("Btn 8");
		 btn8.setTransferHandler(new TransferHandler("icon"));
		 JButton btn9 = new JButton("Btn 9");
		 btn9.setTransferHandler(new TransferHandler("icon"));
		 
		 GridBagConstraints gcon = new GridBagConstraints();
		 gcon.weightx = 1;
		 gcon.weighty = 1;
		 
		 gcon.fill = GridBagConstraints.BOTH;
		 
		 // Prepare Contraints for Btn 1 
		 gcon.gridx = 0;
		 gcon.gridy = 0;
		 gcon.gridwidth = 2;
		 gcon.gridheight = 1;
		 
		 // Add Control
		 carte.add(btn1, gcon);
		 
		 
		 // Prepare Contraints for Btn2
		 gcon.gridx = 2;
		 gcon.gridy = 0;   // pas obliger
		 gcon.gridwidth = 2;  // pas obliger
		 gcon.gridheight = 1;  // pas obliger 
		 
		 // Add Control 
		 
		 carte.add(btn2, gcon);
		 
		 
		 // Prepare Contraints for Btn3 
		 gcon.gridy = 1;
		 gcon.gridwidth = 1;
		 gcon.gridx = 0;
		 
		 // Add Control
		 carte.add(btn3,gcon);
		 
		 // Prepare Contraints for Btn4
		 
		 gcon.gridx = 1;
		 
		 // Add Control
		 
		 carte.add(btn4,gcon);
		 
		 gcon.gridx = 2;
		 
		 carte.add(btn5,gcon);
		 
		 gcon.gridx = 3;
		 carte.add(btn6,gcon);
		 
		 gcon.gridx = 0;
		 gcon.gridy = 2;
		 gcon.gridwidth = 3;
		 carte.add(btn7,gcon);
		 
		 gcon.gridy = 3;
		 carte.add(btn8,gcon);
		 
		 gcon.gridx = 3;
		 gcon.gridy = 2;
		 gcon.gridwidth = 1;
		 gcon.gridheight = 2;
		 
		 carte.add(btn9, gcon);
		 
        return carte;

    }

    private void bloc_equipement() {

        JPanel bloc = global.getBloc();
        bloc.setLayout(new GridLayout(3,1));
        
        ImageIcon icon1 = new ImageIcon("src/main/resources/icon/sensor.png");
        ImageIcon icon2 = new ImageIcon("src/main/resources/icon/ecran.jpg");
        ImageIcon icon3 = new ImageIcon("src/main/resources/icon/prise.jpg");

        JLabel label1 = new JLabel(icon1, JLabel.CENTER);
        JLabel label2 = new JLabel(icon2, JLabel.CENTER);
        JLabel label3 = new JLabel(icon3, JLabel.CENTER);

        DragMouseAdapter listener = new DragMouseAdapter();
        label1.addMouseListener(listener);
        label2.addMouseListener(listener);
        label3.addMouseListener(listener);

        

        label1.setTransferHandler(new TransferHandler("icon"));
        label2.setTransferHandler(new TransferHandler("icon"));
        label3.setTransferHandler(new TransferHandler("icon"));
    
      

        bloc.add(label1);
        bloc.add(label2);
        bloc.add(label3);
        bloc.repaint();
    }
    

    private class DragMouseAdapter extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }

    private String getUriOfFile(String file) {
        String uri = null;
        try {
            uri = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(file)).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public void start() {
        JPanel contentPane = global.getContext();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        contentPane.setLayout(borderLayout);
        arbre = buildTree();
        menuScroll(arbre);
        bloc_equipement();

        contentPane.add(carte());

        contentPane.add(createToolBar(), BorderLayout.SOUTH);
        global.getFrame().pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
