package model;

import java.net.Socket;

import javax.swing.table.DefaultTableModel;

public class User {
	public String pseudo;
	private String hostname;
	public DefaultTableModel tableUser;
	public Socket socketUser;
	
	public User(String pseudo, String hostname) {
		this.pseudo = pseudo;
		this.hostname = hostname;
		this.tableUser = null;
		this.socketUser = null;
	}
	
	public String getHostname() {
		return this.hostname;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void modifyPseudo(String newPseudo) {
		this.pseudo = newPseudo;
	}

}
