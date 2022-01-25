package view;


import javax.swing.*;

import control.UDP;
import model.*;
import control.*;
import java.awt.*;  
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;  

public class ConnexionGUI {
	JFrame frame;
	//public ListUser listUser;
	static UDP udpListener = new UDP(2020,null);
	
	
	
	public static void main(String[] args) {  
		 //final UDP udpListener = new UDP(2020,null);
		//		 udpListener.start();
		 final JFrame f=new JFrame("CONNEXION");  
		 final JTextField tf=new JTextField();
		 final JLabel labelMessage = new JLabel();
		 final JLabel labelPseudo = new JLabel();
		 labelPseudo.setBounds(45,45,80,20); 
		 tf.setBounds(115,45, 150,20); 
		 labelMessage.setBounds(45,70, 250,20);  
		 labelPseudo.setText("Pseudo :");
		 JButton b=new JButton("Connect");
		 tf.setText("Enter your Pseudo");
		 b.setBounds(140,110,100,30);
		 
		 /*
		  * Listener for the bouton connect 
		  * 		-> if your pseudo is unique open the main Windows
		  * 		-> else demande the user to enter an unused pseudo
		  * 
		  */
		 
		 b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 String text = "";
				 String pseudo = tf.getText();
				 try {
					text = Connect(pseudo);
					if(!text.equals("Enter an Unused Pseudo ")) {
						udpListener.setPseudo(pseudo);
						MainWindow window = new MainWindow();
						window.frame.setVisible(true);
						f.setVisible(false);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 labelMessage.setText(text);
			    }
		 });

		 f.add(b);f.add(tf);f.add(labelMessage);f.add(labelPseudo);
		 f.setSize(360,200);  
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
		//Notify all user your are disconnected
		udpListener.notifyDisconnection(pseudo);		
	}
	
	private static boolean CheckPseudoUnicity(String pseudo) throws IOException {
		boolean check= false;
		//Get all user connected and verify if your pseudo is already in 
		udpListener.connectedUsers = udpListener.getAllConnected(); 
		check = (udpListener.connectedUsers).contains(pseudo);
		System.out.printf("Pseudo unique : "+ !check +"\n");
	    return check;
	}
	 
}

