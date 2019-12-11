/**
 * Raul Barbosa 2014-11-07
 */
package hey.model;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import rmiserver.RMIInterface;

public class HeyBean {
	private RMIInterface server;
	private String username; // username and password supplied by the user
	private String password;

	public HeyBean() {

		try {

			server = (RMIInterface) LocateRegistry.getRegistry("10.211.55.3").lookup("server");
		}
		catch(NotBoundException| RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}

	public ArrayList<String> getAllUsers() throws RemoteException {
		return server.getAllUsers(); // are you going to throw all exceptions?
	}
	public String getFunciona() throws RemoteException {

		System.out.println(server.funciona());
		return server.funciona();	 // are you going to throw all exceptions?
	}
	public String getRegista(String username, String password) throws RemoteException{
		return server.regista(username,password);
	}
	public String checkLogin(String username, String password) throws RemoteException{
		return server.login(username,password);
	}

	public String checkWords(String palavras) throws RemoteException{
		return server.checkWords(palavras);
	}
	/*public boolean getUserMatchesPassword() throws RemoteException {
		return server.userMatchesPassword(this.username, this.password);
	}*/
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
