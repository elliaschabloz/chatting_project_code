package src.view;


import javax.swing.*; 
import src.model.*;
import src.control.*;
import java.awt.*;  
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;  

public class ConnexionGUI {
	//public ListUser listUser;
	static UDP udpListener = new UDP(2020,null);
	
	
	public static void main(String[] args) {  
		 //final UDP udpListener = new UDP(2020,null);
		//		 udpListener.start();
		 JFrame f=new JFrame("CONNEXION");  
		 final JTextField tf=new JTextField();
		 final JLabel labelMessage = new JLabel();
		 final JLabel labelPseudo = new JLabel();
		 labelPseudo.setBounds(55,120,80,20); 
		 tf.setBounds(125,120, 150,20); 
		 labelMessage.setBounds(55,180, 250,20);  
		 labelPseudo.setText("Pseudo :");
		 JButton b=new JButton("Connect");
		 JButton b2=new JButton("Disconnect");
		 tf.setText("Enter your Pseudo");
		 b.setBounds(150,220,100,30);
		 b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 String text = "";
				 String pseudo = tf.getText();
				 try {
					text = Connect(pseudo);
					if(!text.equals("Enter an Unused Pseudo ")) {
						udpListener.setPseudo(pseudo);
						udpListener.start();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 labelMessage.setText(text);
			        /*try{
			        	//A voir 

			        	String enteredPseudo = tf.getText();
			        	udpListener.setPseudo(enteredPseudo);
			        	
			        	if (!CheckPseudoUnicity(enteredPseudo)) {
			        		//Add User to UseerList
			        		Connect(enteredPseudo);
			        		udpListener.start();
			        		//Connect("ok");
			        		String reussite="Your are connected as "+enteredPseudo;  
			        		labelMessage.setText(reussite);
			        	}else {
			        		//Retry enter pseudo
			        		String echec="Enter an Unused Pseudo ";  
			        		labelMessage.setText(echec);
			        	}
			        	
			        
			        }catch(Exception ex){System.out.println(ex);}  */
			    }
		 });
		 b2.setBounds(150,260,100,30);
		 b2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 String enteredPseudo = tf.getText();
				 try {
					Disconnect(enteredPseudo);
					String deco="Your are disconnected !";  
	        		labelMessage.setText(deco);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 
			 }
		 });
		 f.add(b);f.add(b2);f.add(tf);f.add(labelMessage);f.add(labelPseudo);
		 f.setSize(400,400);  
		 f.setLayout(null);  
		 f.setVisible(true); 
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static String Connect(String pseudo) throws IOException {
		UDP udpConnect = new UDP(2020,pseudo);
		String retour = "";
		try{
        	
        	if (!CheckPseudoUnicity(pseudo)) {
        		//Add User to UseerList
        		udpConnect.notifyConnection(pseudo);
        		retour = "You are connected as : " + pseudo;
        		//Connect("ok");
        	}else {
        		//Retry enter pseudo
        		retour ="Enter an Unused Pseudo ";
        	}
        }catch(Exception ex){System.out.println(ex);}
		return retour;
	}
	
	public static void Disconnect(String pseudo) throws IOException {
		udpListener.notifyDisconnection(pseudo);
		
	}
	
	private static boolean CheckPseudoUnicity(String pseudo) throws IOException {
		boolean check= false;
		//UDP udp=new UDP(2020,pseudo);
		//udp.connectedUsers = new ListUser();
		//userList AllConnectedUser = new userList();
		//AllConnectedUser = udp.getAllConnected(); 
		//check = AllConnectedUser.contains(pseudo);
		udpListener.connectedUsers = udpListener.getAllConnected(); 
		check = (udpListener.connectedUsers).contains(pseudo);
		System.out.printf("Pseudo unique : "+ !check +"\n");
	    return check;
	}
	 
}

