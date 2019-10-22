package ucBuscas;

import java.rmi.*;

public interface RMIInterface extends Remote {
    public  String regista(String username, String password) throws RemoteException;

}