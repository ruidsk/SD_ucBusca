package ucBuscas;

import java.io.*;
import java.util.*;
import java.net.*;

public class Multicast_Server {

    final static String INET_ADDR = "224.3.2.1";
    final static int PORT = 4321;
    private static MulticastSocket socket = null;





    static String multicastName = "Servidor Multicast";
    private static final String TERMINATE = "Exit";
    static volatile boolean finished = false;



    public static HashMap<String,String> loadLoginData() throws IOException, ClassNotFoundException {
        HashMap<String, String> tmp = null;
        try{
            File toRead=new File("fileone");
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            tmp=(HashMap<String,String>)ois.readObject();

            ois.close();
            fis.close();
            //print All
            /*
            for(Map.Entry<String,String> m :tmp.entrySet()){
                System.out.println(m.getKey()+"<-user---aqui esta no file---pass ->"+m.getValue());
            }*/
        }catch(Exception e){}


        return tmp;
    }
    public static HashMap<String, Boolean> admin_load(){
        HashMap<String, Boolean> tmp = null;
        try{
            File toRead=new File("admins");
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            tmp=(HashMap<String,Boolean>)ois.readObject();

            ois.close();
            fis.close();
            //print All
        }catch(Exception e){}

        return tmp;
    }

    public static String login_user(HashMap<String,String> login){
        String feedback = null;

        try{
            HashMap<String, String> check = loadLoginData();

            if(check.get(login.get("nome")).equals(login.get("password"))){
                //sucesso a dar login
                feedback = "type | logged ; resultado | success ;";
                return feedback;
            }

        }catch(Exception e){

        }
        feedback = "type | logged ; resultado | fail";

        return feedback;
    }

    public static String give_admin(HashMap<String,String> tmp) throws IOException, ClassNotFoundException {


        HashMap<String,Boolean> aux= admin_load();

        String feedback = null;

        if(aux.get(tmp.get("nome")).equals(true)){
            feedback = "type | give ; resultado | already ;";
        }else if(aux.get(tmp.get("nome")).equals(false)){
            aux.put(tmp.get("nome"),true);
            feedback = "type | give ; resultado | success ;";
        }else
            feedback = "type | give ; resultado | fail ;";

        File fileOne=new File("admins");
        FileOutputStream fos=new FileOutputStream(fileOne);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(aux);
        oos.flush();
        oos.close();
        fos.close();


        return feedback;
    }

    public static String check_admin(HashMap<String , String> tmp){
        HashMap<String,Boolean> aux= admin_load();

        String feedback = null;

        if(aux.get(tmp.get("nome")).equals(true)){
            feedback = "type | check ; resultado | success ;";
        }else if(aux.get(tmp.get("nome")).equals(false)){
            aux.put(tmp.get("nome"),true);
            feedback = "type | check ; resultado | fail ;";
        }else
            feedback = "type | check ; resultado | notfound ;";


        return feedback;
    }


    public static String send_notification(String user){
        String feedback = null;

        feedback = "type | admin_notification ; admin | " + user;

        return feedback;
    }

    public static String register_user(HashMap<String,String> login) throws IOException, ClassNotFoundException {

        String feedback = null;
        HashMap<String,String> aux = new HashMap<>();
        HashMap<String,Boolean> admin_hash = new HashMap<>();
        boolean flag  = false;
        try{
            aux.putAll(loadLoginData());
            admin_hash.putAll(admin_load());
        }catch(NullPointerException e){
            flag=true;
        }
        admin_hash.put(login.get("nome"),flag);
        aux.put(login.get("nome"),login.get("password"));

        try{
            File fileOne=new File("fileone");
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(aux);
            oos.flush();
            oos.close();
            fos.close();

            fileOne=new File("admins");
            fos=new FileOutputStream(fileOne);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(admin_hash);
            oos.flush();
            oos.close();
            fos.close();
            if(flag){
                feedback = "type | registar ; resultado | success ; admin | true";
            }else{
                feedback = "type | registar ; resultado | success ; admin | false";

            }
        }catch(Exception e){

            feedback = "fail";
        }

        return feedback;
    }

    public static void sendFeedback (MulticastSocket socket, InetAddress group, String feedback) {
        String message = multicastName + ": " + feedback;
        System.out.println("\tEnviar -> " + message);
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new
                    DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(datagram);
        } catch (IOException e) {
            System.out.println("Excepcao (IO): " + e);
        }
    }
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


    public Multicast_Server(){
        super();
    }

    public static String addUrl(HashMap<String, String> tmp) {

        String feedback = null;

        if(WebCrawler.indexaRecursiva(tmp.get("nome")))
            feedback = "type | addUrl ; resultado | success ;";
        else
            feedback = "type | addUrl ; resultado | error ;";
        return feedback;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Multicast_Server server_multicast = new Multicast_Server();

       // HashMap<String, String> login_data = loadLoginData();

        //server_multicast.run();
        try {
            InetAddress group = InetAddress.getByName(INET_ADDR);
            socket = new MulticastSocket(PORT);
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
    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private static final int MAX_LEN = 1000;

    WaitPackets(MulticastSocket socket,InetAddress group,int port)
    {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run()
    {

        while(!Multicast_Server.finished)
        {
            byte[] buffer = new byte[WaitPackets.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
            String message;
            try
            {

                socket.receive(datagram);


                message = new String(buffer,0,datagram.getLength(),"UTF-8");

                if(!message.startsWith(Multicast_Server.multicastName)) {
                    System.out.println("\t" + message);

                    String delimeter = ": ";
                    String[] tmpPacket = message.split(delimeter);
                    HashMap<String, String> hashPacket = Multicast_Server.strToHash(tmpPacket[1]);
                    System.out.println(hashPacket.get("type"));

                    if (hashPacket.get("type").equals("registar")) {
                        Multicast_Server.sendFeedback(socket,group,Multicast_Server.register_user(hashPacket));

                    }else if(hashPacket.get("type").equals("login")){
                        Multicast_Server.sendFeedback(socket,group,Multicast_Server.login_user(hashPacket));

                    }else if(hashPacket.get("type").equals("admin_give")){
                        Multicast_Server.sendFeedback(socket,group,Multicast_Server.give_admin(hashPacket));

                    }else if(hashPacket.get("type").equals("admin_check")){
                        Multicast_Server.sendFeedback(socket,group,Multicast_Server.check_admin(hashPacket));

                    }else if(hashPacket.get("type").equals("addUrl")){
                    Multicast_Server.sendFeedback(socket,group,Multicast_Server.addUrl(hashPacket));

                    }



                }
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("Socket closed!");
            }
        }
    }
}