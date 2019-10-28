package ucBuscas;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public String regista(String username, String password) throws RemoteException;

    String login(String username, String password) throws RemoteException;

    String give_admin(String username) throws RemoteException;

    String check_admin(String username) throws RemoteException;

    public void subscribe(String username, ClientInterface client) throws RemoteException;

    public void disconnect(String username) throws RemoteException;

    public String addUrl(String url) throws RemoteException;

    public String checkWords(String text) throws RemoteException;

    public void load_online() throws RemoteException;

    void show_online() throws RemoteException;

    String tabelaLigacoes() throws RemoteException;

    String load() throws RemoteException;

    String atualizaConsultas(String username, String text) throws RemoteException;

    String mostraConsultas(String username) throws RemoteException;

    String ligacoesALinks(String text) throws RemoteException;
}