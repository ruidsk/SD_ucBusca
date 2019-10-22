//ACCEPT----------------------------------------------

ServerSocket server=new ServerSocket(PORT);
// Server main loop
while(true){try{Socket clientSocket=server.accept();ClientThread theClient=new ClientThread(clientSocket);theClient.start();}catch(Exception e){
// Error accepting one of the clients
e.printStackTrace();}}

//HANDLE----------------------------------------------
class ClientThread extends Thread {
    private Socket clientSocket;

    public ClientThread(Socket clientSocket) { 
        this.clientSocket = clientSocket;
    }
    public void run() {
        // Handle the client request here! 
        // ...
    } 
}