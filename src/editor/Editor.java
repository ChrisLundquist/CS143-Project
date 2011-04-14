package editor;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.io.File;
import javax.swing.JTextField;
import game.Map;
import java.awt.FlowLayout;

public class Editor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jToolBar = null;
    private JButton openButton = null;
    private JButton saveButton = null;
    private JPanel mapSettings = null;
    private JTextField mapNameField = null;
    private JFileChooser jfc = null;
    private Map map = null;   //  @jve:decl-index=0:visual-constraint=""
    private JLabel jLabel = null;
    private JTabbedPane jTabbedPane = null;
    private JScrollPane jScrollPane = null;
    private JTable spawningPositionTable = null;
    private JScrollPane jScrollPane1 = null;
    private JTable actorTable = null;
    /**
     * This method initializes jToolBar	
     * 	
     * @return javax.swing.JToolBar	
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getOpenButton());
            jToolBar.add(getSaveButton());
        }
        return jToolBar;
    }

    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getOpenButton() {
        if (openButton == null) {
            openButton = new JButton("Open");
            openButton.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {


                    if (getJfc().showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
                        return;
                    loadMap(game.Map.load(getJfc().getSelectedFile()));
                }

            });
        }
        return openButton;
    }

    protected void loadMap(Map map) {
        this.map = map;
        mapNameField.setText(map.name);
        spawningPositionTable.setModel(new PositionTable(map.spawningPositions));
        actorTable.setModel(new ActorTable(map.actors));
    }

    /**
     * This method initializes jButton1	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton("Save");
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getMap().write();
                }
            });
        }
        return saveButton;
    }

    /**
     * This method initializes mapSettings	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getMapSettings() {
        if (mapSettings == null) {
            jLabel = new JLabel();
            jLabel.setText("Map Name");
            jLabel.setName("jLabel");
            mapSettings = new JPanel();
            mapSettings.setLayout(new FlowLayout());
            mapSettings.add(jLabel, null);
            mapSettings.add(getMapNameField(), null);
        }
        return mapSettings;
    }

    /**
     * This method initializes mapNameField	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getMapNameField() {
        if (mapNameField == null) {
            mapNameField = new JTextField();
            mapNameField.setText("Map Name");
            mapNameField.setName("mapNameField");
            mapNameField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ((e.getPropertyName().equals("text"))) {
                        getMap().name = mapNameField.getText(); 
                    }
                }
            });
        }
        return mapNameField;
    }

    /**
     * This method initializes jfc	
     * 	
     * @return javax.swing.JFileChooser	
     */
    private JFileChooser getJfc() {
        if (jfc == null) {
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("assets/maps/"));
        }
        return jfc;
    }

    /**
     * This method initializes map1	
     * 	
     * @return game.Map	
     */
    private Map getMap() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    /**
     * This method initializes jTabbedPane	
     * 	
     * @return javax.swing.JTabbedPane	
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab(null, null, getMapSettings(), null);
            jTabbedPane.addTab(null, null, getJScrollPane(), null);
            jTabbedPane.addTab(null, null, getJScrollPane1(), null);
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getSpawningPositionTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes spawningPositionTable	
     * 	
     * @return javax.swing.JTable	
     */
    private JTable getSpawningPositionTable() {
        if (spawningPositionTable == null) {
            spawningPositionTable = new JTable();
        }
        return spawningPositionTable;
    }

    /**
     * This method initializes jScrollPane1	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getActorTable());
        }
        return jScrollPane1;
    }

    /**
     * This method initializes actorTable	
     * 	
     * @return javax.swing.JTable	
     */
    private JTable getActorTable() {
        if (actorTable == null) {
            actorTable = new JTable();
        }
        return actorTable;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Editor thisClass = new Editor();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     */
    public Editor() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(712, 473);
        this.setContentPane(getJContentPane());
        this.setTitle("Map Editor");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJToolBar(), BorderLayout.NORTH);
            jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
