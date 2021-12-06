package view;
import control.*;
import model.*;

import java.io.IOException;
import java.util.*;


public class MainWindow {
	
	public void Disconnect() {
		
	}
	
	private boolean CheckPseudoUnicity(String pseudo) throws IOException {
		//on doit r�cup�rer les pseudos de tous les users connect�s
		//puis v�rifier l'appartenance du pseudo � cette liste
		boolean unique;
		UDP udp = new UDP(2525);
		userList allConnected = udp.getAllConnected();
		if (allConnected.contains(pseudo)) {
			unique = false;
		} else unique=true;
		return unique;
	}
	
	private void ChangePseudo(String NewPseudo) throws IOException{
		boolean unique;
		do{
			unique = CheckPseudoUnicity(NewPseudo);
		}while(!unique);
	}
	
	public void SendMsgTo(String msg, String receiver) {
		
	}
	
	public String RecvMsgFrom(String msg, String sender) {
		return "";
	}
}
