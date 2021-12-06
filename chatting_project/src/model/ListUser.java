package model;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ListUser implments Runnable {

	public userList ConnectedUsers;

  public boolean isConnected(String pseudo) {
  	boolean check = false;
  	
  	return check;
  }

	//  On doit créer un thread qui sera en écoute permanenet pour mettre à jour 
	//  la list des utilisateurs 
  private void updateListUser() {
		private DatagramSocket socket;
		private boolean running;
		private byte[] buf = new byte[256];
		  
		socket = new DatagramSocket(2020);
		
		running = true;
		while (running) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    InetAddress address = packet.getAddress();
		    int port = packet.getPort();
		    packet = new DatagramPacket(buf, buf.length, address, port);
		    String received  = new String(packet.getData(), 0, packet.getLength());
		    
		    if (received.equals("end")) {
		        running = false;
		        continue;
		    }
		    try {      	
		    	//renvoie le paquet reçue 
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket.close();
	}
  public void run() {
	  updateListUser();
  }
		  
		  
}
	  