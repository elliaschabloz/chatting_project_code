package control;
import java.net.*;

import model.User;
import view.ConnexionGUI;
import view.MainWindow;

import java.io.*;

public class TCPServerThread extends Thread {

	private Socket socket;
	private String remoteHost;
	 
    public TCPServerThread(Socket socket,String host) {
        this.socket = socket;
        this.remoteHost = host;
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
                //System.out.println("j'ai recu un truc");
                if(response==null) break;
                System.out.println("Server: " + response);
                for(User u:MainWindow.userList) {
                	if(u.getHostname().equals(this.remoteHost)) {
                		u.socketUser = socket;
                		
                		ConnexionGUI.myMainWindow.insertRow(u,MainWindow.udpListener.me, response);
                	}
                	
                }
                
				
			} while (true);
			socket.close();
			System.out.println("Socket ferm� !");
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
