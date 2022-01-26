package view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.LayerUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import control.TCP;
import control.UDP;
import model.*;
import net.miginfocom.swing.MigLayout;

public class MainWindow {
	public JFrame frame;
	public static List<User> userList = new ArrayList<User>();;
	private JTable connectedUser;
	private static JTable messageViewUser;
	static SimpleDateFormat formater = new SimpleDateFormat("h:mm a");
	public static final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	public static UDP udpListener;

	public MainWindow(UDP udpListener) {
		this.udpListener = udpListener;
		MainWindow.udpListener.start();

		Runnable runSrv =
				new Runnable() {
			public void run() {
				TCP.Server(1234);
			}
		};
		Thread th = new Thread(runSrv);
		th.start();
		
		initialize();
	}

	public JComponent makeUI() {
		User user1 = new User("User1","hostame1");
		User user2 = new User("User2","hostame2");
		userList.add(user1);userList.add(user2);
		
	    UIManager.put("TabbedPane.tabInsets", new Insets(2, 2, 2, 50));
	    final JTabbedPane tabbedPane = new JTabbedPane();
	    
	    //tabbedPane.addTab("Welcome", new JPanel());
	    
	    //create main panel divided with a splitpane
	    JPanel p = new JPanel(new BorderLayout());
	    JSplitPane splitPane = new JSplitPane();
		p.add(splitPane);
		
		//create and add components of splitpane
		JPanel listPanel = initListPanel(splitPane);
	    JPanel messagePanel = initMessagePanel(splitPane,tabbedPane);
	    
		splitPane.setLeftComponent(messagePanel);
		splitPane.setRightComponent(listPanel);
		
		//create and add list of user to listPanel
		final JTable connectedUser = initConnectedUser(userList);
		listPanel.add(connectedUser, "cell 0 0,grow");
		
		//create and add tab to messagePanel
	    //messagePanel.add(new JLayer<JTabbedPane>(tabbedPane, new CloseableTabbedPaneLayerUI()),"cell 0 0,grow");
//	    p.add(new JButton(new AbstractAction("add tab") {
//	      @Override
//	      public void actionPerformed(ActionEvent e) {
//	        tabbedPane.addTab("test", new JPanel());
//	      }
//	    }), BorderLayout.SOUTH);
	    connectedUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] sel;
				User value = null;
				
				sel = connectedUser.getSelectedRows();
				 // CHECK IF THE TAB ALREADY OPENED

