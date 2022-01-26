

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import control.*;
import model.*;
import view.*;


public class Main {

	public static void main(String args[]) throws IOException {
	
		
		
		ConnexionGUI myConnexionGUI = new ConnexionGUI();
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("debut");
		User userTest = new User("test","127.0.0.1");
		
		for(User u:MainWindow.userList) {
			System.out.println(u.pseudo);
			if(u.pseudo=="User1") {
//				MainWindow.insertRow(userTest,u, "coucou");
//				userTest.socketUser = TCP.StartChattingSessionWith(null, 1234);
//				TCP.SendTo(userTest.socketUser, "coucou");
				
			}
		}
		
		
	}
}
