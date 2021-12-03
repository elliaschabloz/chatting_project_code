package model;
import java.util.Date;
//import java.text.SimpleDateFormat;

//import com.modeliosoft.modelio.javadesigner.annotations.objid;

//@objid ("aceb6da4-6a28-4d8c-bfe8-df0fea0b587c")
public class Clock {
	public Date Date;
	
	//@objid ("f1ac0a3a-cc59-49d5-b5d8-86dc6023d3cf")
	//public Classic GUI  classic GUI ;
	
	//@objid ("a02f8996-f963-4b3e-8931-2e35d4624cbe")
	public Date getDate() {
		  
		  // Pour obtenir la date en String on utilise la ligne qui suivante
		  
		  //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		  Date date = new Date();
		 
		  // date = formatter.format(date)  
		  return date;
	}
}