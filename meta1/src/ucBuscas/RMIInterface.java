package ucBuscas;

import java.rmi.*;

public interface RMIInterface extends Remote {
    public  String regista(String username, String password) throws RemoteException;
    String login(String username, String password) throws RemoteException;
    String give_admin(String username) throws RemoteException;

    String check_admin(String username) throws RemoteException;

    public void subscribe(String username, ClientInterface client) throws RemoteException;

    public void disconnect(String username) throws RemoteException ;

    public String addUrl(String url) throws RemoteException;
}