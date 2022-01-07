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
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
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
import java.awt.event.ActionEvent;

public class MainWindow {

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
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

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
		final DefaultTableModel model =new DefaultTableModel(
				new Object[][] {
					{null},
					{null},
					{null},
				},
				new String[] {
					"Connected Users"
				}
			); 
		connectedUser.setModel(model);
		userPanel.add(connectedUser, "cell 0 0,grow");
		
		JPanel usrOptions = new JPanel();
		userPanel.add(usrOptions, "cell 0 1,grow");
		SpringLayout sl_usrOptions = new SpringLayout();
		usrOptions.setLayout(sl_usrOptions);
		
		
		String[] optionsList = {"Start","Change Pseudo","Deconnection"};
		JComboBox comboBox = new JComboBox(optionsList);
		sl_usrOptions.putConstraint(SpringLayout.NORTH, comboBox, 23, SpringLayout.NORTH, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.WEST, comboBox, 15, SpringLayout.WEST, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.EAST, comboBox, 135, SpringLayout.WEST, usrOptions);
		usrOptions.add(comboBox);
		
		JPanel messagePanel = new JPanel();
		splitPane.setLeftComponent(messagePanel);
		messagePanel.setLayout(new MigLayout("", "[400.00,grow]", "[grow]"));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		messagePanel.add(tabbedPane, "cell 0 0,grow");
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Welcome", null, panel_1, null);
		
		JPanel message = new JPanel();
		tabbedPane.addTab("User 1", null, message, null);
		message.setLayout(new MigLayout("", "[grow]", "[300.00,grow][grow]"));
		
		JPanel messageView = new JPanel();
		messageView.setBackground(Color.WHITE);
		message.add(messageView, "cell 0 0,grow");
		messageView.setLayout(new BoxLayout(messageView, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		messageView.add(scrollPane);
		
		messageViewUser = new JTable();
		scrollPane.setViewportView(messageViewUser);
		messageViewUser.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null}
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
		});
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			model.addRow(new Object[]{"user 1",new java.util.Date().getTime()
					,messageToSend.getText()
					});
			
			}
		});
		messageViewUser.getColumnModel().getColumn(0).setPreferredWidth(50);
		messageViewUser.getColumnModel().getColumn(0).setMinWidth(10);
		messageViewUser.getColumnModel().getColumn(0).setMaxWidth(60);
		messageViewUser.getColumnModel().getColumn(1).setPreferredWidth(60);
		messageViewUser.getColumnModel().getColumn(1).setMaxWidth(60);
		messageViewUser.setTableHeader(null);
		JPanel messageArea = new JPanel();
		message.add(messageArea, "cell 0 1,grow");
		messageArea.setLayout(new MigLayout("", "[grow][]", "[]"));
		
		messageToSend = new JTextField();
		messageArea.add(messageToSend, "cell 0 0,growx");
		messageToSend.setColumns(10);
		
//		JButton btnNewButton = new JButton("Send");
//		btnNewButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			model.addRow(new Object[]{"user 1",new java.util.Date().getTime()
//					,messageToSend.getText()
//					});
//			
//			}
//		});
		messageArea.add(btnNewButton, "cell 1 0");
	}
	
	
	public void Disconnect() {
		
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
	
	private void ChangePseudo(String NewPseudo) throws IOException{
		boolean unique;
		do{
			unique = CheckPseudoUnicity(NewPseudo);
		}while(!unique);
	}
	
	public void SendMsgTo(String msg, String receiver) {
		
	}
	
	public String RecvMsgFrom(String msg, String sender) {
		return "";
	}
}
