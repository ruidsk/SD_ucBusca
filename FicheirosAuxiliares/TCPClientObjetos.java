Client:TCPClientO
public class TCPClient_O {
public static void main (String args[]) { String texto;
Socket s = null;
int serversocket = 6000;
try{
    // 1o passo
    s = new Socket(host, serversocket);
    //...
    // 2o passo
    DataInputStream in = new DataInputStream( s.getInputStream());
    DataOutputStream out = new DataOutputStream( s.getOutputStream());

    // Criar ObjecOutputStream e ObjectInputStream
    ObjectOutputStream obj_o = new ObjectOutputStream(out); 
    ObjectInputStream obj_i = new ObjectInputStream(in);

    int contador=0;
    // 3o passo 
    while(contador < 5){
        contador++;
        Data d = new Data(contador); 
        obj_o.writeObject(d);
        obj_o.flush();
        d.print_conteudo(); 
        System.out.println("ENVIEI OBJECTO"); 
        String data = in.readUTF(); 
        System.out.println("Received: "+ data);

    } 
//... catches....