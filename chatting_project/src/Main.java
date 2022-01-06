package src;

import java.io.IOException;

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
		runSrv.run();
		
		String hostname = "insa-20072";
		TCP.Client(hostname, 1234);
	}
}
