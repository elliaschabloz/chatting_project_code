package src;

import java.io.IOException;
import java.net.Socket;

import src.control.*;
import src.model.*;
import src.view.*;


public class Main {

	public static void main(String args[]) throws IOException {
		/*
		String myPseudo = "toto";
		UDP udp = new UDP(1234,null);
		
		String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        */
		//TCP.Server(1234);
		
		
		Runnable runSrv =
				new Runnable() {
			public void run() {
				TCP.Server(1234);
			}
		};
		Thread th = new Thread(runSrv);
		th.start();
		
		String hostname = "insa-20072";
		/*
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//TCP.Client(hostname, 1234);
		Socket clientSocket = TCP.StartChattingSessionWith(hostname, 1234);
		TCP.SendTo(clientSocket, "coucou");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TCP.SendTo(clientSocket, "c'est moi");
		System.out.println("après c'est moi");

		TCP.CloseChattingSessionWith(clientSocket);
	}
}
