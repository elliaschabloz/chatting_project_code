package src.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataBase {

	
	public static void main(String[] args) {
		Connection con = null;
		initDB(con);
		ArrayList<List<String>> historique = null;
		try {
			historique = QueryMsgFromDB(con,"toto", "titi");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("historique = " + historique);
	}
	
	public static void initDB(Connection con) {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_015",
					"tp_servlet_015","ThooWib1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMsgToDB(Connection con,String emitter, String receiver, String msg) {
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(
					"INSERT INTO messages (emitter,receiver,msg,date) VALUES ("
					+ "\""+ emitter + ","+"\""+receiver+ ","+"\""+msg+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<List<String>> QueryMsgFromDB(Connection con, String user1, String user2) {
		
		ArrayList<List<String>> historique = new ArrayList<List<String>>();
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM MESSAGE WHERE (emitter='"+user1+"' AND receiver='"+user2+"') OR"
					+ " (emitter='"+user2+"' AND receiver='"+user1+"')");
			while (rs.next()) {
				List<String> elem = Arrays.asList(rs.getString("emitter"),
				rs.getString("receiver"),"null",rs.getString("message"));
				historique.add(elem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return historique;
	}
}
