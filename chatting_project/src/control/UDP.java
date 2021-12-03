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
	
	private UDPListener extends Thread{
		
		// PARTIE RECEIVE MESSAGE 
		DatagramSocket receiverSocket = new DatagramSocket(this.port);
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		receiverSocket.receive(receiverPacket);
		data = receiverPacket.getData();
		
		String rcv_msg = data.toString();
		String token = rcv_msg.substring(0,4);
		
		if(token.equals("Conne")) {
			//User Connected Add to user List
			userList.append(rcv_msg.substring(10));
			
		}else if(token.equals("Disco")) {
			//User Disconnected Remove from UserList
			userList.remove(rcv_msg.substring(13));
		
		}else if(token.equals("Who's")) {
			//Doit envoyer I am son pseudo au demandeur 
			
			
		}else if(token.equals("I am ")) {
			userList.append(rcv_msg.substring(5));
		}
		
		receiverSocket.close();
		
	}
	
	
	private void sendToAll(String MsgToAll) {		
		// PARTIE SEND MESSAGE EN BROADCAST 
		DatagramSocket senderSocket = new DatagramSocket();
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.port);
		senderSocket.send(datagramPacket);
		senderSocket.close();
	}
	
	
	
	
	
	
	public userList getAllConnected() throws IOException {
		TimerTask task = new TimerTask() {
			public void run() {
				
			}
		};
		
		userList usrList = new userList();
		int BUFFER_SIZE = 80;
		
		sendToAll("Who's connected?");
			
		// PARTIE RECEIVE MESSAGE 
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
		sendToAll("Connection "+pseudo);
		
	}
	
	public void notifyDisconnection(String pseudo) {
		sendToAll("Disconnection "+ pseudo);
	}
	
	public void notifyChangedPseudo(String oldPseudo, String newPseudo) {
		sendToAll(oldPseudo+" changed to "+newPseudo);
	}
	
	public void storeMsg(msgList history) {
		
	}
	
	public msgList getHistory(int id1, int id2) {
		return null;
	}
}
