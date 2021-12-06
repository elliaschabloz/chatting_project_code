package src.view;
import javax.swing.*; 
import src.model.*;
import java.awt.*;  
import java.awt.event.*;  

public class ConnexionGUI {
	public ListUser listUser;
	
	public static void main(String[] args) {  
		 JFrame f=new JFrame("CONNEXION");  
		 JTextField tf=new JTextField();
		 final JLabel l = new JLabel();
		 tf.setBounds(50,50, 150,20); 
		 l.setBounds(50,100, 150,20);  
		 JButton b=new JButton("Connect");  
		 b.setBounds(50,150,100,30);  
		 
		 b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {  
			        try{ 
			        //Connect("ok");
			        String reussite="Your are connected";  
			        l.setText(reussite);
			        }catch(Exception ex){System.out.println(ex);}  
			    } 
		 });
		 
		 f.add(b);f.add(tf);f.add(l);
		 f.setSize(400,400);  
		 f.setLayout(null);  
		 f.setVisible(true); 
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static void Connect(String pseudo) {
	}
	private boolean CheckPseudoUnicity(String pseudo) {
		boolean check= false;
	    return check;
	}
	 
}

