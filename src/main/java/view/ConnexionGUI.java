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
	JFrame f;
	//public ListUser listUser;
	static UDP udpListener = new UDP(2020,null);
	public static MainWindow myMainWindow;
	
	public ConnexionGUI() {
		makeUI();
	}
	
	public static void main(String[] args) {  

		ConnexionGUI myConnexionGUI = new ConnexionGUI();
		
		 
		 
	}

	public static void makeUI() {
		
		final JFrame f=new JFrame("CONNEXION");  		 
		final JTextField tf=new JTextField();
		final JLabel labelMessage = new JLabel();
		JLabel labelPseudo = new JLabel();
		labelPseudo.setBounds(45,45,80,20); 
		tf.setBounds(115,45, 150,20); 
		labelMessage.setBounds(45,70, 250,20);  
		labelPseudo.setText("Pseudo :");
		JButton b=new JButton("Connect");
		tf.setText("Enter your Pseudo");
		b.setBounds(140,110,100,30);
		
		f.add(b);f.add(tf);f.add(labelMessage);f.add(labelPseudo);
		f.setSize(360,200);  
		f.setLayout(null);  
		f.setVisible(true); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "";
				String pseudo = tf.getText();
				try {
					text = Connect(pseudo);
					if(!text.equals("Enter an Unused Pseudo ")) {
						udpListener.setPseudo(pseudo);
						myMainWindow = new MainWindow(udpListener);
						udpListener.myMainWindow = myMainWindow;
						udpListener.myMainWindow.frame.setVisible(true);
						f.setVisible(false);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				labelMessage.setText(text);		
			}
		 });

		 
	}
	
	public static String Connect(String pseudo) throws IOException {
		UDP udpConnect = new UDP(2020,pseudo);
		String retour = "";
		try{
        	
        	if (CheckPseudoUnicity(pseudo)) {
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
		boolean check= true;
		//Get all user connected and verify if your pseudo is already in 
		udpListener.userList = udpListener.getAllConnected(); 
		for(User user:udpListener.userList) {
			System.out.println("ici");
			if(user.pseudo.equals(pseudo)) {
				check=false;
				break;
			}
		}
//		check = (udpListener.userList).contains(pseudo);
		System.out.printf("Pseudo unique ? : "+ check +"\n");
	    return check;
	}
	 
}

