

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import control.*;
import model.*;
import view.*;


public class Main {

	public static void main(String args[]) throws IOException {
	
		Runnable runSrv =
				new Runnable() {
			public void run() {
				TCP.Server(1234);
			}
		};
		Thread th = new Thread(runSrv);
		th.start();
		
		
		
	}
}
