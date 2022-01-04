package src.control;
import src.model.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

/* UDP Class for Connection=CheckingPseudoUnicity in broadcast
 * and disconnection which is a broadcast message.
 * UDP listening port is fixed at 2020 for all users.
 * Sockets are closed after each emitted messages.
 * The udpateListUser() method will permanently run after init
 * and listen on udp port.
*/

public class UDP extends Thread  {
	int udpPort;
	public String userPseudo;
	public ArrayList<String> connectedUsers;
	
	public UDP(int port,String userPseudo) {
		this.udpPort = port;
		this.userPseudo=userPseudo;
		this.connectedUsers= new ArrayList<String>();
	}
	
	@Override
	public void run() {
		
		System.out.printf("Début de l'écoute sur la socket 2020 \n");
		
		while(true) {
			try {
				updateListUser();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPseudo(String pseudo) {
		this.userPseudo=pseudo;
	}
	
	private void sendToAll(String MsgToAll) throws IOException {		
		// PARTIE SEND MESSAGE EN BROADCAST 
		DatagramSocket senderSocket = new DatagramSocket();
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.udpPort);
		senderSocket.send(datagramPacket);
		System.out.println("Message envoyé : " + MsgToAll +" sur le port "+this.udpPort);
		senderSocket.close();
	}	
	
	public ArrayList<String> getAllConnected() throws IOException {				
		int BUFFER_SIZE = 300;
		
		// PARTIE SEND MESSAGE
		String MsgToAll="Who is connected ?";
		DatagramSocket Socket = new DatagramSocket(2020);
		System.out.printf("socket :"+Socket.getLocalPort()+" \n");
		byte[] data = MsgToAll.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		datagramPacket.setPort(this.udpPort);
		Socket.send(datagramPacket);
		
		// PARTIE RECEIVE MESSAGE
		DatagramPacket receiverPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
		Socket.setSoTimeout(2*1000);

		while(true) {
			try {
				System.out.printf("dans le try \n");
				Socket.receive(receiverPacket);  
				if(!(receiverPacket.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))) {
					String rcv_msg = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
					System.out.printf("msg recu :%s\n",rcv_msg);
		      
					(this.connectedUsers).add(rcv_msg.substring(5));
					System.out.printf("utilisateur ajouté : "+this.connectedUsers+"\n");
				}	
			}
			catch(SocketTimeoutException e){
				break;
			}
		}
				
		Socket.close();
		System.out.printf("Liste initiale : "+(this.connectedUsers)+"\n");
		//list.updateListUser();
		return this.connectedUsers;
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
		
		DatagramSocket socket = new DatagramSocket(this.udpPort);
		DatagramPacket received_Packet = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
		socket.receive(received_Packet);
		String rcv_msg = new String(received_Packet.getData(), 0, received_Packet.getLength());
		System.out.printf("message received : "+rcv_msg+"\n");
		String token = rcv_msg.substring(0,4);
		if (!(received_Packet.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))) {
			if(token.equals("Conn")) {
				//User Connected Add to user List
				this.connectedUsers.add(rcv_msg.substring(11));
			}else if(token.equals("Disc")) {
				//User Disconnected Remove from UserList
				this.connectedUsers.remove(rcv_msg.substring(14));
			}else if(token.equals("Who'")) {
				//User Used a broadcast have to respond
				byte[] msg_buff = ("I am "+userPseudo).getBytes();
				InetAddress target_Address = received_Packet.getAddress();
				int target_Port = received_Packet.getPort();
				System.out.println("Send : I am "+userPseudo+"\n");
				DatagramPacket response = new DatagramPacket(msg_buff,msg_buff.length,target_Address,target_Port);
				socket.send(response);
				
			}else if(token.equals("I am")) {
				this.connectedUsers.add(rcv_msg.substring(6));
			}
		}
		socket.close();
	}
}
