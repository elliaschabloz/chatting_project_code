package control;
import model.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class UDP {
	int port;
	
	public UDP(int port) {
		this.port = port;
	}
	
	
	public userList getAllConnected() throws IOException {
		TimerTask task = new TimerTask() {
			public void run() {
				
			}
		};
		
		userList usrList = new userList();
		int BUFFER_SIZE = 80;
		
		DatagramSocket senderSocket = new DatagramSocket();
		String msg = "Who's connected?";
		byte[] data = msg.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.port);
		senderSocket.send(datagramPacket);
		senderSocket.close();
		
		DatagramSocket receiverSocket = new DatagramSocket(this.port);
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		receiverSocket.receive(receiverPacket);
		data = receiverPacket.getData();
		
		String rcv_msg = data.toString();
		usrList.append(rcv_msg);
		receiverSocket.close();
		return usrList;
	}
	
	public void notifyConnection(String pseudo) {
		
	}
	
	public void notifyDisconnection(String pseudo) {
		
	}
	
	public void notifyChangedPseudo(String oldPseudo, String newPseudo) {
		
	}
	
	public void storeMsg(msgList history) {
		
	}
	
	public msgList getHistory(int id1, int id2) {
		return null;
	}
}
