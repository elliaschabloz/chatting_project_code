package src.model;

public class User {
	public String pseudo;
	private String hostname;
	
	public User(String pseudo, String hostname) {
		this.pseudo = pseudo;
		this.hostname = hostname;
	}
	
	public String getHostname() {
		return this.hostname;
	}

}
