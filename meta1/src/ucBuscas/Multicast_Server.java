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


    public static void load_data(){


        System.out.println("asdasdasd");
    }

    public void registar_user(){

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
            System.out.println("Excepcao a converter String para Hash (IO): " + e);
        }

        return null;
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


    public Multicast_Server(){
        super();
    }



    public static void main(String[] args) throws UnknownHostException, InterruptedException {

        Multicast_Server server_multicast = new Multicast_Server();



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
        Multicast_Server.load_data();
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
                    Multicast_Server.sendFeedback(socket, group, "yes");
                    //String delimeter = ": ";
                    //String[] tmpPacket = message.split(delimeter);
                    //HashMap<String, String> hashPacket = Multicast_Server.strToHash(tmpPacket[1]);
                    //System.out.println(hashPacket.get("type"));
                    //if (hashPacket.get("type").equals("registar")) {
                     //   Multicast_Server.storeData(socket, group, hashPacket);
                   // }
/*
                    else if(hashPacket.get("type").equals("addbanda")){
                        Multicast_Server.addBanda(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("addmusica")) {
                        Multicast_Server.addMusic(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("addartista")) {
                        Multicast_Server.addArtista(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("addconcerto")) {
                        Multicast_Server.addConcerto(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("addcomentario")) {
                        Multicast_Server.addComentario(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("addalbum")) {
                        Multicast_Server.addAlbum(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("entrar")) {
                        Multicast_Server.validaLogIn(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("findArtistaByNome")||hashPacket.get("type").equals("findArtistaByBanda")) {
                        Multicast_Server.encontraArtistas(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("findBandaByNome")||hashPacket.get("type").equals("findBandaByGenero")) {
                        Multicast_Server.encontraBanda(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("findAlbumByNome")||hashPacket.get("type").equals("findAlbumByGenero")||hashPacket.get("type").equals("findAlbumByEditora")||hashPacket.get("type").equals("findAlbumByBanda")) {
                        Multicast_Server.encontraAlbum(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("findMusicaByNome")||hashPacket.get("type").equals("findMusicaByGenero")||hashPacket.get("type").equals("findMusicaByBanda")||hashPacket.get("type").equals("findMusicaByAlbum")) {
                        Multicast_Server.encontraMusica(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("findConcerto")) {
                        Multicast_Server.encontraConcerto(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editBandaNome")||hashPacket.get("type").equals("editBandaGenero")||hashPacket.get("type").equals("editBandaDataFormacao")||hashPacket.get("type").equals("editBandaGenero")||hashPacket.get("type").equals("editBandaHistoria")) {
                        Multicast_Server.editBanda(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editMusicaNome")||hashPacket.get("type").equals("editMusicaBanda")||hashPacket.get("type").equals("editMusicaDataLancamento")||hashPacket.get("type").equals("editMusicaAlbum")||hashPacket.get("type").equals("editMusicaCompositor")||hashPacket.get("type").equals("editMusicaDuracao")||hashPacket.get("type").equals("editMusicaFicheiro")) {
                        Multicast_Server.editMusica(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editArtistaNome")||hashPacket.get("type").equals("editArtistaBanda")||hashPacket.get("type").equals("editArtistaDataNascimento")||hashPacket.get("type").equals("editArtistaLocalidade")) {
                        Multicast_Server.editArtista(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editAlbumNome")||hashPacket.get("type").equals("editAlbumBanda")||hashPacket.get("type").equals("editAlbumDataLancamento")||hashPacket.get("type").equals("editAlbumEditora")||hashPacket.get("type").equals("editAlbumDescricao")||hashPacket.get("type").equals("editAlbumGenero")) {
                        Multicast_Server.editAlbum(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editConcertoBanda")||hashPacket.get("type").equals("editConcertoData")||hashPacket.get("type").equals("editConcertoHora")||hashPacket.get("type").equals("editConcertoLocalidade")) {
                        Multicast_Server.editConcerto(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editPessoaDataNascimento")||hashPacket.get("type").equals("editPessoaEmail")||hashPacket.get("type").equals("editPessoaPassword")) {
                        Multicast_Server.editConfiguracoesPessoais(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("editEditor")) {
                        Multicast_Server.editEditor(socket, group, hashPacket);
                    }
                    else if(hashPacket.get("type").equals("deletePessoa")||hashPacket.get("type").equals("deleteMusica")||hashPacket.get("type").equals("deleteAlbum")||hashPacket.get("type").equals("deleteConcerto")||hashPacket.get("type").equals("deleteArtista")||hashPacket.get("type").equals("deleteBanda")) {
                        Multicast_Server.apagaConteudo(socket, group, hashPacket);
                    }

                    else if(hashPacket.get("type").equals("transfer")){
                        Multicast_Server.daIP(socket, group);
                        Multicast_Server.criaConexao(socket, group, hashPacket);
                    }
*/


                }
            } catch(IOException e) {
                System.out.println("Socket closed!");
            }
        }
    }
}