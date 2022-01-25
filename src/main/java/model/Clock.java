package model;
import java.util.Date;
//import java.text.SimpleDateFormat;


public class Clock {
	public Date Date;

	public Date getDate() {
		  
		  // Pour obtenir la date en String on utilise la ligne qui suivante
		  
		  //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		  Date date = new Date();
		 
		  // date = formatter.format(date)  
		  return date;
	}
}