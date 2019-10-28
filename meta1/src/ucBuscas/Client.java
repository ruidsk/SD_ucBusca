package ucBuscas;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {


    RMIInterface rmi_interface = null;
    boolean flag ;
    protected Client() throws RemoteException {
        super();
    }

    public static void main(String args[]) throws RemoteException {

        /* This might be necessary if you ever need to download classes:*/
        System.getProperties().put("java.security.policy", "al.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Client client = new Client();

        client.rmi_interface = client.connect();
        client.rmi_interface.load_online();

        client.menu(client);


        //System.out.println("Exception in main: " + e);
        //e.printStackTrace();
    }

    public void notification(String note) throws RemoteException {
        System.out.println(note);
        if(flag==true){

        }
    }

    public String menu(Client c) throws RemoteException {

        int a = 0;
        boolean exception;
        String aux = null;

        do {

            System.out.println("1. Entrar");
            System.out.println("2. Registar");
            System.out.println("3. Realizar uma pesquisa");
            System.out.println("0. Sair");
            System.out.println("Selecione o número que deseja: ");
            Scanner input = new Scanner(System.in);
            do {
                exception = false;
                try {
                    a = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    exception = true;
                    System.out.println("\nNúmero inválido. Tente outra vez!");
                }
            } while (exception);
            System.out.println("\n");
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
                            } else if (aux.contains("fail")) {
                                System.out.println("\nuser/password incorreto\n");
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
                            System.out.println("o servidor respondeu : " + aux);
                        } catch (RemoteException | NullPointerException enp) {
                            rmi_interface = connect();
                            rmi_interface.load_online();
                            exception = true;
                        }
                    } while (exception);

                    break;
                case 3:

                    System.out.println("\nIntroduza as palavras, separadas por espaços:");
                    String text = input.nextLine();

                    String tmp = rmi_interface.checkWords(text);
                    String[] tmp_split = tmp.split(":", 2);

                    if (tmp.length() == 22) {
                        System.out.println("\nNão existem urls com as palavras!");
                    } else {

                        System.out.println("\nOs urls são: " + tmp_split[1]);
                    }

                    break;

                case 99:
                    rmi_interface.show_online();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Escolha errada!");

            }

        } while (a != 0);


        return aux;
    }

    public String menuPrincipal(String username, Client c) throws RemoteException {

        rmi_interface.loadUser(username);

        int a = 0;
        boolean exception;
        String aux = null;
        do {
            flag = true;
            System.out.println("\n\n-----MENU----- user: " + username);
            System.out.println("\n1. Consultar o histórico de pesquisas");
            System.out.println("2. Resultados ordenados por número de ligações para cada página");
            System.out.println("3. Pesquisar páginas que contenham um conjunto de palavras");
            System.out.println("4. Consultar lista de páginas com ligações para uma página específica");
            System.out.println("5. Consultar palavras mais pesquisadas");
            try {
                aux = rmi_interface.check_admin(username);
                if (aux.contains("success")) {
                    System.out.println("10. Entrar em modo administrador");
                }
            } catch (NullPointerException | RemoteException np) {
                rmi_interface = connect();
                rmi_interface.load_online();
            }
            System.out.println("0. Logout");
            System.out.println("Selecione o número que deseja: ");
            Scanner input = new Scanner(System.in);
            flag =false;
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
                    String tmp = rmi_interface.mostraConsultas(username);
                    String[] tmp_split;
                    tmp_split = tmp.split(":", 2);
                    //System.out.println("size:" + tmp.length());
                    if (tmp.length() < 21) {
                        System.out.println("Não existem pesquisas na base de dados");
                    } else {
                        System.out.println("\n\nAs pesquisas realizadas são:");
                        System.out.println(tmp_split[1]);
                    }



                    break;

                case 2:


                    tmp = rmi_interface.tabelaLigacoes();
                    //System.out.println("size:" + tmp.length());
                    tmp_split = tmp.split(":", 2);
                    if (tmp.length()<27) {

                        System.out.println("\nNão existem sites na base de dados");
                    } else {
                        System.out.println("\n\nOs sites com mais links são:");
                        System.out.println(tmp_split[1]);
                    }

//                    if (tmp.length()==22) {
//                        System.out.println("\nNão existem urls com as palavras!");
//                    } else {
//
//                        System.out.println("\nOs urls são: "+tmp_split[1]);
//                    }
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
                    System.out.println("\nIntroduza as palavras, separadas por espaços:");
                    String text = input.nextLine();


                    tmp = rmi_interface.checkWords(text);
                    rmi_interface.atualizaConsultas(username, text);
                    tmp_split = tmp.split(":", 2);

                    if (tmp.length() == 22) {
                        System.out.println("\nNão existem urls com as palavras!");
                    } else {

                        System.out.println("\nOs urls são: " + tmp_split[1]);
                    }
                    break;

                case 4:
                    System.out.println("\nIntroduza o url a pesquisar:");
                    text = input.nextLine();
                    tmp = rmi_interface.ligacoesALinks(text);
                    tmp_split = tmp.split(":", 2);
                    System.out.println(tmp_split[1]);

                    break;

                case 5:

                    tmp = rmi_interface.tabelaPalavras();
                    //System.out.println("size:" + tmp.length());
                    tmp_split = tmp.split(":", 2);
                    if (tmp.length() < 27) {
                        System.out.println("Não existem palavras pesquisadas!");
                    } else {
                        System.out.println("\n\nAs palavras mais pesquisadas são:");
                        System.out.println(tmp_split[1]);
                    }

                    break;

                case 10:
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

                case 99:
                    rmi_interface.show_online();
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
            System.out.println("\n\n-----ADMIN MENU------");
            System.out.println("\n1. Adicionar novo admin");
            System.out.println("2. Indexar um url");
            System.out.println("3. Indexar iterativamente urls");
            System.out.println("4. Mostrar utilizadores online");
            System.out.println("0. Voltar ao menu principal");
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
                    System.out.println("Introduza o username do novo admin: ");
                    String user = input.next();
                        /*System.out.println("Password: ");
                        String password = input.next();*/
                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.give_admin(user);
                            if (aux.startsWith("Servidor Multicast: type | give ; resultado | success ;")) {
                                System.out.println("administrador atribuido a :" + user);
                            } else if (aux.contains("fail")) {
                                System.out.println("erro a atribuir admin");

                            } else if (aux.contains("already")) {
                                System.out.println("esse user já é admin");
                            }
                        } catch (NullPointerException | RemoteException np) {
                            rmi_interface = connect();
                            rmi_interface.load_online();
                            exception = true;
                        }

                    } while (exception);

                    break;

                case 2:
                    System.out.println("Introduza o URL: ");
                    String url = input.next();
                    System.out.println("\n");
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

                    System.out.println("Introduza o URL: ");
                    url = input.next();
                    System.out.println("\n");
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

                case 4:
                    do {
                        try {
                            exception = false;
                            aux = rmi_interface.showOnline2Admin();
                            System.out.println("\t\t- ONLINE USERS -\n"+ aux);

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

                //david -> 194.210.37.29
                //david MV -> 10.211.55.3
                //me -> 192.168.56.1

                return h;
            } catch (Exception e) {
                h = null;
                //System.out.println("no\n");
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

}
