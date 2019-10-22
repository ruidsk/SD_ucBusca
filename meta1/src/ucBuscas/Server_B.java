package ucBuscas;


import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server_B extends UnicastRemoteObject implements RMIInterface {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Server_B() throws RemoteException {
        super();
    }



    // =========================================================
    public static void main(String args[]) {

        try {
            Server h = new Server();
            Registry r = LocateRegistry.createRegistry(7001);
            r.rebind("ucBusca", h);
            System.out.println("ucBusca Server B ready.");
        } catch (RemoteException re) {
            System.out.println("Exception in SERVER_B.main: " + re);
            System.out.println("Exception in SERVER_B.main: " + re);
        }
    }

}