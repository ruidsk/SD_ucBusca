package ucBuscas;


import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
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
    static HashMap<String,ClientInterface> online = new HashMap<>();
    private static final long serialVersionUID = 1L;
    HashMap<String,String> nome_nota = new HashMap<>();

    public Server_B() throws RemoteException {
        super();
    }

    /**
     * @throws RemoteException
     * hidden function que mostra os utilizadores online quando é introduzido '99' do lado do cliente
     */
    public void show_online()  throws RemoteException{

        System.out.println(online.keySet());
    }

    /**
     * @return
     * @throws RemoteException
     * funçao que permite aos administradores ver os utilizadores online
     */
    public String showOnline2Admin() throws RemoteException{
        String aux = "";

        for(String x : online.keySet()){
            aux+=x+"\n";
        }
        return aux;
    }

    /**
     * @throws RemoteException
     * permite aos servidores RMI trocarem informações sobre os membros online carregando o ficheiro
     */
    public void load_online()  throws RemoteException{
        HashMap<String, ClientInterface> tmp = new HashMap<>();
        //System.out.println("ansdnajsdn");

        try{
            File toRead=new File("online");
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            tmp=(HashMap<String,ClientInterface>)ois.readObject();

            ois.close();
            fis.close();
            //print All
            /*
            for(Map.Entry<String,String> m :tmp.entrySet()){
                System.out.println(m.getKey()+"<-user---aqui esta no file---pass ->"+m.getValue());
            }*/
        }catch(Exception e){
            online = null;
        }

        online =  tmp;
    }


    /**
     * @param username nome do utilizador a ficar online
     * @param c interface que permite enviar mensagem a esse user
     * @throws RemoteException
     * esta funçao é chamada quando um utilizador inicia sessão e passa-o para a lista de users online
     * envia notificaçoes guardadas aos users que ficaram
     */
    public void subscribe(String username, ClientInterface c) throws RemoteException{

        online.put(username,c);
        if(nome_nota.containsKey(username)){
            online.get(username).notification(nome_nota.get(username));
            nome_nota.remove(username);
        }
        save_online();

        // aqui gravar en ficheiro para os rmi acederem quando cair um
    }


    /**
     * @param username user a ficar offline
     * @throws RemoteException
     * função que remove um user da lista de users online
     */
    public void disconnect(String username) throws RemoteException{
        online.remove(username);
        save_online();
    }

    private void save_online() {
        try{
            File on=new File("online");
            FileOutputStream fos=new FileOutputStream(on);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(online);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){
            System.out.println("Erro nos user online");
        }
    }

    /**
     * @param username user a registar
     * @param password pass do user
     * @return
     * funçao que envia o protocolo de registo para o Multicast e devolve o resultado da operação
     * @throws RemoteException
     */
    public String regista(String username, String password) throws RemoteException {
        String feedback = null;
//        System.out.println("ahsdasjd");
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | registar ; nome | " + username + " ; password | " + password;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @param username
     * @param password
     * @return
     * funçao que envia o protocolo de login para o Multicast e devolve o resultado da operação
     *
     * @throws RemoteException
     */
    public String login(String username, String password) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | login ; nome | " + username + " ; password | " + password;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);

        return feedback;
    }

    /**
     * @param username user que vai passar a ser admin
     * @return
     * funçao que envia o protocolo de admin_give para o Multicast e devolve o resultado da operação
     * envia notificações a todos os online
     * coloca em lista todos os users registados que nao estão online e posteriormente quando eles derem login envia-lhe a notificação
     * @throws RemoteException
     */
    public String give_admin(String username) throws RemoteException {
        String feedback = null;
        String feedback2 = null;
        String[] spliter = null;
        String[] all_users = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | admin_give ; nome | " + username;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        System.out.println(feedback);
        if (feedback.contains("success")){
            protocolo = "type | help_note ; nome | "+ username;
            tmpInput = split(protocolo);
            feedback2 = toMulticast(tmpInput);
            spliter = feedback2.split(":");
            all_users = spliter[1].split(";");
            for(String i : all_users){
                if(!online.containsKey(i)){
                    nome_nota.put(i,"\n\tSERVER NOTIFICATION:\n\t"+username+" é o novo admin\n");
                }
            }

            for (Map.Entry<String,ClientInterface> nome : online.entrySet()) {
                nome.getValue().notification("\n\tSERVER NOTIFICATION:\n\t"+username+" é o novo admin\n");

            }



        }

        return feedback;
    }

    /**
     * @param username  nome a verificar
     * @return
     * @throws RemoteException
     * verifica se o username é administrador
     */
    public String check_admin(String username) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | admin_check ; nome | " + username;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;


    }

    /**
     * @param url
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de addUrl para o Multicast e devolve o resultado da operação
     */
    public String addUrl(String url) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | addUrl ; nome | " + url;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;


    }


    /**
     * @param text
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de checkWords para o Multicast e devolve o resultado da operação
     */
    public String checkWords(String text) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | checkWords ; palavras | " + text;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de tabelaLigacoes para o Multicast e devolve o resultado da operação
     */
    public String tabelaLigacoes() throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | tabelaLigacoes ; palavras | ";
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }


    /**
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de laod para o Multicast e devolve o resultado da operação
     */
    public String load() throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | load ; palavras | ";
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @param user
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de loadUser para o Multicast e devolve o resultado da operação
     */
    public String loadUser(String user) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | loadUser ; user | " + user;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @param user
     * @param text
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo de atualizaConsultas para o Multicast e devolve o resultado da operação
     *
     */
    public String atualizaConsultas(String user, String text) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | atualizaConsultas ; nome | " + user + " ; consulta | " + text;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);

        return feedback;
    }

    /**
     * @param user
     * @return
     * @throws RemoteException
     * funçao que envia o protocolode mostraConsultas para o Multicast e devolve o resultado da operação
     */
    public String mostraConsultas(String user) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | mostraConsultas ; user | " + user;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @param ws
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo para o Multicast e devolve o resultado da operação
     */
    public String ligacoesALinks(String ws) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | ligacoesALinks ; ws | " + ws;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo para o Multicast e devolve o resultado da operação
     */
    public String tabelaPalavras() throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | tabelaPalavras ; palavras | ";
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }

    /**
     * @param ws
     * @return
     * @throws RemoteException
     * funçao que envia o protocolo para o Multicast e devolve o resultado da operação
     */
    public String addUrlRec(String ws) throws RemoteException {
        String feedback = null;
        HashMap<String, String> tmpInput = new HashMap<>();
        String protocolo = "type | addUrlRec ; url | " + ws;
        tmpInput = split(protocolo);
        feedback = toMulticast(tmpInput);
        return feedback;
    }


    /**
     * @param protocolo
     * @return
     * função que transforma o protocolo num HashMap
     */
    public static HashMap<String, String> split(String protocolo) {
        HashMap<String, String> tmpHash = new HashMap<>();
        Arrays.stream(protocolo.split(";")).map(s -> s.split("\\|")).forEach(i -> tmpHash.put(i[0].trim(), i[1].trim()));
        return tmpHash;
    }

    /**
     * @param tmpHash
     * @return
     * @throws RemoteException
     * função que envia para o multicast da joinGroup e fica á espera de resposta
     */
    public static String toMulticast(HashMap<String, String> tmpHash) throws RemoteException {
        int MAX_LEN = 100000;
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
            System.out.println("enviei"+socket);

            //Espera por resposta

            byte[] buffer = new byte[MAX_LEN];
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
    public static void main(String args[]) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
        System.getProperties().put("java.security.policy","al.policy");
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Server h = new Server();
            Registry r = LocateRegistry.createRegistry(7001);
            r.rebind("ucBusca", h);
            System.out.println("\n\n\n\tucBusca Server A ready.\n");



        } catch (RemoteException re) {
            System.out.println("Exception in SERVER_A.main: " + re);
        }
    }

}