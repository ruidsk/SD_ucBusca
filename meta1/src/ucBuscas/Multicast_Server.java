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
            //print All data in MAP
            for(Map.Entry<String,String> m :tmp.entrySet()){
                System.out.println(m.getKey()+"<-user---aqui esta no file---pass ->"+m.getValue());
            }
        }catch(Exception e){}


        return tmp;
    }



    public static String register_user(HashMap<String,String> login){

        String feedback = "type | registo ; resultado | sucess";
        try{
            File fileOne=new File("fileone");
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(login);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){


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



    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Multicast_Server server_multicast = new Multicast_Server();

        HashMap<String, String> login_data = loadLoginData();

        //server_multicast.run();
        try {
            InetAddress group = InetAddress.getByName(INET_ADDR);
            socket = new MulticastSocket(PORT);

            socket.setTimeToLive(1); //Para subnet definir a 1
            socket.joinGroup(group);

            Thread tread = new Thread(new
                    WaitPackets(socket, group, PORT));

            // Spawn a thread for reading messages
            tread.start();

            // sent to the current group
            System.out.println("\t-> Multicast Server: ON\n");

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
                    }



                }
            } catch(IOException e) {
                System.out.println("Socket closed!");
            }
        }
    }
}