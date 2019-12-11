/**
 * Raul Barbosa 2014-11-07
 */
package hey.model;

import rmiserver.ClientInterface;
import rmiserver.RMIInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;

public class HeyBean extends UnicastRemoteObject implements ClientInterface {
	private RMIInterface server;

	private String username; // username and password supplied by the user
	private String password;

	public HeyBean() throws RemoteException {
		super();

		try {

			server = (RMIInterface) LocateRegistry.getRegistry("10.211.55.3").lookup("server");
		} catch (NotBoundException | RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}

	public ArrayList<String> getAllUsers() throws RemoteException {
		return server.getAllUsers(); // are you going to throw all exceptions?
	}

	public String getFunciona() throws RemoteException {
		System.out.println("Asd" + server.funciona());
		return server.funciona(); // are you going to throw all exceptions?
	}

	public String getRegista(String username, String password) throws RemoteException {
		return server.regista(username, password);
	}

	public String checkLogin(String username, String password) throws RemoteException {
		server.subscribe(username, this);
		return server.login(username, password);
	}

	public String checkWords(String palavras) throws RemoteException {
		return server.checkWords(palavras);
	}

	/*
	 * public boolean getUserMatchesPassword() throws RemoteException { return
	 * server.userMatchesPassword(this.username, this.password); }
	 */
	public String checkAdmin(String username) throws RemoteException {
		return server.check_admin(username);
	}

	public void disconnect(String username) throws RemoteException {
		server.disconnect(username);
	}

	public HashMap<String, ClientInterface> getShowOnline() throws RemoteException {
		System.out.println(server.online_user());
		return server.online_user();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void notification(String s) throws RemoteException {

	}

}
