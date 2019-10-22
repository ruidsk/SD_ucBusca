package ucBuscas;


import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class Server_B extends UnicastRemoteObject implements RMIInterface {
    /**
     *
     */

    final static String INET_ADDR = "224.3.2.1";
    final static int PORT = 4321;
    static String name = "RMI Server";

    private static final long serialVersionUID = 1L;

    public Server_B() throws RemoteException {
        super();
    }

    public String regista(String username, String password) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | registar ; nome | " + username + " ; password | " + password;

        tmpInput = split(protocolo);

        feedback = toMulticast(tmpInput);

        return feedback;
    }
    public static HashMap<String, String> split(String protocolo) {
        HashMap<String, String> tmpHash = new HashMap<>();
        Arrays.stream(protocolo.split(";")).map(s -> s.split("\\|")).forEach(i -> tmpHash.put(i[0].trim(), i[1].trim()));
        return tmpHash;
    }

    public static String toMulticast(HashMap<String, String> tmpHash) throws RemoteException {
        int MAX_LEN = 1000;
        MulticastSocket socket = null;
        InetAddress group = null;

        try {

            group = InetAddress.getByName(INET_ADDR);
            socket = new MulticastSocket(PORT);
            socket.setTimeToLive(1);
            socket.joinGroup(group);

        } catch (IOException e) {
            System.out.println("IOException " + e);
        }

        String feedback = null;
        byte[] buf = tmpHash.toString().getBytes();

        String tmpPacket = tmpHash.toString();

        // Envia pacote
        try {


            String sReply = null;
            String message = name + ": " + tmpPacket;
            byte[] m = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(m, m.length, group, PORT);

            socket.send(datagram);


            //Espera por resposta

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new
                    DatagramPacket(buffer, buffer.length, datagram.getAddress(), datagram.getPort());


            socket.receive(reply); //echo
            socket.receive(reply);
            sReply = new
                    String(reply.getData(), 0, reply.getLength(), "UTF-8"); // echo


            if (!sReply.startsWith(Server.name)) {
                System.out.println("\t" + sReply);
            }

            feedback = sReply;
        } catch (IOException e) {
            System.out.println("Excepcao a ler/escrever do multicast (IO): " + e);
        }
        return feedback;
    }


    // =========================================================
    // =========================================================
    public static void main(String args[]) {

        try {
            Server h = new Server();
            Registry r = LocateRegistry.createRegistry(7001);
            r.rebind("ucBusca", h);
            System.out.println("ucBusca Server B ready.\n");


        } catch (RemoteException re) {
            System.out.println("Exception in SERVER_A.main: " + re);
        }
    }

}