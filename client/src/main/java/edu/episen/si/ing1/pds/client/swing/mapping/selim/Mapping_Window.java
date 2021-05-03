package edu.episen.si.ing1.pds.client.swing.mapping.selim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.Icon;
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
import javax.swing.tree.DefaultTreeCellRenderer;





import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;
import edu.episen.si.ing1.pds.client.utils.Utils;



public class Mapping_Window implements Navigate {
    Main global;
    private JPanel content = new JPanel();
  

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
        JTree tree = null;
	  try {
		Request request = new Request();
		request.setEvent("tree_list");
		Response response = Utils.sendRequest(request);
          Map<Map, List<Map>> dataMap = ( Map<Map, List<Map>>) response.getMessage();

		DefaultMutableTreeNode racine = new DefaultMutableTreeNode(Utils.getCompanyName());

		for (Object building: dataMap.keySet()) {
		    Map buildingMap = Utils.toMap(building.toString());
			DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(buildingMap.get("name"));
            for (Object floor: dataMap.get(building)) {
                Map<Map, List> floorN = (Map) floor;
                for (Object works: floorN.keySet()) {
                    Map floorMap = Utils.toMap(works.toString());
                    DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode(floorMap.get("floor"));
                    buildNode.add(floorNode);
                    for (List<Map> dataN: floorN.values()) {
                        for (Map workspace: dataN) {
                            DefaultMutableTreeNode workNode = new DefaultMutableTreeNode(workspace);
                            floorNode.add(workNode);
                        }
                    }
                }
            }
            racine.add(buildNode);
		}
		tree = new JTree(racine);

		tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                Object selected = ( (DefaultMutableTreeNode) value).getUserObject();
                if(selected instanceof Map) {
                    setText(((Map)  selected).get("workspace_type").toString());
                }

                if(leaf && sel && hasFocus) {
                    content.removeAll();
                   
                    content.setLayout(new BorderLayout());
                    content.add(carte((Integer) ((Map)  selected).get("id_workspace")));
                    content.invalidate();
                    content.validate();
                    content.repaint();
                }
                return this;
            }
        });
	} catch (Exception e) {
		e.printStackTrace();
	}
	   return tree;
 }

    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                global.getBloc().removeAll();
            	global.setupFrame();
                global.getFrame().pack();
            }
        });
        toolBar.add(retour);

        return toolBar;
    }
    
   
    private JPanel carte(Integer id) {
        Request request=new Request();
        request.setEvent("mapping_list");
        request.setData(Map.of("workspace_id", id));
        Response response = Utils.sendRequest(request);
        List<Map> data=(List<Map>) response.getMessage();

        JPanel carte = new JPanel(new GridBagLayout());
        carte.setBorder(new LineBorder(Color.black));
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.weightx = 1;
        gcon.weighty = 1;

        

        gcon.fill = GridBagConstraints.BOTH;

        for(Map e:data) {
            
        	JButton btn = new JButton(e.get("equipment_id").toString());
            gcon.gridx = Integer.valueOf(e.get("gridx").toString());
            gcon.gridy = Integer.valueOf(e.get("gridy").toString());
            gcon.gridheight = Integer.valueOf(e.get("gridheigth").toString());
            gcon.gridwidth = Integer.valueOf(e.get("gridwidth").toString());
            btn.setTransferHandler(new TransferHandler("icon"));
            carte.add(btn, gcon);
            

        }


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
        bloc.setVisible(true);
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
        JTree arbre = buildTree();
        menuScroll(arbre);
        bloc_equipement();

        JLabel label = new JLabel("Veuillez sélectionnez un espace à configurer à l'aide de l’arborescence sur votre gauche");
        content.add(label);

        contentPane.add(content);


        contentPane.add(createToolBar(), BorderLayout.NORTH);
        global.getFrame().pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
