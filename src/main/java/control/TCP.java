package control;
import java.net.*;
import java.sql.Connection;

import model.DataBase;
import model.User;
import view.MainWindow;

import java.io.*;


public class TCP {
    
	public static void main(String[] args) {
       
        String hostname = null;
        
        // creates a thread that will run a server that listens 
        // to the TCP connections
        Runnable runSrv =
				new Runnable() {
			public void run() {
				Server(1234);
			}
		};
		Thread th = new Thread(runSrv);
		th.start();
		
        try {
			Socket userSocket = StartChattingSessionWith(hostname, 1234);
			SendTo(userSocket, "coucou");
			//CloseChattingSessionWith(userSocket);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }
	
	public static Socket StartChattingSessionWith(String hostname, int port) throws UnknownHostException, IOException {
		// create the socket for a specific user for them to chat through
		Socket socket = new Socket(hostname, port);
		return socket;
	}
	
	public static void SendTo(Socket socket, String msg) {
		OutputStream output;
		PrintWriter writer;
		try {
			output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			//send message
	        writer.println(msg);
			//socket.close();
	        //AJOUTER ENVOI DANS LA DB
	        Connection con = null;
			con = DataBase.initDB(con);
			User rcver=null;
			for(User u: MainWindow.userList) {
				if(u.socketUser.equals(socket)) {
					rcver = u;
					break;
				}
			}
			try {
				DataBase.addMsgToDB(con, MainWindow.udpListener.me.pseudo,rcver.pseudo,msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void CloseChattingSessionWith(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static void Client(String hostname, int port) {
		try (Socket socket = new Socket(hostname, port)) {
			 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            String message;
 
            do {	
                //write message
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Client:");
                message = br.readLine();
                writer.println(message);	
				
			} while (!message.equals("bye"));
			socket.close();
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
	}
	
	public static void Server(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
	 
			while (true) {
				Socket socket = serverSocket.accept();
	            System.out.println("New client connected");
	            String remoteHost = socket.getInetAddress().getHostName();
	            
	            new TCPServerThread(socket,remoteHost).start();
	        }
		} catch (IOException ex) {
	        System.out.println("Server exception: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}

}
