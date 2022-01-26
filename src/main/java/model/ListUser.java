package model;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ListUser extends ArrayList<String>{
	
	public ArrayList<String> ConnectedUsers;
	
	public ListUser() {
		this.ConnectedUsers = new ArrayList<String>();
	}
	
	public boolean isConnected(String pseudo) {
		boolean check = false;
		check = this.ConnectedUsers.contains(pseudo);
		return check;
	}

	//  On doit cr�er un thread qui sera en �coute permanenet pour mettre � jour 
	//  la list des utilisateurs 
	@SuppressWarnings("resource")
	public void updateListUser(DatagramSocket socket,String userPseudo) throws IOException {
//		int portListener = 2020;
//		DatagramSocket socket ;
//		socket = new DatagramSocket(null);
//		socket.bind(new InetSocketAddress(portListener));
//		System.out.printf("�coute sur la socket :"+socket.getLocalPort()+" \n");
		
		int BUFFER_SIZE = 300;

		System.out.printf("Waiting for updates\n");
		DatagramPacket received_Packet = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
		socket.receive(received_Packet);
		String rcv_msg = new String(received_Packet.getData(), 0, received_Packet.getLength());
		System.out.printf("message received : "+rcv_msg+"\n");
		String token = rcv_msg.substring(0,4);
		
		if (socket.getInetAddress() != socket.getLocalAddress()) {
			if(token.equals("Conn")) {
				//User Connected Add to user List
				this.ConnectedUsers.add(rcv_msg.substring(10));
			}else if(token.equals("Disc")) {
				//User Disconnected Remove from UserList
				this.ConnectedUsers.remove(rcv_msg.substring(13));
			}else if(token.equals("Who ")) {
				//User Used a broadcast have to respond
				byte[] msg_buff = ("I am "+userPseudo).getBytes();
				InetAddress target_Address = received_Packet.getAddress();
				int target_Port = received_Packet.getPort();
				System.out.println("Send : I am "+userPseudo+"\n");
				DatagramPacket response = new DatagramPacket(msg_buff,msg_buff.length,target_Address,target_Port);
				socket.send(response);
				
			}else if(token.equals("I am")) {
				this.ConnectedUsers.add(rcv_msg.substring(5));
			}
		}
	}
}
	  