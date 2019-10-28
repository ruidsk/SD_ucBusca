package ucBuscas;


import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server_B extends UnicastRemoteObject implements RMIInterface {
    /**
     *
     */

    final static String INET_ADDR = "224.3.2.1";
    final static int PORT = 4321;
    private static final long serialVersionUID = 1L;
    static String name = "RMI Server";
    static HashMap<String, ClientInterface> online = new HashMap<>();


    protected Server_B() throws RemoteException {
        super();
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
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length, datagram.getAddress(), datagram.getPort());


            socket.receive(reply); //echo
            socket.receive(reply);
            sReply = new String(reply.getData(), 0, reply.getLength(), "UTF-8"); // echo


            if (!sReply.startsWith(Server.name)) {
                System.out.println("\t" + sReply);
            }
            socket.close();
            feedback = sReply;
        } catch (IOException e) {
            System.out.println("Excepcao a ler/escrever do multicast " + e);
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
            System.out.println("Exception in SERVER_B.main: " + re);
        }
    }

    public void show_online() throws RemoteException {

        System.out.println(online.keySet());
    }

    @Override
    public String showOnline2Admin() throws RemoteException {
        return null;
    }

    @Override
    public String tabelaLigacoes() throws RemoteException {
        return null;
    }

    @Override
    public String load() throws RemoteException {
        return null;
    }

    @Override
    public String atualizaConsultas(String username, String text) {
        return null;
    }

    @Override
    public String mostraConsultas(String username) {
        return null;
    }

    @Override
    public String ligacoesALinks(String text) throws RemoteException {
        return null;
    }

    @Override
    public String tabelaPalavras() throws RemoteException {
        return null;
    }

    @Override
    public String loadUser(String username) throws RemoteException {
        return null;
    }

    public void load_online() throws RemoteException {
        HashMap<String, ClientInterface> tmp = new HashMap<>();
        try {
            File toRead = new File("online");
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            tmp = (HashMap<String, ClientInterface>) ois.readObject();

            ois.close();
            fis.close();
            //print All
            /*
            for(Map.Entry<String,String> m :tmp.entrySet()){
                System.out.println(m.getKey()+"<-user---aqui esta no file---pass ->"+m.getValue());
            }*/
        } catch (Exception e) {
            online = null;
        }


        online = tmp;
    }

    public void subscribe(String username, ClientInterface c) throws RemoteException {

        online.put(username, c);
        save_online();

        // aqui gravar en ficheiro para os rmi acederem quando cair um

    }

    public void disconnect(String username) throws RemoteException {
        online.remove(username);
        save_online();
    }

    private void save_online() {
        try {
            File on = new File("online");
            FileOutputStream fos = new FileOutputStream(on);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(online);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Erro nos user online");
        }
    }

    public String regista(String username, String password) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | registar ; nome | " + username + " ; password | " + password;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    public String login(String username, String password) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | login ; nome | " + username + " ; password | " + password;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    public String give_admin(String username) throws RemoteException {
        String feedback = null;

        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | admin_give ; nome | " + username;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        System.out.println(feedback);
        if (feedback.contains("success")) {
            for (Map.Entry<String, ClientInterface> x : online.entrySet()) {
                x.getValue().notification("\n\tSERVER NOTIFICATION:\n\t" + username + " é o novo admin\n");
            }
        }

        return feedback;
    }

    public String check_admin(String username) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | admin_check ; nome | " + username;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;


    }

    public String addUrl(String url) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | addUrl ; nome | " + url;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;


    }

    public String checkWords(String text) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | checkWords ; palavras | " + text;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

}