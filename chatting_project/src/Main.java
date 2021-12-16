package src;

import java.io.IOException;

import src.control.*;
import src.model.*;
import src.view.*;


public class Main {

	public static void main(String args[]) throws IOException {
		String myPseudo = "toto";
		UDP udp = new UDP(1234,null);
		System.out.printf("avant getAllConnected\n");
		ListUser connectedUsers = udp.getAllConnected();
		System.out.printf("apres getAllConnected\n");
		//ListUser connectedUsersList = new ListUser();
		boolean exist = connectedUsers.isConnected(myPseudo);
		System.out.printf("valid=%s\n", exist);	
		
	}
}
