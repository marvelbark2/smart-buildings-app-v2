package edu.episen.si.ing1.pds.client.swing.mapping.selim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.network.SocketConfig;
import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;
import edu.episen.si.ing1.pds.client.utils.Utils;

public class Mapping_Window implements Navigate {
    Main global;
    private JTree arbre;

    public Mapping_Window(Main global) {
        this.global = global;


    }
    
    private void getBatiment() {
    	try {
    		SocketConfig.Instance.setEnv(true);
    		Request request=new Request();

    		JPanel panel = new JPanel();
    		request.setEvent("building_list");
            Response response = Utils.sendRequest(request);
            List<Map> data = (List<Map>) response.getMessage();
            for ( Map e: data) {
                JLabel label = new JLabel(e.get("abbreviation").toString());
                label.setBorder(new LineBorder(Color.BLUE));
                panel.add(label);
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
        
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
    	
    	DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Racine");    
    	
    	for(int i = 1; i < 6; i++){
    		DefaultMutableTreeNode rep = new DefaultMutableTreeNode("Noeud n°"+i);
    		if(i < 4){   
    			DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode("Fichier enfant");
    			rep.add(rep2);
    			}
    		racine.add(rep);
    		}
    	
    	arbre = new JTree(racine); 
    	return arbre;
    }

    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                global.setupFrame();
            }
        });
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

    private void bloc_equipement() {

        JPanel bloc = global.getBloc();
        bloc.setLayout(new GridLayout(3,1));

        JLabel ecran = new JLabel("Ecran");
        ecran.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/ecran.jpg")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        ecran.setPreferredSize(new Dimension(100,30));

        JLabel prise = new JLabel("Prise");
        prise.setPreferredSize(new Dimension(100,30));
        prise.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/prise.jpg")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        JLabel capteur = new JLabel("Capteur");
        capteur.setPreferredSize(new Dimension(100,30));
        capteur.setIcon(new ImageIcon(new ImageIcon(getUriOfFile("icon/capteur.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        bloc.add(ecran);
        bloc.add(capteur);
        bloc.add(prise);

        bloc.repaint();
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
