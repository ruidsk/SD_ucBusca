package ucBuscas;


import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements RMIInterface {
    /**
     *
     */

    final static String INET_ADDR = "224.3.2.1";
    final static int PORT = 4321;
    static String name = "RMI Server";

    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }
    public static String toMulticast(String tmpPacket /*HashMap<String, String> tmpHash*/) throws RemoteException {
        int MAX_LEN = 1000;
        MulticastSocket socket = null;
        InetAddress group = null;

        try {

            group = InetAddress.getByName(INET_ADDR);
            socket = new MulticastSocket(PORT);
            socket.setTimeToLive(1);
            socket.joinGroup(group);

        } catch (IOException e) {
            System.out.println("IOException em registarPessoas");
        }

        String feedback = null;/*
        byte[] buf = tmpHash.toString().getBytes();

        String tmpPacket = tmpHash.toString();*/

        // Envia pacote
        try {


            String sReply = null;
            String message = name + ": " + tmpPacket;
            byte[] m = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(m, m.length, group, PORT);
            //System.out.println(datagram);
            socket.send(datagram);


            // Espera por resposta

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new
                    DatagramPacket(buffer, buffer.length, datagram.getAddress(), datagram.getPort());


            socket.receive(reply); //echo
            socket.receive(reply);
            sReply = new
                    String(reply.getData(), 0, reply.getLength(), "UTF-8"); // echo

            //sReply = sReply.split(":")[1];
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
            Registry r = LocateRegistry.createRegistry(7000);
            r.rebind("ucBusca", h);
            System.out.println("ucBusca Server A ready.\n");
            String resposta = toMulticast("Servidor Multicast é uma grande network");
            System.out.println(resposta+"\n");
        } catch (RemoteException re) {
            System.out.println("Exception in SERVER_A.main: " + re);
        }
    }

}