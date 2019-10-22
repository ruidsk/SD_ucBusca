import java.net.*;
import java.io.*;

public class SimpleServer {
    public static void main(String args[]) throws IOException {

        DatagramSocket socket = new DatagramSocket();

        // receives a packet request
        byte[] buf = new byte[256];
        DatagramPacket packetRequest = new DatagramPacket(buf, buf.length);
        socket.receive(packetRequest);

        /*
         * Ler mensagens grandes: int size,tam=0; while(tam < N){
         * size=in.readBytes(.....); tam = tam + size; }
         */

        // Display the request
        byte[] data = packetRequest.getData();
        String s = new String(data, 0, data.length);
        System.out.println("Port " + packet1.getPort() + " on " + packet1.getAddress() + " sent this message:" + s);
        if (s.equals("get_current_time_date")) {
            String theDate = (new Date()).toString();
            byte[] bufSend = theDate.getBytes();

            InetAddress address = packetRequest.getAddress();
            int port = packetRequest.getPort();
            DatagramPacket responsePacket = new DatagramPacket(bufSend, bufSend.length, address, port);
            socket.send(responsePacket);
        }

    }
}