		          TableModel tm = connectedUser.getModel();
		          String pseudo = (String) tm.getValueAt(sel[0],0);
		          for(User u : userList) {
						if(u.pseudo == pseudo) value = u;
		          }
		          if(tabbedPane.indexOfTab(pseudo)==-1) {
		        	  tabbedPane.addTab(pseudo,null,createTab(value),null);
		        	  //tabbedPane.addTab("test", new JPanel());
		          };
		          // OPEN THE TAB SELECTED
		          tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(pseudo));
		          }
		});
	    return p;
	  }
		
	public static void main(String[] args) {
		 JFrame f = new JFrame();
		 f.setBounds(100, 100, 600, 480);
		 f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		    
		 f.getContentPane().add(new MainWindow(udpListener).makeUI());		    
		 //f.setSize(320, 240);		   
		 f.setVisible(true);
	}
	
	
	
	private JPanel initListPanel(JSplitPane splitPane) {
		JPanel userPanel = new JPanel();	
		splitPane.setRightComponent(userPanel);
		userPanel.setLayout(new MigLayout("", "[grow]", "[279.00,grow][grow]"));
		return userPanel;
	}
	
	private JTable initConnectedUser(List<User> userList) {
		connectedUser = new JTable();
		connectedUser.setFont(new Font("Tahoma", Font.PLAIN, 13));
		connectedUser.setShowVerticalLines(false);
		
		User user1 = userList.get(0);
		User user2 = userList.get(1);
		
		DefaultTableModel modelUser =new DefaultTableModel(
				new String[][] {
					{user1.pseudo},
					{user2.pseudo},
				},
				new String[] {"Connected Users"})
		
		{
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
		
		
		return connectedUser;
	}
	
	private JPanel initUserOptions(JPanel userPanel, JComboBox comboBox) {
		JPanel usrOptions = new JPanel();
		userPanel.add(usrOptions, "cell 0 1,grow");
		SpringLayout sl_usrOptions = new SpringLayout();
		usrOptions.setLayout(sl_usrOptions);
		
		sl_usrOptions.putConstraint(SpringLayout.NORTH, comboBox, 23, SpringLayout.NORTH, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.WEST, comboBox, 15, SpringLayout.WEST, usrOptions);
		sl_usrOptions.putConstraint(SpringLayout.EAST, comboBox, 135, SpringLayout.WEST, usrOptions);
		
		usrOptions.add(comboBox);
		return usrOptions;
	}
	
	private JPanel initMessagePanel(JSplitPane splitPane, JTabbedPane tabbedPane) {
		JPanel messagePanel = new JPanel();
		splitPane.setLeftComponent(messagePanel);
		messagePanel.setLayout(new MigLayout("", "[400.00,grow]", "[grow]"));
		
		
		messagePanel.add(tabbedPane, "cell 0 0,grow");
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Welcome", null, panel_1, null);
		return messagePanel;
	}
	
	private static JPanel createTab(final User receiver) {
		
		
		
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
		
		/*
		 * THE MESSAGE VIEW BASED ON A TABLE  
		 *  IN THE FOLLOWING CODE WE CREATE A TABLE WITH SENDER,DATE AND MESSAGE
		 */
		
		
		final DefaultTableModel modelMessage = new DefaultTableModel(
				new Object[][] {},
				new String[] {"User", "Date", "Message"}) 
		;
		
//		Object[] ligne = {"tutu","oo","dzdzudi"};
//		modelMessage.addRow(ligne);
		if(receiver.tableUser==null) {
			receiver.tableUser= modelMessage;
		}	
		messageViewUser.setModel(receiver.tableUser);
		messageViewUser.getColumnModel().getColumn(0).setPreferredWidth(50);
		messageViewUser.getColumnModel().getColumn(0).setMinWidth(10);
		messageViewUser.getColumnModel().getColumn(0).setMaxWidth(60);
		messageViewUser.getColumnModel().getColumn(1).setPreferredWidth(70);
		messageViewUser.getColumnModel().getColumn(1).setMaxWidth(75);
		messageViewUser.setTableHeader(null);
		
		
		
		JPanel messageArea = new JPanel();
		tabUser.add(messageArea, "cell 0 1,grow");
		messageArea.setLayout(new MigLayout("", "[grow][]", "[]"));
		
		final JTextField messageToSend = new JTextField();
		messageArea.add(messageToSend, "cell 0 0,growx");
		messageToSend.setColumns(10);
		
		
		/*
		 * LISTENER TO SEND A MESSAGE IN THE ACTIVE TAB 
		 * 	AND WE ADD A NEW ROW FOR EACH MESSAGE SEND BY THE USER
		 */
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
			insertRow(udpListener.me, receiver, messageToSend.getText());
			
			TCP.SendTo(receiver.socketUser, messageToSend.getText());
			
//			if(messageToSend.getText().equals("close_tab")) {
//				System.out.println("remove tab");
//				tabbedPane.remove(tabbedPane.getSelectedComponent());
//			}
			messageToSend.setText("");
			
			
			}
		});
		messageArea.add(btnNewButton, "cell 1 0");
		Object[] res = new Object[2];
		res[0] = tabUser;
		res[1] = modelMessage;
		return tabUser;
	}
	
	public static void insertRow(User emitter, User receiver, String message) {
		Object[] ligne = {emitter.pseudo,formater.format(new Date()),message};
		try {
			receiver.tableUser.addRow(ligne);
		} catch (NullPointerException e) {
//			JPanel newUser = createTab(receiver);
//			tabbedPane.add(newUser);
			tabbedPane.addTab(emitter.pseudo,null,createTab(emitter),null);
			emitter.tableUser.addRow(ligne);
		}
		
		
//		modelMessage.addRow(new Object[]{emitter,formater.format(new Date())
//				,message
//				});
	}
	
	private void initialize() {
		UIManager.put("TabbedPane.tabInsets", new Insets(2, 2, 2, 50));
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane);
		
		String[] optionsList = {"Options...","Change Pseudo","Deconnection"};
		//JComboBox comboBox = new JComboBox(optionsList);
		JComboBox<String> comboBox = new JComboBox<String>();
		for(String s : optionsList) {
			comboBox.addItem(s);
		}

		
		
		User user1 = new User("User1","hostame1");
		User user2 = new User("User2","hostame2");
		userList.add(user1);userList.add(user2);
		
		// USER PANEL
		
		JPanel userPanel = initListPanel(splitPane);
		
		// CONNECTED USERS: TABLE OF USERS
		
		
		
		final JTable connectedUser = initConnectedUser(userList);
		userPanel.add(connectedUser, "cell 0 0,grow");
		
		// USER OPTIONS		
		
		JPanel usrOptions = initUserOptions(userPanel,comboBox);
		
		// MESSAGE PANEL		
		
		JPanel messagePanel = initMessagePanel(splitPane, tabbedPane);
		
		messagePanel.add(new JLayer<JTabbedPane>(tabbedPane, new CloseableTabbedPaneLayerUI()),"cell 0 0,grow");
		/*
		 * SECTION LISTENER AND EVENT
		 * 
		 */
		
		/*
		 *  OPTION SELECTION
		 */
