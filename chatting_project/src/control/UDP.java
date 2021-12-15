package src.control;
import src.model.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class UDP extends Thread  {
	int port;
	String userPseudo;
	
	public UDP(int port,String userPseudo) {
		this.port = port;
		this.userPseudo=userPseudo;
		
	}
	
	private void sendToAll(String MsgToAll) throws IOException {		
		// PARTIE SEND MESSAGE EN BROADCAST 
		DatagramSocket senderSocket = new DatagramSocket();
		System.out.printf("socket :"+senderSocket.getLocalPort()+" \n");
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.port);
		senderSocket.send(datagramPacket);
		System.out.println("Message envoyé : " + MsgToAll +" sur le port "+this.port);
		senderSocket.close();
	}	
	
	public userList getAllConnected() throws IOException {
				
		userList usrList = new userList();
		int BUFFER_SIZE = 300;
		// PARTIE SEND MESSAGE
		String MsgToAll="Who's connected?";
		
		DatagramSocket Socket = new DatagramSocket();
		System.out.printf("socket :"+Socket.getLocalPort()+" \n");
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.port);
		Socket.send(datagramPacket);
		System.out.println("Message envoyé : " + MsgToAll +" sur le port "+this.port);
		
		// PARTIE RECEIVE MESSAGE 		
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		Socket.setSoTimeout(3*1000);
		ListUser list = new ListUser(usrList);
		
<<<<<<< HEAD
		while( System.currentTimeMillis() < timeout ) {
			//System.out.printf("a\n");
			receiverSocket.receive(receiverPacket);
			//System.out.printf("b\n");
			String rcv_msg = receiverPacket.getData().toString();
			//System.out.printf("c:%s\n",rcv_msg);
			usrList.add(rcv_msg);
			//System.out.printf("coucou\n");
=======
		while(true) {
			try {
				Socket.receive(receiverPacket);
				String rcv_msg = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
		        System.out.printf("msg recue :%s\n",rcv_msg);
		        System.out.printf("Liste User  ["+rcv_msg+"]\n");
				usrList.add(rcv_msg);
				System.out.printf("utilisateur ajouté "+usrList+"\n");
				
			}
			catch(SocketTimeoutException e){
				break;
			}
>>>>>>> b8c4828f70fd451a9b0d4d2775e901e90fbfe417
		}
				
		Socket.close();

		System.out.printf("Liste User  ["+list.ConnectedUsers+"]\n");
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
//		try {
//			usrList.ConnectedUsers=getAllConnected();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		while(true) {
			try {
				usrList.updateListUser(userPseudo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		
	}
}
