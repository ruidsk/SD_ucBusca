package ucBuscas;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {


    private RMIInterface rmi_interface = null;

    protected Client() throws RemoteException {
        super();
    }


    public void notification(String note) throws RemoteException {
        System.out.println(note);
    }

    public String menu(Client c) {

        int a = 0;
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

            switch (a) {

                case 1:
                    System.out.println("Username: ");
                    String username = input.next();
                    System.out.println("Password: ");
                    String password = input.next();

                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.login(username, password);

                            if (aux.equals("Servidor Multicast: type | logged ; resultado | success ;")) {

                                rmi_interface.subscribe(username, (ClientInterface) c);
                                menuPrincipal(username, c);
                            }
                        } catch (NullPointerException | RemoteException np) {
                            rmi_interface = connect();
                            rmi_interface.load_online();

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
                            rmi_interface.load_online();
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

        } while (a != 0);


        return aux;
    }

    public String menuPrincipal(String username, Client c) throws RemoteException {

        int a = 0;
        boolean exception;
        String aux = null;
        do {
            System.out.println("-----MENU----- user: " + username);
            System.out.println("1. entrar em modo administrador");
            System.out.println("2. coisas");
            System.out.println("3. Pesquisar páginas que contenham um conjunto de palavras");
            System.out.println("0. logout");
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

            switch (a) {

                case 1:

                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.check_admin(username);
                            if (aux.contains("success")) {
                                admin_menu(username, c);
                            }
                        } catch (NullPointerException | RemoteException np) {
                            rmi_interface = connect();
                            rmi_interface.load_online();
                            exception = true;
                        }
                    } while (exception);


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
                                rmi_interface.load_online();
                                exception = true;
                            }
                        } while (exception);*/

                    break;

                case 3:
                    System.out.println("Introduza as palavras, separadas por espaços.");
                    String text = input.nextLine();

                    String tmp;
                    tmp = rmi_interface.checkWords(text);
                    if (tmp.isEmpty()) {
                        System.out.println("Não estem urls com as palavras");
                    } else {
                        System.out.println("Os url são:" + tmp);
                    }


                    break;


                case 0:
                    System.out.println("Saindo para o menu login...");
                    rmi_interface.disconnect(username);
                    menu(c);
                    break;

                default:
                    System.out.println("Escolha errada!");

            }

        } while (a != 0);


        return aux;
    }

    public void admin_menu(String username, Client c) throws RemoteException {
        int a = 0;
        boolean exception;
        String aux = null;
        do {
            System.out.println("-----ADMIN MENU------");
            System.out.println("1. adicionar novo admin");
            System.out.println("2. coisas");
            System.out.println("3. Adicionar url");
            System.out.println("0. menu principal");
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

            switch (a) {

                case 1:
                    System.out.println("introduza o username do novo admin: ");
                    String user = input.next();
                        /*System.out.println("Password: ");
                        String password = input.next();*/
                        do {
                            try {
                                exception = false;
                                aux = rmi_interface.give_admin(user);
                                if (aux.startsWith("Servidor Multicast: type | give ; resultado | success ;")){
                                    System.out.println("administrador atribuido a :"+user);
                                }
                            } catch (NullPointerException | RemoteException np){
                                rmi_interface = connect();
                                rmi_interface.load_online();
                                exception = true;
                            }
                        
                    } while (exception);

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
                                rmi_interface.load_online();
                                exception = true;
                            }
                        } while (exception);*/

                    break;

                case 3:
                    System.out.println("Introduza o URL ");
                    String url = input.next();
                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.addUrl(url);
                            if (aux.startsWith("Servidor Multicast: type | addUrl ; resultado | success ;")) {
                                System.out.println(url + " adicionado com sucesso");
                            }
                        } catch (NullPointerException | RemoteException np) {
                            rmi_interface = connect();
                            rmi_interface.load_online();
                            exception = true;
                        }
                    } while (exception);

                    break;


                case 0:
                    System.out.println("Saindo...");
                    menuPrincipal(username, c);
                    break;

                default:
                    System.out.println("Escolha errada!");

            }

        } while (a != 0);


        // return aux;
    }

    public RMIInterface connect() {
        int port = 7000;

        long time = System.currentTimeMillis() + 30000;
        RMIInterface h = null;


        do {

            try {

                h = (RMIInterface) LocateRegistry.getRegistry(port).lookup("ucBusca");
                h.load_online();
                return h;
            } catch (Exception e) {
                h = null;
                if (port == 7000) {
                    port = 7001;
                } else if (port == 7001) {
                    port = 7000;
                }

            }

        } while (h == null && System.currentTimeMillis() < time);

        System.out.println("RMI CONNECTION TIMEOUT");
        System.exit(0);
        return null;

    }


    public static void main(String args[]) throws RemoteException {

		/* This might be necessary if you ever need to download classes:*/


        Client client = new Client();

        client.rmi_interface = client.connect();
        client.rmi_interface.load_online();

        client.menu(client);






        //System.out.println("Exception in main: " + e);
        //e.printStackTrace();
    }

}
