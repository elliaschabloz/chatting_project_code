package src.control;
import java.net.*;
import java.io.*;

public class TCPServerThread extends Thread {

	private Socket socket;
	 
    public TCPServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
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
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
