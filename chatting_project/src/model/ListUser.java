package src.model;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ListUser{

	public userList ConnectedUsers;
	
	public ListUser(userList list) {
		this.ConnectedUsers = list;
	}
	
	public boolean isConnected(String pseudo) {
		boolean check = false;
		check = this.ConnectedUsers.contains(pseudo);
		return check;
	}

	//  On doit créer un thread qui sera en écoute permanenet pour mettre à jour 
	//  la list des utilisateurs 
	@SuppressWarnings("resource")
	public void updateListUser() throws IOException {
		DatagramSocket socket;
		socket = new DatagramSocket();
		int BUFFER_SIZE = 256;

		System.out.printf("wainting message\n");
		DatagramPacket received_Packet = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
		socket.receive(received_Packet);
		System.out.printf("message received\n");
		String rcv_msg = new String(received_Packet.getData(), 0, received_Packet.getLength());
		
		String token = rcv_msg.substring(0,4);
		
		if(token.equals("Conne")) {
			//User Connected Add to user List
			//userList.add(rcv_msg.substring(10));
		}else if(token.equals("Disco")) {
			//User Disconnected Remove from UserList
			//userList.remove(rcv_msg.substring(13));
		}else if(token.equals("Who's")) {
			//Doit envoyer I am son pseudo au demandeur
			
			byte[] msg_buff = ("I am ").getBytes();
			InetAddress target_Address = received_Packet.getAddress();
			int target_Port = received_Packet.getPort();
			System.out.println("Send : I am ######");
			DatagramPacket response = new DatagramPacket(msg_buff,msg_buff.length,target_Address,target_Port);
			socket.send(response);
			
		}else if(token.equals("I am ")) {
			//userList.append(rcv_msg.substring(5));
		}
		
		
		
	}
}
	  