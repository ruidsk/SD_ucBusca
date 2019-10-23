package ucBuscas;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject{


    private RMIInterface rmi_interface = null;

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
                   do {
                        try {
                            exception = false;
                            aux = rmi_interface.login(username, password);

                            return aux;
                        } catch (NullPointerException | RemoteException np){
                            rmi_interface = connect();
                            exception = true;
                        }
                    } while (exception);

                    break;

                case 2:
                    System.out.println("Username: ");
                    username = input.nextLine();
                    System.out.println("Password: ");
                    password = input.nextLine();
                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.regista(username, password);
                            System.out.println("o servidor respondeu : "+aux);
                        } catch (RemoteException |NullPointerException enp) {
                            rmi_interface=connect();
                            exception = true;
                        }
                    } while (exception);

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

    public String menuPrincipal(){

            int a=0;
            boolean exception;
            String aux = null;
            do {

                System.out.println("1. coias");
                System.out.println("2. coisas");
                System.out.println("0. coisas");
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
                        /*System.out.println("Username: ");
                        String username = input.next();
                        System.out.println("Password: ");
                        String password = input.next();
                        do {
                            try {
                                exception = false;
                                aux = rmi_interface.login(username, password);
                                if (aux.startsWith("type | registo ; resultado | success ;"){
                                    menuPrincipal();
                                }
                            } catch (NullPointerException | RemoteException np){
                                rmi_interface = connect();
                                exception = true;
                            }
                        } while (exception);
                        */
                        break;

                    case 2:
                       /* System.out.println("Username: ");
                        username = input.nextLine();
                        System.out.println("Password: ");
                        password = input.nextLine();
                        do {
                            try {
                                exception = false;
                                aux = rmi_interface.regista(username, password);
                                System.out.println("o servidor respondeu : "+aux);
                            } catch (RemoteException |NullPointerException enp) {
                                rmi_interface=connect();
                                exception = true;
                            }
                        } while (exception);*/

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






    public RMIInterface connect() {
        int port = 7000;

        long time = System.currentTimeMillis() + 30000;
        RMIInterface h = null;
       do {

            try {

                h = (RMIInterface) LocateRegistry.getRegistry(port).lookup("ucBusca");

                return h;
            } catch (Exception e) {
                h= null;
                if (port == 7000) {
                    port = 7001;
                } else if (port == 7001) {
                    port = 7000;
                }

            }

        }while(h==null &&  System.currentTimeMillis() < time);

        System.out.println("RMI CONNECTION TIMEOUT");
        System.exit(0);
        return null;

    }


    public static void main(String args[]) throws RemoteException {

		/* This might be necessary if you ever need to download classes:
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/
        Client client = new Client();

        client.rmi_interface = client.connect();
        if(client.menu().equals("Servidor Multicast: type | login ; resultado | success ;")){
            client.menuPrincipal();
        }else{
            System.out.println("login failed try again\n");
            client.menu();
        }





        //System.out.println("Exception in main: " + e);
        //e.printStackTrace();
    }

}