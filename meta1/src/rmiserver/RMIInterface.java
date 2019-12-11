package rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIInterface extends Remote {
    public String regista(String username, String password) throws RemoteException;

    public String funciona() throws RemoteException;

    String login(String username, String password) throws RemoteException;

    public ArrayList<String> getAllUsers() throws RemoteException;

    String give_admin(String username) throws RemoteException;

    String check_admin(String username) throws RemoteException;

    public void subscribe(String username, ClientInterface client) throws RemoteException;

    public void disconnect(String username) throws RemoteException;

    public String addUrl(String url) throws RemoteException;

    public String checkWords(String text) throws RemoteException;

    public void load_online() throws RemoteException;

    void show_online() throws RemoteException;

    public HashMap<String, ClientInterface> online_user() throws RemoteException;

    String showOnline2Admin() throws RemoteException;

    String tabelaLigacoes() throws RemoteException;

    String load() throws RemoteException;

    String atualizaConsultas(String username, String text) throws RemoteException;

    String mostraConsultas(String username) throws RemoteException;

    String ligacoesALinks(String text) throws RemoteException;

    String tabelaPalavras() throws RemoteException;

    String loadUser(String username) throws RemoteException;

    String addUrlRec(String url) throws RemoteException;
}