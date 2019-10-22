package ucBuscas;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject{

    protected Client() throws RemoteException {
        super();
    }

    public String menu(){

        int a=0;
        boolean exception;
        String aux = null;
        do {

            System.out.println("1. ENTRAR");
            System.out.println("2. REGISTAR");
            System.out.println("0. SAIR");
            System.out.println("Selecione o número que deseja: ");
            Scanner input = new Scanner(System.in);
            do {
                exception = false;
                try {
                    a = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    exception = true;
                    System.out.println("Número inválido. Tente outra vez!");
                }
            } while (exception);

            switch(a){

                case 1:
                    System.out.println("Username: ");
                    String username = input.next();
                    System.out.println("Password: ");
                    String password = input.next();

                   /* do {
                        try {
                            exception = false;
                            aux = RMIInterface.login(username, password);
                        } catch (RemoteException e) {
                            connect();
                            exception = true;
                        }
                    } while (exception);
*/
                    break;

                case 2:
                    System.out.println("Username: ");
                    username = input.nextLine();
                    System.out.println("Password: ");
                    password = input.nextLine();
                   /* System.out.println("Data de Nascimento (dd/mm/yy): ");
                    String datanascimento = input.nextLine();
                    System.out.println("Email: ");
                    String email = input.nextLine();*//*
                    do {
                        try {
                            exception = false;
                            aux = RMIInterface.regista(username, password);
                        } catch (RemoteException e) {
                            connect();
                            exception = true;
                        }
                    } while (exception);
*/

                    break;


                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Escolha errada!");

            }

        }while(a!=0);



        return aux;
    }
    public void connect() {
        int port = 7000;
        boolean flag_exception = true;
        long time = System.currentTimeMillis() + 30000;
        RMIInterface h = null;
        while (flag_exception && time < System.currentTimeMillis()) {

            try {
                flag_exception = false;
                h = (RMIInterface) LocateRegistry.getRegistry(port).lookup("ucBusca");

            } catch (Exception e) {
                flag_exception = true;
                if (port == 7000) {
                    port = 7001;
                } else if (port == 7001) {
                    port = 7000;
                }
            }

        }
        if(h == null){
            System.out.println("CONNECTION TIMED OUT");
        }

    }

    public static void main(String args[]) throws RemoteException {

		/* This might be necessary if you ever need to download classes:
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/
        Client client = new Client();

        client.connect();
        client.menu();





        //System.out.println("Exception in main: " + e);
        //e.printStackTrace();
    }

}