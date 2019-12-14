package hey.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import com.github.scribejava.core.model.Token;

import rmiserver.ClientInterface;
import rmiserver.RMIInterface;
import webSocket.WebSocketAnnotation;

import javax.servlet.http.HttpSession;

public class FacebookBean extends UnicastRemoteObject implements ClientInterface {
	private RMIInterface server;
	
	

	
	private Token accessToken;
	private String authorizationUrl;
	private String username;
	private String facebook_id;
	private String userid;

	private WebSocketAnnotation websocket;
	private HttpSession browserSession;


	public FacebookBean(WebSocketAnnotation web, HttpSession session) throws RemoteException {
		super();
		this.browserSession = session;
		this.websocket = web;

		System.setProperty("java.net.preferIPv4Stack" , "true");


		try {

			server = (RMIInterface) LocateRegistry.getRegistry("10.211.55.3").lookup("server");
			//"10.211.55.3"
		} catch (NotBoundException | RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}
	

	public String loginFace(String username) throws RemoteException
	{
		server.subscribe(username, (ClientInterface) this);

		return server.login(username,"facebook");

	}
	public String checkAdmin(String username) throws RemoteException {
		return server.check_admin(username);
	}
	public String registoFace(String username) throws RemoteException {
		return server.regista(username,"facebook");
	}
	
	/*public HashMap<String,String> getFacebookCredentials(String userid) throws RemoteException
	{
		return server.getFacebookLoginInformation(userid);
	}
	
	public boolean removeFacebookInformation(String userid) throws RemoteException
	{
		return server.removeFacebookInformation(userid);
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public Token getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	public String getAuthorizationUrl() {
		return authorizationUrl;
	}

	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	@Override
	public void notification(String s) throws RemoteException {

		System.out.println("sou facebook"+s);
		//this.websocket.sendMessage(s+this.browserSession.getAttribute("username").toString());
	}
}
	