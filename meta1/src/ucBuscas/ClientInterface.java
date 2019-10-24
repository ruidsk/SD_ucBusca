package ucBuscas;
import java.rmi.*;
public interface ClientInterface extends Remote{
    public void notification(String s) throws RemoteException;
}
