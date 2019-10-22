package ucBuscas;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server extends UnicastRemoteObject implements RMIInterface {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }



    // =========================================================
    // =========================================================
    public static void main(String args[]) {

        try {
            Server h = new Server();
            Registry r = LocateRegistry.createRegistry(7000);
            r.rebind("ucBusca", h);
            System.out.println("ucBusca Server A ready.");
        } catch (RemoteException re) {
            System.out.println("Exception in SERVER_A.main: " + re);
        }
    }

}