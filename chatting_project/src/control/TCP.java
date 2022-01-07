package src.control;
import java.net.*;
import java.io.*;

public class TCP {
    
	public static void main(String[] args) {
        //if (args.length < 2) return;
 
        //String hostname = args[0];
        //int port = Integer.parseInt(args[1]);
		/*
		try {
			System.out.println("Hostname : " + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Server(1234);
		Client(null, 1234);
        */
		
    }
	
	public static Socket StartChattingSessionWith(String hostname, int port) throws UnknownHostException, IOException {
		
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void CloseChattingSessionWith(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	 
	            new TCPServerThread(socket).start();
	        }
		} catch (IOException ex) {
	        System.out.println("Server exception: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}

}
