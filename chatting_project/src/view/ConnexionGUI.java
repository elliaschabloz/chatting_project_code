package src.view;


import javax.swing.*; 
import src.model.*;
import src.control.*;
import java.awt.*;  
import java.awt.event.*;
import java.io.IOException;  

public class ConnexionGUI {
	public ListUser listUser;
	
	
	
	public static void main(String[] args) {  
		 UDP udpListener = new UDP(5555,null);
		
		 udpListener.start();
		 JFrame f=new JFrame("CONNEXION");  
		 final JTextField tf=new JTextField();
		 final JLabel labelMessage = new JLabel();
		 final JLabel labelPseudo = new JLabel();
		 labelPseudo.setBounds(55,120,80,20); 
		 tf.setBounds(125,120, 150,20); 
		 labelMessage.setBounds(55,180, 250,20);  
		 labelPseudo.setText("Pseudo :");
		 JButton b=new JButton("Connect");
		 tf.setText("Enter your Pseudo");
		 b.setBounds(150,220,100,30);  
		 b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {  
			        try{
			        	//A voir 

			        	String enteredPseudo = tf.getText();
			        	
			        	if (!CheckPseudoUnicity(enteredPseudo)) {
			        		//Add User to UseerList
			        		Connect(enteredPseudo);
			        		//Connect("ok");
			        		String reussite="Your are connected as "+enteredPseudo;  
			        		labelMessage.setText(reussite);
			        	}else {
			        		//Retry enter pseudo
			        		String echec="Enter an Unused Pseudo ";  
			        		labelMessage.setText(echec);
			        	}
			        
			        
			        }catch(Exception ex){System.out.println(ex);}  
			    } 
		 });
		 
		 f.add(b);f.add(tf);f.add(labelMessage);f.add(labelPseudo);
		 f.setSize(400,400);  
		 f.setLayout(null);  
		 f.setVisible(true); 
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static void Connect(String pseudo) throws IOException {
		UDP udp = new UDP(2020,pseudo);
		udp.notifyConnection(pseudo);
		
	}
	private static boolean CheckPseudoUnicity(String pseudo) throws IOException {
		boolean check= false;
		UDP udp=new UDP(2020,pseudo);
		userList AllConnectedUser = new userList();
		AllConnectedUser = udp.getAllConnected(); 
		check = AllConnectedUser.contains(pseudo);
		System.out.printf("Valeur de checkUnicity : "+check+"\n");
	    return check;
	}
	 
}

