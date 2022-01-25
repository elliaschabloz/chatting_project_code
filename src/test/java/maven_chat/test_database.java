package maven_chat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import model.DataBase;

public class test_database {
	
	@Test
	public final void testQueryDB() {
		Connection con = null;
		con = DataBase.initDB(con);
		List<ArrayList<String>> historique = new ArrayList<ArrayList<String>>();;
		try {
			historique = DataBase.QueryMsgFromDB(con,"tutu", "tata");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		ArrayList<String> elem = new ArrayList<String>();
		elem.add("tutu"); 
		elem.add("tata");
		elem.add(null);
		elem.add("Lorem Ipsum");
		res.add(elem);
//		System.out.println(res);
//		System.out.println(historique);
//		System.out.println(historique.equals(res));
		assertTrue("Valide",historique.equals(res));
	}
}
