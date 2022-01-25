package control;
import java.net.*;
import java.io.*;

public class TCPServerThread extends Thread {

	private Socket socket;
	public static volatile String response;
	 
    public TCPServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
//            String response;
			do {	
				//read entry
				System.out.println("avant bloquant"+response);
				input = socket.getInputStream();
				
				reader = new BufferedReader(new InputStreamReader(input));
 
                response = reader.readLine();
                //System.out.println("j'ai recu un truc");
                if(response==null) break;
                System.out.println("Server: " + response);
                
				
			} while (true);
			socket.close();
			System.out.println("Socket fermé !");
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