//		comboBox.addActionListener(new JComboBox () {
//		    public void actionPerformed(ActionEvent e) {
//		        JComboBox cb = (JComboBox)e.getSource();
//		        String optionSelected = (String)cb.getSelectedItem();
//		        
//		        // OPTION CHOOSED BY THE USER
//		        if(optionSelected.equals("Change Pseudo")) {
//		        	try {
//						ChangePseudo();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//		        }else if(optionSelected.equals("Deconnection")) {
//		        	Disconnect();
//		        }
//		    }});
		
		/*
		 *  CREATION TAB FOR A DISCUSSION
		 */
		
		
		connectedUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] sel;
				User value = null;
				
				sel = connectedUser.getSelectedRows();
				 // CHECK IF THE TAB ALREADY OPENED

		          TableModel tm = connectedUser.getModel();
		          String pseudo = (String) tm.getValueAt(sel[0],0);
		          for(User u : userList) {
						if(u.pseudo == pseudo) value = u;
		          }
		          if(tabbedPane.indexOfTab(pseudo)==-1) {
		        	  tabbedPane.addTab(pseudo,null,createTab(value),null);
		        	 		        	 
		        	  try {							
		        		  value.socketUser = TCP.StartChattingSessionWith(null, 1234);
		        	  } catch (IOException e1) {
		        		  System.out.println("erreur bip : "+value.socketUser);
		        		  e1.printStackTrace();
		        	  }
		        	  
//		        	  tabbedPane.addTab("test", new JPanel());
		          };
		          // OPEN THE TAB SELECTED
		          tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(pseudo));
		          }
		});
		
		
	}
	
	class MyCloseButton extends JButton {
		  public MyCloseButton() {
		    super("x");
		    setBorder(BorderFactory.createEmptyBorder());
		    setFocusPainted(false);
		    setBorderPainted(false);
		    setContentAreaFilled(false);
		    setRolloverEnabled(false);
		  }
		  @Override
		  public Dimension getPreferredSize() {
		    return new Dimension(16, 16);
		  }
	}
	
	class CloseableTabbedPaneLayerUI extends LayerUI<JTabbedPane> {
		  JPanel p = new JPanel();
		  Point pt = new Point(-100, -100);
		  MyCloseButton button = new MyCloseButton();

		  public CloseableTabbedPaneLayerUI() {
		    super();
		  }

		  @Override
		  public void paint(Graphics g, JComponent c) {
		    super.paint(g, c);
		    if (c instanceof JLayer == false) {
		      return;
		    }
		    JLayer jlayer = (JLayer) c;
		    JTabbedPane tabPane = (JTabbedPane) jlayer.getView();
		    for (int i = 0; i < tabPane.getTabCount(); i++) {
		      Rectangle rect = tabPane.getBoundsAt(i);
		      Dimension d = button.getPreferredSize();
		      int x = rect.x + rect.width - d.width - 2;
		      int y = rect.y + (rect.height - d.height) / 2;
		      Rectangle r = new Rectangle(x, y, d.width, d.height);
		      button.setForeground(r.contains(pt) ? Color.RED : Color.BLACK);
		      SwingUtilities.paintComponent(g, button, p, r);
		    }
		  }

		  @Override
		  public void installUI(JComponent c) {
		    super.installUI(c);
		    ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK
		        | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		  }

		  @Override
		  public void uninstallUI(JComponent c) {
		    ((JLayer) c).setLayerEventMask(0);
		    super.uninstallUI(c);
		  }

		  @Override
		  protected void processMouseEvent(MouseEvent e, JLayer<? extends JTabbedPane> l) {
		    if (e.getID() != MouseEvent.MOUSE_CLICKED) {
		      return;
		    }
		    pt.setLocation(e.getPoint());
		    JTabbedPane tabbedPane = (JTabbedPane) l.getView();
		    int index = tabbedPane.indexAtLocation(pt.x, pt.y);
		    if (index >= 0) {
		      Rectangle rect = tabbedPane.getBoundsAt(index);
		      Dimension d = button.getPreferredSize();
		      int x = rect.x + rect.width - d.width - 2;
		      int y = rect.y + (rect.height - d.height) / 2;
		      Rectangle r = new Rectangle(x, y, d.width, d.height);
		      if (r.contains(pt)) {
		    	  for(User u : userList) {
		    		  if(u.pseudo.equals(tabbedPane.getTitleAt(index))) {
		    			  TCP.CloseChattingSessionWith(u.socketUser);
		    		  }
		    	  }
		    	
		        tabbedPane.removeTabAt(index);
		        
		        
		      }
		    }
		    l.getView().repaint();
		  }

		  @Override
		  protected void processMouseMotionEvent(MouseEvent e,
		      JLayer<? extends JTabbedPane> l) {
		    pt.setLocation(e.getPoint());
		    JTabbedPane tabbedPane = (JTabbedPane) l.getView();
		    int index = tabbedPane.indexAtLocation(pt.x, pt.y);
		    if (index >= 0) {
		      tabbedPane.repaint(tabbedPane.getBoundsAt(index));
		    } else {
		      tabbedPane.repaint();
		    }
		  }

	}
}


