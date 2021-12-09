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
		 JFrame f=new JFrame("CONNEXION");  
		 final JTextField tf=new JTextField();
		 final JLabel l = new JLabel();
		 tf.setBounds(50,50, 150,20); 
		 l.setBounds(50,100, 150,20);  
		 JButton b=new JButton("Connect");
		 tf.setText("Enter your Pseudo");
		 b.setBounds(50,150,100,30);  
		 b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {  
			        try{
			        	//A voir 

			        	String enteredPseudo = tf.getText();
			        	
			        	if (CheckPseudoUnicity(enteredPseudo)) {
			        		//Add User to UseerList
			        		Connect(enteredPseudo);
			        		//Connect("ok");
			        		String reussite="Your are connected";  
					        l.setText(reussite);
			        	}else {
			        		//Retry enter pseudo
			        		String reussite="Enter an Unused Pseudo ";  
					        l.setText(reussite);
			        	}
			        
			        
			        }catch(Exception ex){System.out.println(ex);}  
			    } 
		 });
		 
		 f.add(b);f.add(tf);f.add(l);
		 f.setSize(400,400);  
		 f.setLayout(null);  
		 f.setVisible(true); 
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static void Connect(String pseudo) throws IOException {
		UDP udp = new UDP(2020);
		udp.notifyConnection(pseudo);
		
	}
	private static boolean CheckPseudoUnicity(String pseudo) throws IOException {
		boolean check= false;
		UDP udp=new UDP(2020);
		userList AllConnectedUser = new userList();
		AllConnectedUser = udp.getAllConnected(); 
		check = AllConnectedUser.contains(pseudo);
	    return check;
	}
	 
}

