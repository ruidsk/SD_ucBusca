import java.net.*;
import java.io.*;

public class SimpleServer {
    public static void main(String args[]) throws IOException {
        // Register service on port 1234
        ServerSocket s = new ServerSocket(1234);
        while (true) {
            Socket s1 = s.accept(); // Wait and accept a connection
            // Get a stream associated with the socket
            OutputStream s1out = s1.getOutputStream();
            DataOutputStream dos = new DataOutputStream(s1out);
            // Send a string!
            dos.writeUTF("Ola Boa Noite");
            System.out.println("Respondeu ao cliente");
            // Close the connection, but not the server socket
            dos.close();
            s1out.close();
            s1.close();
        }
    }
}