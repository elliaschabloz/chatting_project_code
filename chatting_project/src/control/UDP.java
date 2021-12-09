package src.control;
import src.model.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class UDP implements Runnable  {
	int port;
	
	public UDP(int port) {
		this.port = port;
	}
	
	private void sendToAll(String MsgToAll) throws IOException {		
		// PARTIE SEND MESSAGE EN BROADCAST 
		DatagramSocket senderSocket = new DatagramSocket();
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.port);
		senderSocket.send(datagramPacket);
		System.out.println("Message envoyé : " + MsgToAll);
		senderSocket.close();
	}	
	
	public userList getAllConnected() throws IOException {
				
		userList usrList = new userList();
		
		int BUFFER_SIZE = 80;
		
		// PARTIE SEND MESSAGE
		sendToAll("Who's connected?");
		System.out.printf("broadcast envoyé!\n");
		
		// PARTIE RECEIVE MESSAGE 		
		DatagramSocket receiverSocket = new DatagramSocket();			
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		String rcv_msg = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
        System.out.printf("msg recue :%s\n",rcv_msg);
		usrList.add(rcv_msg);
		System.out.printf("utilisateur ajouté à userlist !\n");
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

	@Override
	
	
	
	
	
	public void run() {
		userList list = new userList();
		ListUser usrList = new ListUser(list);
		try {
			usrList.ConnectedUsers=getAllConnected();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			try {
				usrList.updateListUser();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		
	}
}
