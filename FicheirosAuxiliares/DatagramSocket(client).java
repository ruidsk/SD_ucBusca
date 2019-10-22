import java.io.*;
import java.net.*;

/*
 * Para enviar dados para um servidor: Converte-se os dados para array de bytes.
 * Passa-se esse array, o nu ́mero de bytes, a InetAddress e o porto de destino
 * ao construtor do DatagramPacket. Cria-se um DatagramSocket e envia-se o
 * pacote atrav ́es do m ́etodo send().
 */

public class SimpleClient {
    public static void main(String args[]) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        // send request
        InetAddress address = InetAddress.getByName("eden.dei.uc.pt");
        String s = "get_current_time_date";
        byte[] b = s.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, address, 4445);
        socket.send(packet);

        // get response
        byte[] buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData());
        System.out.println("the time:" + received);
        socket.close();
    }
}