package model;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;


public class ListUser {

	public userList ConnectedUsers;
	
	public ListUser(userList list) {
		this.ConnectedUsers = list;
	}

	public boolean isConnected(String pseudo) {
		boolean check = false;
		return check;
	}

	//  On doit cr�er un thread qui sera en �coute permanenet pour mettre � jour 
	//  la list des utilisateurs 
	  public void updateListUser() {
	  }
	  
}