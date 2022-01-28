package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

//
public class DataBase {
	
	static SimpleDateFormat formater = new SimpleDateFormat("h:mm a");

	
	public static Connection initDB(Connection con) {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_015",
					"tp_servlet_015","ThooWib1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void addMsgToDB(Connection con,String emitter, String receiver, String msg) {
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(
					"INSERT INTO MESSAGE (emitter,receiver,message) VALUES ("
					+ "'"+ emitter + "',"+"'"+receiver+"'"+formater.format(new Date())+ "'," +"'"+msg+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<ArrayList<String>> QueryMsgFromDB(Connection con, String user1, String user2) {
		
		List<ArrayList<String>> historique = new ArrayList<ArrayList<String>>();
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM MESSAGE WHERE (emitter='"+user1+"' AND receiver='"+user2+"') OR"
					+ " (emitter='"+user2+"' AND receiver='"+user1+"')");
			while (rs.next()) {				
				ArrayList<String> elem = new ArrayList<String>();
				elem.add(rs.getString("emitter")); 
				elem.add(rs.getString("receiver"));
				elem.add(rs.getString("date"));
				elem.add(rs.getString("message"));
				historique.add(elem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historique;
	}
}
