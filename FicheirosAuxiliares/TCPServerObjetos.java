public class TCPServer1_O {
public static void main(String args[]) throws IOException,ClassNotFoundException{ 
    int serverPort = 6000;
    //...
    listenSocket = new ServerSocket(serverPort);

    while(true){

        System.out.println("A espera de ligacao no socket "+serverPort); 
        try{
            clientSocket = listenSocket.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            // Criar ObjectinputStream e ObjectOutputStream
            ObjectOutputStream obj_o = new ObjectOutputStream(out); 
            ObjectInputStream obj_i = new ObjectInputStream(in);
            Data d;

            while(true){
                d = (Data)obj_i.readObject();
                System.out.println("Recebeu: "+d); d.print_conteudo();
                out.writeUTF("OBJECTO OK"); out.flush();
            }// while 
        //... catches....