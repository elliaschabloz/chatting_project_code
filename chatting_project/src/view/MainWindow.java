package src.view;

import src.control.*;
import src.model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class MainWindow {
	public String userPseudo;
	private JFrame frame;
	private JTable connectedUser;
	private JTextField messageToSend;
	private JTable messageViewUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow("moi");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow(String userPseudo) {
		this.userPseudo=userPseudo;
		initialize();
	}
	/**
	 * UTILS FONCTION
	 * 
	 */
	SimpleDateFormat formater = new SimpleDateFormat("h:mm a");
	
	
	/*
	 * CREATE A TABBED TO START A CONVERSATION  
	 * */
	
	private JPanel createTab(final Object name) {
		JPanel tabUser = new JPanel();
		tabUser.setLayout(new MigLayout("", "[grow]", "[300.00,grow][grow]"));
		
		JPanel messageView = new JPanel();
		messageView.setBackground(Color.WHITE);
		tabUser.add(messageView, "cell 0 0,grow");
		messageView.setLayout(new BoxLayout(messageView, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		messageView.add(scrollPane);
		
		messageViewUser = new JTable();
		messageViewUser.setShowVerticalLines(false);
		scrollPane.setViewportView(messageViewUser);
		
		final DefaultTableModel modelMessage = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"User", "Date", "Message"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, Object.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		
		messageViewUser.setModel(modelMessage);
		messageViewUser.getColumnModel().getColumn(0).setPreferredWidth(50);
		messageViewUser.getColumnModel().getColumn(0).setMinWidth(10);
		messageViewUser.getColumnModel().getColumn(0).setMaxWidth(60);
		messageViewUser.getColumnModel().getColumn(1).setPreferredWidth(70);
		messageViewUser.getColumnModel().getColumn(1).setMaxWidth(75);
		messageViewUser.setTableHeader(null);
		JPanel messageArea = new JPanel();
		tabUser.add(messageArea, "cell 0 1,grow");
		messageArea.setLayout(new MigLayout("", "[grow][]", "[]"));
		
		messageToSend = new JTextField();
		messageArea.add(messageToSend, "cell 0 0,growx");
		messageToSend.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			modelMessage.addRow(new Object[]{name,formater.format(new Date())
					,messageToSend.getText()
					});
			
			}
		});
		messageArea.add(btnNewButton, "cell 1 0");
		
		return tabUser;
	};
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane);
		
		JPanel userPanel = new JPanel();
		splitPane.setRightComponent(userPanel);
		userPanel.setLayout(new MigLayout("", "[grow]", "[279.00,grow][grow]"));
		
		connectedUser = new JTable();
		connectedUser.setFont(new Font("Tahoma", Font.PLAIN, 13));
		connectedUser.setShowVerticalLines(false);
		DefaultTableModel modelUser =new DefaultTableModel(
				new Object[][] {
					{"User 1"},
					{"User 2"},
					{"User 3"},
				},
				new String[] {
					"Connected Users"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			}; 
		connectedUser.setModel(modelUser);
		userPanel.add(connectedUser, "cell 0 0,grow");
		
		

		
		JPanel usrOptions = new JPanel();
		userPanel.add(usrOptions, "cell 0 1,grow");
		SpringLayout sl_usrOptions = new SpringLayout();
		usrOptions.setLayout(sl_usrOptions);
		
		
		String[] optionsList = {"Options...","Change Pseudo","Deconnection"};
		JComboBox comboBox = new JComboBox(optionsList);

		sl_usrOptions.putConstraint(SpringLayout.NORTH, comboBox, 23, SpringLayout.NORTH, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.WEST, comboBox, 15, SpringLayout.WEST, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.EAST, comboBox, 135, SpringLayout.WEST, usrOptions);
		
		comboBox.addActionListener(new JComboBox () {
		    public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox)e.getSource();
		        String optionSelected = (String)cb.getSelectedItem();
		        if(optionSelected.equals("Change Pseudo")) {
		        	try {
						ChangePseudo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }else if(optionSelected.equals("Deconnection")) {
		        	Disconnect();
		        }
		    }});
		
		usrOptions.add(comboBox);
		
		JPanel messagePanel = new JPanel();
		splitPane.setLeftComponent(messagePanel);
		messagePanel.setLayout(new MigLayout("", "[400.00,grow]", "[grow]"));
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		messagePanel.add(tabbedPane, "cell 0 0,grow");
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Welcome", null, panel_1, null);
		
		
		/*
		 * SECTION LISTENER AND EVENT
		 * 
		 * */
		connectedUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] sel;
				Object value;
				sel = connectedUser.getSelectedRows();

				 // récupérer les données de la table

		          TableModel tm = connectedUser.getModel();
		          value = tm.getValueAt(sel[0],0);
		          if(tabbedPane.indexOfTab((String) value)==-1) {
		        	  tabbedPane.addTab((String) value,null,createTab(userPseudo),null);
		          };
		          tabbedPane.setSelectedIndex(tabbedPane.indexOfTab((String) value));
		          }
		});
	}
	
	
	public void Disconnect() {
		JFrame disconnectFrame = new JFrame();
		int result = JOptionPane.showConfirmDialog(disconnectFrame,"Confirm your deconnection");		
		if(result==0) {
			/*
			*insert upd disconnect
			*/
			System.out.println("Normalement on est d�connect� ");

		}
	}
	
	private boolean CheckPseudoUnicity(String pseudo) throws IOException {
		//on doit récupérer les pseudos de tous les users connectés
		//puis vérifier l'appartenance du pseudo à cette liste
		boolean unique;
		UDP udp = new UDP(2525,null);
		List<String> allConnected = udp.getAllConnected();
		if (allConnected.contains(pseudo)) {
			unique = false;
		} else unique=true;
		return unique;
	}
	
	private void ChangePseudo() throws IOException{
		JFrame changePseudoFrame= new JFrame();
		boolean unique;
		do{

			String retour = JOptionPane.showInputDialog(changePseudoFrame, "Please enter a valid Pseudo", "Change Pseudo", JOptionPane.QUESTION_MESSAGE);
			unique = CheckPseudoUnicity(retour);
		}while(!unique);
	}
	
	public void SendMsgTo(String msg, String receiver) {
		
	}
	
	public String RecvMsgFrom(String msg, String sender) {
		return "";
	}
}
