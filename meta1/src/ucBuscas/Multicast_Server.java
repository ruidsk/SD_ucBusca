package ucBuscas;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Multicast_Server {

    final static String INET_ADDR = "224.3.2.1";
    final static int PORT = 4321;
    private static final String TERMINATE = "Exit";
    static String multicastName = "Servidor Multicast";
    static volatile boolean finished = false;
    private static MulticastSocket socket = null;


    /**
     * construtor
     */
    public Multicast_Server() {
        super();
    }

    /**
     * faz o load dos ficheiros
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static HashMap<String, String> loadLoginData() throws IOException, ClassNotFoundException {
        HashMap<String, String> tmp = null;
        try {
            File toRead = new File("fileone");
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            tmp = (HashMap<String, String>) ois.readObject();

            ois.close();
            fis.close();


        } catch (Exception e) {
        }


        return tmp;
    }

    /**
     * enviar todos os useres registados ao servidor rmi
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String note_help() throws IOException, ClassNotFoundException {
        HashMap<String, String> tmp = loadLoginData();
        String aux = "";

        for(String m :tmp.keySet()) {
            aux+=m+";";

        }

        return aux;
    }

    /**
     * carrega os administradores
     * @return
     */
    public static HashMap<String, Boolean> admin_load() {
        HashMap<String, Boolean> tmp = null;
        try {
            File toRead = new File("admins");
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            tmp = (HashMap<String, Boolean>) ois.readObject();

            ois.close();
            fis.close();
            //print All
        } catch (Exception e) {
        }

        return tmp;
    }

    /**
     * verifica se o user e password estão corretos e permite ou não o login
     * @param login
     * @return
     */
    public static String login_user(HashMap<String, String> login) {
        String feedback = null;

        try {
            HashMap<String, String> check = loadLoginData();

            if (check.get(login.get("nome")).equals(login.get("password"))) {
                //sucesso a dar login
                feedback = "type | logged ; resultado | success ;";
                return feedback;
            }

        } catch (Exception e) {

        }
        feedback = "type | logged ; resultado | fail";

        return feedback;
    }

    /**
     * da permissão de administrador a um utilizador
     * @param tmp
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String give_admin(HashMap<String, String> tmp) throws IOException, ClassNotFoundException {

        String feedback = null;
        try{
            HashMap<String, Boolean> aux = admin_load();


            if (aux.get(tmp.get("nome")).equals(true)) {
                feedback = "type | give ; resultado | already ;";
            } else if (aux.containsKey(tmp.get("nome")) && aux.get(tmp.get("nome")).equals(false)) {
                aux.put(tmp.get("nome"), true);
                feedback = "type | give ; resultado | success ;";
            } else
                feedback = "type | give ; resultado | fail ;";

            File fileOne = new File("admins");
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(aux);
            oos.flush();
            oos.close();
            fos.close();

        }catch(NullPointerException e){
            feedback = "type | give ; resultado | fail ;";
        }


        return feedback;
    }

    /**
     * verifica se o utilizdor é administrador
     * @param tmp
     * @return
     */
    public static String check_admin(HashMap<String, String> tmp) {
        HashMap<String, Boolean> aux = admin_load();

        String feedback = null;

        if (aux.get(tmp.get("nome")).equals(true)) {
            feedback = "type | check ; resultado | success ;";
        } else if (aux.get(tmp.get("nome")).equals(false)) {
            aux.put(tmp.get("nome"), true);
            feedback = "type | check ; resultado | fail ;";
        } else
            feedback = "type | check ; resultado | notfound ;";


        return feedback;
    }

    /**
     * envia uma notificação
     * @param user
     * @return
     */
    public static String send_notification(String user) {
        String feedback = null;

        feedback = "type | admin_notification ; admin | " + user;

        return feedback;
    }

    /**
     * regista um novo utilizador
     * @param login
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String register_user(HashMap<String, String> login) throws IOException, ClassNotFoundException {

        String feedback = null;
        HashMap<String, String> aux = new HashMap<>();
        HashMap<String, Boolean> admin_hash = new HashMap<>();
        boolean flag = false;
        try {
            aux.putAll(loadLoginData());
            admin_hash.putAll(admin_load());
        } catch (NullPointerException e) {
            flag = true;
        }
        admin_hash.put(login.get("nome"), flag);
        aux.put(login.get("nome"), login.get("password"));

        try {
            File fileOne = new File("fileone");
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(aux);
            oos.flush();
            oos.close();
            fos.close();

            fileOne = new File("admins");
            fos = new FileOutputStream(fileOne);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(admin_hash);
            oos.flush();
            oos.close();
            fos.close();
            if (flag) {
                feedback = "type | registar ; resultado | success ; admin | true";
            } else {
                feedback = "type | registar ; resultado | success ; admin | false";

            }
        } catch (Exception e) {

            feedback = "fail";
        }

        return feedback;
    }

    /**
     *
     * @param socket
     * @param group
     * @param feedback
     */
    public static void sendFeedback(MulticastSocket socket, InetAddress group, String feedback) {
        String message = multicastName + ": " + feedback;
        System.out.println("\tEnviar -> " + message);
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(datagram);
        } catch (IOException e) {
            System.out.println("Excepcao (IO): " + e);
        }
    }

    /**
     * @param str
     * @return
     */
    public static HashMap<String, String> strToHash(String str) {
        Properties props = new Properties();
        try {
            props.load(new StringReader(str.substring(1, str.length() - 1).replace(", ", "\n")));
            HashMap<String, String> tmpHash = new HashMap<String, String>();
            for (Map.Entry<Object, Object> e : props.entrySet()) {
                tmpHash.put((String) e.getKey(), (String) e.getValue());
            }

            return tmpHash;
        } catch (IOException e) {
            System.out.println("String to Hash failed: " + e);
        }

        return null;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @param tmp
     * @return
     */
    public static String addUrl(HashMap<String, String> tmp) {

        String feedback = null;

        if (WebCrawler.main(tmp.get("nome")))
            feedback = "type | addUrl ; resultado | success ;";
        else
            feedback = "type | addUrl ; resultado | error ;";
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     *  o resultado enviado da funçao chamada em WebCrawler
     * @param hashPacket
     * @return
     */
    public static String addUrlRec(HashMap<String, String> hashPacket) {

        String feedback = null;

        String tmp = WebCrawler.indexaRecursiva(hashPacket.get("url"));

        feedback = tmp;
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @param hashPacket
     * @return
     */
    public static String checkWords(HashMap<String, String> hashPacket) {
        String feedback = null;

        String tmp = WebCrawler.checkWords(hashPacket.get("palavras"));

        feedback = tmp;
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @return
     */
    public static String tabelaLigacoes() {
        String feedback = null;

        String tmp = WebCrawler.tabelaLigacoes();

        feedback = tmp;
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @return
     * @throws IOException
     */
    public static String load() throws IOException {
        String feedback = null;

        String tmp = WebCrawler.load();

        feedback = tmp;
        return feedback;
    }


    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @param hashPacket
     * @return
     * @throws IOException
     */
    public static String atualizaConsultas(HashMap<String, String> hashPacket) throws IOException {
        String feedback = null;


        if (WebCrawler.atualizaConsultas(hashPacket.get("nome"),hashPacket.get("consulta")))
            feedback = "success";
        else
            feedback = "error";
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @param hashPacket
     * @return
     * @throws IOException
     */
    public static String mostraConsultas(HashMap<String, String> hashPacket) throws IOException {
        String feedback = null;

        String tmp = WebCrawler.mostraConsultas(hashPacket.get("user"));

        feedback = tmp;
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @param hashPacket
     * @return
     * @throws IOException
     */
    public static String ligacoesALinks(HashMap<String, String> hashPacket) throws IOException {
        String feedback = null;

        String tmp = WebCrawler.ligacoesALinks(hashPacket.get("ws"));

        feedback = tmp;
        return feedback;
    }

    /**
     * função que depois de recebido o protocolo, envia um um feedback em conformidade com
     * o resultado enviado da funçao chamada em WebCrawler
     * @return
     */
    public static String tabelaPalavras() {
        String feedback = null;

        String tmp = WebCrawler.tabelaPalavras();

        feedback = tmp;
        return feedback;
    }


    /**
     * inicia a thread que fica à escuta
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Multicast_Server server_multicast = new Multicast_Server();

        // HashMap<String, String> login_data = loadLoginData();
        System.out.println(InetAddress.getLocalHost());
        System.out.println("\nA carregar dados...\n\n\n");
        server_multicast.load();
        //server_multicast.run();
        try {
            InetAddress group = InetAddress.getByName(INET_ADDR);
            socket = new MulticastSocket(PORT);
            System.out.println(InetAddress.getLocalHost());
            socket.joinGroup(group);
            socket.setTimeToLive(1);


            Thread t = new Thread(new WaitPackets(socket, group, PORT));

            // Spawn a thread for reading messages
            t.start();

            // sent to the current group
            System.out.println("\n\t Multicast Server: ON\n\n");

        } catch (IOException e) {
            System.out.println("Excepcao (IO): " + e);
        }
    }



}

/* thread que responde às mensagens do RMI_SEVER*/

class WaitPackets implements Runnable {
    private static final int MAX_LEN = 100000;
    private MulticastSocket socket;
    private InetAddress group;
    private int port;

    WaitPackets(MulticastSocket socket, InetAddress group, int port) throws IOException {
        this.socket = socket;

        this.group = group;
        this.port = port;
    }


    /**
     * thread que espera mensagens do multicast group
     */
    @Override
    public void run() {

        while (!Multicast_Server.finished) {
            byte[] buffer = new byte[WaitPackets.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            String message;
            try {

                socket.receive(datagram);

                message = new String(buffer, 0, datagram.getLength(), "UTF-8");

                if (!message.startsWith(Multicast_Server.multicastName)) {
                    System.out.println("\t" + message);

                    String delimeter = ": ";
                    String[] tmpPacket = message.split(delimeter);
                    HashMap<String, String> hashPacket = Multicast_Server.strToHash(tmpPacket[1]);
                    //System.out.println(hashPacket.get("type"));

                    if (hashPacket.get("type").equals("registar")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.register_user(hashPacket));

                    } else if (hashPacket.get("type").equals("login")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.login_user(hashPacket));

                    } else if (hashPacket.get("type").equals("admin_give")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.give_admin(hashPacket));

                    } else if (hashPacket.get("type").equals("admin_check")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.check_admin(hashPacket));

                    } else if (hashPacket.get("type").equals("checkWords")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.checkWords(hashPacket));

                    } else if (hashPacket.get("type").equals("addUrl")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.addUrl(hashPacket));
                    }else if (hashPacket.get("type").equals("help_note")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.note_help());

                    }else if (hashPacket.get("type").equals("tabelaLigacoes")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.tabelaLigacoes());
                    }else if (hashPacket.get("type").equals("load")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.load());
                    }else if (hashPacket.get("type").equals("atualizaConsultas")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.atualizaConsultas(hashPacket));
                    }else if (hashPacket.get("type").equals("mostraConsultas")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.mostraConsultas(hashPacket));
                    }else if (hashPacket.get("type").equals("ligacoesALinks")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.ligacoesALinks(hashPacket));
                    }else if (hashPacket.get("type").equals("tabelaPalavras")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.tabelaPalavras());
                    }else if (hashPacket.get("type").equals("addUrlRec")) {
                        Multicast_Server.sendFeedback(socket, group, Multicast_Server.addUrlRec(hashPacket));
                    }



                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Socket closed!");

            }
        }
    }
}
