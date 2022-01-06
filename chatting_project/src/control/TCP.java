package src.control;
import src.model.*;
import java.net.*;
import java.io.*;
import java.util.*;


public class TCP {
	private Socket socket;
	public TCP(Socket socket) {
		this.socket = socket;
	}
	public void init() throws IOException{
		final InputStream input=null;
		final BufferedReader reader=null;
		final OutputStream output=null;
		final PrintWriter writer=null;
		
		//Thread SendThread = new Thread
		Runnable runSend =
				new Runnable() {
			public void run() {
				System.out.println("Sender Runnable running");
				TCPSend(output, writer);
			}
		};
		
		Runnable runRcv =
				new Runnable() {
			public void run() {
				System.out.println("Rcver Runnable running");
				TCPRcv(input,reader);
			}
		};
				
		Thread RcvThread = new Thread(runRcv);
		Thread SendThread = new Thread(runSend);
		
		RcvThread.start();
		SendThread.start();
	}
	
	public void TCPSend(OutputStream output, PrintWriter writer) {
		try {
			output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			String message;
			do {	
                //write message
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Client:");
                message = br.readLine();
                writer.println(message);	
				
			} while (!message.equals("bye"));
			socket.close();
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
	
	public void TCPRcv(InputStream input, BufferedReader reader) {
		try {
			String response;
			do {	
				//read entry
				input = socket.getInputStream();
				reader = new BufferedReader(new InputStreamReader(input));
 
                response = reader.readLine();
                System.out.println("j'ai recu un truc");
                System.out.println("Server: " + response);
				
			} while (!response.equals("bye"));
			socket.close();
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
	
}