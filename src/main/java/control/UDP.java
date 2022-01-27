package control;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import model.*;
import view.MainWindow;
import model.*;

/* UDP Class for Connection=CheckingPseudoUnicity in broadcast
 * and disconnection which is a broadcast message.
 * UDP listening port is fixed at 2020 for all users.
 * Sockets are closed after each emitted messages.
 * The udpateListUser() method will permanently run after init
 * and listen on udp port.
*/

public class UDP extends Thread  {
	int udpPort;
	//public String userPseudo;
	public User me;
	//public ArrayList<String> connectedUsers;
	public List<User> userList;
	
	public UDP(int port,String userPseudo) {
		this.udpPort = port;
		//this.userPseudo=userPseudo;
		//this.connectedUsers= new ArrayList<String>();
		try {
			this.me = new User(userPseudo,InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.userList = new ArrayList<User>();
	}
	
	@Override
	public void run() {
		
		System.out.printf("D�but de l'�coute sur la socket 2020 \n");
		
		while(true) {
			try {
				updateListUser();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPseudo(String pseudo) {
		this.me.pseudo=pseudo;
	}
	public String getPseudo() {
		return this.me.pseudo;
	}
	
	private void sendToAll(String MsgToAll) throws IOException {		
		// PARTIE SEND MESSAGE EN BROADCAST 
		DatagramSocket senderSocket = new DatagramSocket();
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.udpPort);
		senderSocket.send(datagramPacket);
		System.out.println("Message envoy� : " + MsgToAll +" sur le port "+this.udpPort);
		senderSocket.close();
	}	
	
	
	public List<User> getAllConnected() throws IOException {				
		
		// PARTIE SEND MESSAGE	
		//int BUFFER_SIZE = 300;
		DatagramSocket Socket = new DatagramSocket(2020);
		byte[] data = ("Who is connected ?").getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.udpPort);
		Socket.send(datagramPacket);
		System.out.println("Message envoy� : " + "Who is connected ?" +" sur le port "+this.udpPort);
		//NetworkInterface.getI
		// PARTIE RECEIVE MESSAGE
		int BUFFER_SIZE = 300;
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		Socket.setSoTimeout(2*1000);
		
		while(true) {
			try {
				Socket.receive(receiverPacket);
				//System.out.println("remoteIP="+NetworkInterface.getByInetAddress(receiverPacket.getAddress()));
				
				if( NetworkInterface.getByInetAddress(receiverPacket.getAddress()) == null) {
					String rcv_msg = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
					String pseudo = rcv_msg.substring(5);
					String hostname = receiverPacket.getAddress().getHostName();
			        System.out.printf("msg recu :%s\n",rcv_msg);
			        
			        User u = new User(pseudo,hostname);
			        
			        if( !this.userList.contains(u)){
						this.userList.add(u);
					}
					System.out.printf("utilisateur ajout� : "+this.userList+"\n");
				}
			}
			catch(SocketTimeoutException e){
				break;
			}
		}
				
		Socket.close();
//		System.out.printf("Liste initiale : "+(this.connectedUsers)+"\n");
		System.out.println("Liste user initiale : "+(this.userList));
//		System.out.println("Liste pair initiale : "+(this.pairList));
		//list.updateListUser();
		return this.userList;
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

	
	
	public void updateListUser() throws IOException {
		
		int BUFFER_SIZE = 300;

		System.out.printf("Waiting for updates\n");
		
		DatagramSocket socket = new DatagramSocket(2020);
		DatagramPacket received_Packet = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
				
		socket.receive(received_Packet);
		String rcv_msg = new String(received_Packet.getData(), 0, received_Packet.getLength());
		String pseudo;
		String token = rcv_msg.substring(0,4);
		
		//System.out.println("LocalAdd : " + InetAddress.getLocalHost().getHostAddress());
		//System.out.println("InetAdd : " + received_Packet.getAddress().getHostAddress() );
		
		if(NetworkInterface.getByInetAddress(received_Packet.getAddress()) == null) {
			System.out.printf("message received : "+rcv_msg+"\n");
			if(token.equals("Conn")) {
				//User Connected Add to user List
				pseudo = rcv_msg.substring(11);
				String hostname = received_Packet.getAddress().getHostName();
				User user = new User(pseudo, hostname);
				
				System.out.println(this.userList);
				System.out.println(pseudo);
				if( !this.userList.contains(user)){
					this.userList.add(user);
					
				}
				System.out.println("Nouvelle liste : " + this.userList);
				
			}else if(token.equals("Disc")) {
				//User Disconnected Remove from UserList
				pseudo = rcv_msg.substring(14);
				String hostname = received_Packet.getAddress().getHostName();
				
//				this.connectedUsers.remove(pseudo);
				
				User discUser = new User(pseudo, hostname);
				this.userList.remove(discUser);
				
//				System.out.println("Nouvelle liste : " + this.connectedUsers);
				System.out.println("Nouvelle userList : "+(this.userList));
				
			}else if(token.equals("Who ")) {
				//User Used a broadcast have to respond
				byte[] msg_buff = ("I am "+me.pseudo).getBytes();
				InetAddress target_Address = received_Packet.getAddress();
				int target_Port = received_Packet.getPort();
				System.out.println("Send : I am "+me.pseudo+"\n");
				DatagramPacket response = new DatagramPacket(msg_buff,msg_buff.length,target_Address,target_Port);
				socket.send(response);
				
			}else if(token.equals("I am")) {
				pseudo = rcv_msg.substring(6);
				String hostname = received_Packet.getAddress().getHostName();
				User user = new User(pseudo, hostname);
				
				System.out.println("liste actuelle="+this.userList);
				System.out.println("retour du contains="+this.userList.contains(user));
				if( !this.userList.contains(user)){
					this.userList.add(user);					
					
				}
//				System.out.println("Nouvelle liste : " + this.connectedUsers);
				System.out.println("Nouvelle userList : "+(this.userList));
			}
		}
		socket.close();
	}
}
