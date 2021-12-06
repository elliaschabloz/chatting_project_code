package src.control;
import src.model.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class UDP {
	int port;
	
	public UDP(int port) {
		this.port = port;
	}
	/*
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
	*/
	
	private void sendToAll(String MsgToAll) throws IOException {		
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
				
		userList usrList = new userList();
		
		int BUFFER_SIZE = 80;
		
		// PARTIE SEND MESSAGE
		sendToAll("Who's connected?");
		System.out.printf("broadcast envoyé !\n");
		// PARTIE RECEIVE MESSAGE 
		final long timeout = System.currentTimeMillis() + 10000; //(60000 ms -> 1 minute)
		DatagramSocket receiverSocket = new DatagramSocket(this.port);
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		
		while( System.currentTimeMillis() < timeout ) {
			System.out.printf("a\n");
			receiverSocket.receive(receiverPacket);
			System.out.printf("b\n");
			String rcv_msg = receiverPacket.getData().toString();
			System.out.printf("c:%s\n",rcv_msg);
			usrList.append(rcv_msg);
			System.out.printf("coucou\n");
		}
		System.out.printf("timeout !\n");
		receiverSocket.close();
		ListUser list = new ListUser(usrList);
		//list.updateListUser();
		return list.ConnectedUsers;
	}
	
	public void notifyConnection(String pseudo) throws IOException {
		sendToAll("Connection "+pseudo);
		
	}
	
	public void notifyDisconnection(String pseudo) throws IOException {
		sendToAll("Disconnection "+ pseudo);
	}
	
	public void notifyChangedPseudo(String oldPseudo, String newPseudo) throws IOException {
		sendToAll(oldPseudo+" changed to "+newPseudo);
	}
	
	public void storeMsg(msgList history) {
		
	}
	
	public msgList getHistory(int id1, int id2) {
		return null;
	}
}
