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

			server = (RMIInterface) LocateRegistry.getRegistry().lookup("server");
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
		server.atualizaConsultas(username,palavras);
		return server.checkWords(palavras);
	}

	public String pesquisarSite(String site) throws RemoteException {
		server.atualizaConsultas(username,site);
		return server.ligacoesALinks(site);
	}

	public String addUrl(String site) throws RemoteException {
		return server.addUrl(site);
	}

	public String addUrlRec(String site) throws RemoteException{
		return server.addUrlRec(site);
	}

	public String mostraConsultas(){
		String resp ="";
		try{
			resp = server.mostraConsultas(username);

		} catch (RemoteException e){
			e.printStackTrace();
		}
		String[] tmp_split;
		tmp_split = resp.split(":", 2);
		if (resp.length() < 21) {
			System.out.println("Não existem pesquisas na base de dados");
			resp="Não existem pesquisas na base de dados";
		} else {
			System.out.println("\n\nAs pesquisas realizadas são:");
			System.out.println(tmp_split[1]);
			resp=(tmp_split[1]);
		}
		return resp;
	}

	public String tabelaPalavras() throws RemoteException {
		server.loadUser(username);
		String tmp ="";
		try{
			tmp = server.tabelaPalavras();

		} catch (RemoteException e){
			e.printStackTrace();
		}
		String[] tmp_split;
		tmp_split = tmp.split(":", 2);
		if (tmp.length() < 27) {
			System.out.println("Não existem palavras pesquisadas!");
			tmp="Não existem palavras pesquisadas!";
		} else {
			System.out.println("\n\nAs palavras mais pesquisadas são:");
			System.out.println(tmp_split[1]);
			tmp=tmp_split[1];
		}
		return tmp;
	}

	public String tabelaLigacoes() throws RemoteException{
		String tmp = server.tabelaLigacoes();
		String[] tmp_split;
		tmp_split = tmp.split(":", 2);
		if (tmp.length()<27) {

			System.out.println("\nNão existem sites na base de dados");
			tmp = "\nNão existem sites na base de dados";
		} else {
			System.out.println("\n\nOs sites com mais links são:");
			System.out.println(tmp_split[1]);
			tmp=tmp_split[1];
		}
		return tmp;
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
