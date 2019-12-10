package ucBuscas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * navegador que usa jsoup
 */
public class WebCrawler {
    static Map<String, Integer> NlinksPSite = new TreeMap<>();  //links e numero de ligacoes
    static Map<String, Integer> Npalavras = new TreeMap<>(); //palavras e numero de vezes que são pesquisadas
    private static int N_paginas_A_visitar = 10;
    private static Set<String> paginasVisitadas = new HashSet<String>();
    private static List<String> paginas_A_Visitar = new LinkedList<String>();
    private static List<String> links2 = new LinkedList<String>();
    private static HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
    private static HashMap<String, HashSet<String>> mapUrls = new HashMap<String, HashSet<String>>();

    private static String diretoria = "D:\\trabalhos - FCTUC - DEI\\---------\\SD\\SD_ucBusca\\meta1\\backups";

    /**
     * @param ws
     * @return true or false
     * função para indexar um url
     */
    public static boolean main(String ws) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Document doc = null;


        // Read website
        // System.out.print("Website: ");
        try {

            //String ws = bf.readLine();
            if (!ws.startsWith("http://") && !ws.startsWith("https://"))
                ws = "http://".concat(ws);
            if(!map.containsKey(ws)) {

                try {
                    // Attempt to connect and get the document
                    doc = Jsoup.connect(ws).get();  // Documentation: https://jsoup.org/
                    System.out.println("**Sucesso** Recebeu do html");
                } catch (MalformedURLException e) {
                    System.out.println("**Failure** Retrieved something other than HTML");
                    return false;

                }
                if (!NlinksPSite.containsKey(ws)) {
                    NlinksPSite.put(ws, 0);
                }
                // Title
                System.out.println(doc.title() + "\n");

                // Get all links
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    // Ignore bookmarks within the page
                    if (link.attr("href").startsWith("#")) {
                        continue;
                    }

                    // Shall we ignore local links? Otherwise we have to rebuild them for future parsing
                    if (!link.attr("href").startsWith("http")) {
                        continue;
                    }
                    //map.put(ws,link.text());


                    if (mapUrls.containsKey(link.attr("href"))) {
                        mapUrls.get(link.attr("href")).add(ws);
                    } else {
                        HashSet<String> aux = new HashSet<>();
                        aux.add(ws);
                        mapUrls.put(link.attr("href"), aux);
                    }
                    links2.add(link.absUrl("href"));
                    NlinksPSite.put(ws, NlinksPSite.get(ws) + 1);
                    System.out.println("Link: " + link.attr("href"));
                    System.out.println("Text: " + link.text() + "\n");
                }
                faz_backup(ws);
                // Get website text and count words
                String text = doc.text(); // We can use doc.body().text() if we only want to get text from <body></body>
                countWords(text, ws);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
            //System.out.println("**Failure** Retrieving links!");
        }

        return true;
    }

    /**
     * @param text
     * @param url
     * função para contar o número de palavras por site bem como adicionar a palavra ao hashmap
     */
    private static void countWords(String text, String url) {
        Map<String, Integer> countMap = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;

        // Get words and respective count
        while (true) {
            try {
                if ((line = reader.readLine()) == null)
                    break;
                String[] words = line.split("[ ,;:.?!(){}\\[\\]<>']+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if ("".equals(word)) {
                        continue;
                    }
                    if (!countMap.containsKey(word)) {
                        countMap.put(word, 1);
                    } else {
                        countMap.put(word, countMap.get(word) + 1);
                    }
                    if (map.containsKey(word)) {
                        map.get(word).add(url);
                    } else {
                        HashSet<String> aux = new HashSet<>();
                        aux.add(url);
                        map.put(word, aux);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Close reader
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display words and counts
       /* for (String word : countMap.keySet()) {
            if (word.length() >= 3) { // Shall we ignore small words?
                System.out.println(word + "\t" + countMap.get(word));
            }
        }*/
    }

    /**
     * @return links2
     * funçao não usada ainda que retorna os links
     */
    public static List<String> getLinks() {

        return links2;
    }


    /**
     * @param url
     * @return string com o numero de sites visitados
     * função que itera N_paginas a visitar e indexa os urls visitados
     */
    public static String indexaRecursiva(String url) {
        int count = 0;
        String ws = url;
        String resultado = "";
        if (!ws.startsWith("http://") && !ws.startsWith("https://"))
            ws = "http://".concat(ws);

        while (paginasVisitadas.size() < N_paginas_A_visitar) {
            String currentUrl;
            if (paginas_A_Visitar.isEmpty()) {
                currentUrl = ws;
                paginasVisitadas.add(ws);
            } else {
                currentUrl = nextUrl();
            }
            main(currentUrl);
            paginas_A_Visitar.addAll(getLinks());
            count++;
        }
        N_paginas_A_visitar += 2;
        System.out.println("\n**Done** Visited " + count + " web page(s)\n");


        resultado=String.valueOf(count);

        return resultado;
    }


    /**
     * função que vai percorrendo a lista de sites a usar e removendo-os da lista
     *
     * @return
     */
    private static String nextUrl() {
        String nextUrl;
        do {
            nextUrl = paginas_A_Visitar.remove(0);
        } while (paginasVisitadas.contains(nextUrl));
        paginasVisitadas.add(nextUrl);
        return nextUrl;
    }

    /**
     * @param palavras
     * @return urls
     * funçao que pesquisa as palavras na hash e retorna os sites onde a palavra aparece
     */
    public static String checkWords(String palavras) {
        String[] words = palavras.split("[ ,;:.?!(){}\\[\\]<>']+");
        String tmp;
        int iterador;
        String urls = "\n ";
        ArrayList<String> resultado = new ArrayList<String>();
        ArrayList<String> aux = new ArrayList<String>();
        int existe = 0;
        //System.out.println(map);
        resultado = obtemUrls2(words[0]);
        for (String word : words) {
            //System.out.println("antes:"+word);
            word = word.toLowerCase();
            //System.out.println("depois:"+word);
            //System.out.println("Vai pesquisar: "+ word);
            if (!Npalavras.containsKey(word)) {
                Npalavras.put(word, 1);
            }
            else {
                Npalavras.put(word, Npalavras.get(word) + 1);
            }
            //System.out.println(Npalavras);
            if (map.containsKey(word)) {
                System.out.println("\nSites: " + resultado);
                aux = obtemUrls2(word);
                //System.out.println("Size do resultado"+ resultado.size());
                for (int j = 1; j < resultado.size(); j++) {
                    existe = 0;
                    for (int i = 0; i < aux.size(); i++) {
                        if (aux.get(i).equals(resultado.get(j-1))) {
                            existe = 1;
                        }
                    }
                    if (existe == 0) {
                        resultado.set(j-1,null);
                    }
                }
                resultado.removeAll(Collections.singleton(null));
                urls = String.valueOf(resultado);
//                tmp = obtemUrls(word);
//                urls= urls + tmp + " ";
            }
        }
        return urls;
    }

    /**
     * @param key
     * @return lista de urls
     * função que recebe uma key e devolde os urls associados a key
     */
    public static String obtemUrls(String key) {
        String[] aux = new String[0], aux2 = new String[100];
        String listEnd = "\n";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry word = (HashMap.Entry) it.next();
            if (word.getKey().equals(key)) {
                aux = word.getValue().toString().split(", ");
                for (int j = 0; j < aux.length; j++) {
                    aux2[j] = Arrays.toString(new String[]{aux[j]});
                    aux2[j] = aux2[j].replace("[", "");
                    aux2[j] = aux2[j].replace("]", "");
                    //System.out.println(aux2[j].toString());
                    listEnd = listEnd + aux2[j].toString() + " \n";
                }
            }
        }
        return listEnd;
    }

    /**
     * @param key
     * @return arrey de sites
     * recebe uma key de map e retorna os sites associados a esta lista em forma de lista
     */
    public static ArrayList<String> obtemUrls2(String key) {
        String[] aux = new String[0], aux2 = new String[100];
        ArrayList<String> listEnd = new ArrayList<String>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry word = (HashMap.Entry) it.next();
            if (word.getKey().equals(key)) {
                aux = word.getValue().toString().split(", ");
                for (int j = 0; j < aux.length; j++) {
                    aux2[j] = Arrays.toString(new String[]{aux[j]});
                    aux2[j] = aux2[j].replace("[", "");
                    aux2[j] = aux2[j].replace("]", "");
                    //System.out.println(aux2[j].toString());
                    listEnd.add(aux2[j].toString());
                }
            }
        }
        return listEnd;
    }

    /**
     * funçao que carrega os sites pesquisados anteriormente que estão guardados num txt
     * @return sucesso ou erro
     * @throws IOException
     */
    public static String load() throws IOException {
        File file = new File("backups/hash.txt");
        file.createNewFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "failure";
        }

        String temp;
        int i = 0;
        try {
            while (((temp = br.readLine()) != null)) {
                i++;
                if (i > 100) {
                    break;
                } else {
                    loadmain(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";



    }

    /**
     * @param ws
     * @return true or false
     * função para indexar um url
     */
    public static boolean loadmain(String ws) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Document doc = null;


        // Read website
        // System.out.print("Website: ");
        try {

            //String ws = bf.readLine();
            if (!ws.startsWith("http://") && !ws.startsWith("https://"))
                ws = "http://".concat(ws);
            if(!map.containsKey(ws)) {

                try {
                    // Attempt to connect and get the document
                    doc = Jsoup.connect(ws).get();  // Documentation: https://jsoup.org/
                    System.out.println("**Sucesso** Recebeu do html");
                } catch (MalformedURLException e) {
                    System.out.println("**Failure** Retrieved something other than HTML");
                    return false;

                }
                if (!NlinksPSite.containsKey(ws)) {
                    NlinksPSite.put(ws, 0);
                }
                // Title
                System.out.println(doc.title() + "\n");

                // Get all links
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    // Ignore bookmarks within the page
                    if (link.attr("href").startsWith("#")) {
                        continue;
                    }

                    // Shall we ignore local links? Otherwise we have to rebuild them for future parsing
                    if (!link.attr("href").startsWith("http")) {
                        continue;
                    }
                    //map.put(ws,link.text());


                    if (mapUrls.containsKey(link.attr("href"))) {
                        mapUrls.get(link.attr("href")).add(ws);
                    } else {
                        HashSet<String> aux = new HashSet<>();
                        aux.add(ws);
                        mapUrls.put(link.attr("href"), aux);
                    }
                    links2.add(link.absUrl("href"));
                    NlinksPSite.put(ws, NlinksPSite.get(ws) + 1);
                    System.out.println("Link: " + link.attr("href"));
                    System.out.println("Text: " + link.text() + "\n");
                }

                // Get website text and count words
                String text = doc.text(); // We can use doc.body().text() if we only want to get text from <body></body>
                countWords(text, ws);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
            //System.out.println("**Failure** Retrieving links!");
        }

        return true;
    }

    /**
     * funçao que carrega as pesquisas realizadas anteriormente pelo userque estão guardados num txt
     * @param user
     * @return
     * @throws IOException
     */
    public static String loadUser(String user) throws IOException {
        File file = new File("backups/" + user + "_hist.txt");
        file.createNewFile();
        BufferedReader br = null;
        String result="";
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "failure";
        }

        String temp;
        int i = 0;
        try {
            while (((temp = br.readLine()) != null)) {
                i++;
                if (i > 100) {
                    break;
                } else {
                    if (!Npalavras.containsKey(temp)) {
                        Npalavras.put(temp, 1);

                    }
                    else {
                        Npalavras.put(temp, Npalavras.get(temp) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
        return result;

    }

    /**
     * função que guarda os dados pesquisados num ficheiro txt
     * @param ws
     */
    public static void faz_backup(String ws) {
        File file = new File("backups/hash.txt");
        if (file.exists() && file.isFile()) {
            try {
                FileWriter filew = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(filew);
                bw.append(ws);
                bw.newLine();
                bw.close();
                filew.close();
            } catch (IOException e) {
                System.out.println("Não foi possível escrever no file");
            }
        } else {
            System.out.println("Não exite qualquer backup");
        }

        // ler_dados();
    }

    /**
     * Consultar lista de pesquisas feitas pelo próprio utilizador
     * @param user
     * @param text
     * @return sucesso ou erro
     * @throws IOException
     */

    public static boolean atualizaConsultas(String user, String text) throws IOException {
        File file = new File("backups/" + user + "_hist.txt");
        file.createNewFile();
        BufferedReader br = null;
        try {
            FileWriter filew = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(filew);
            bw.append(text);
            bw.newLine();
            bw.close();
            filew.close();
        } catch (IOException e) {
            System.out.println("Não foi possível escrever no file");
            return false;
        }
        return true;
    }

    /**
     * mostra as consultas realizadas pelo utilizador
     * @param user
     * @return
     * @throws IOException
     */
    public static String mostraConsultas(String user) throws IOException {
        File file = new File("backups/" + user + "_hist.txt");
        file.createNewFile();
        BufferedReader br = null;
        String result="";
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "failure";
        }

        String temp;
        int i = 0;
        try {
            while (((temp = br.readLine()) != null)) {
                i++;
                if (i > 100) {
                    break;
                } else {
                    result=result+temp+"\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
        return result;
    }

    /**
     * Resultados ordenados por número de ligações para cada página
     * @return
     */

    public static String tabelaLigacoes() {
        int maiorI = 0;
        String auxS = "";
        String resultado = "\n";
        for (String key : NlinksPSite.keySet()) {
            if (NlinksPSite.get(key) > maiorI) {
                maiorI = NlinksPSite.get(key);
                auxS = key;
            }
        }
        resultado = resultado +"1. "+ auxS + "\t" + maiorI;
        for (int i = 2; i < 11; i++) {
            int aux = 0;
            for (String key : NlinksPSite.keySet()) {
                if (NlinksPSite.get(key) > aux && NlinksPSite.get(key) < maiorI) {
                    aux = NlinksPSite.get(key);
                    auxS = key;
                }
            }
            if (aux != 0) {
                resultado = resultado + "\n"+i+". " + auxS + "\t" + aux;
            }
            maiorI = aux;
        }

        return resultado;
    }

    /**
     * recebe uma key url e vai a hash mapUrls e devolve os urls que estão associados a essa key
     * @param key
     * @return
     */
    public static String obtemUrlsFromUrls(String key) {
        String[] aux = new String[0], aux2 = new String[10000];
        String listEnd = "\n";
        Iterator it = mapUrls.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry word = (HashMap.Entry) it.next();
            if (word.getKey().equals(key)) {
                aux = word.getValue().toString().split(", ");
                for (int j = 0; j < aux.length; j++) {
                    aux2[j] = Arrays.toString(new String[]{aux[j]});
                    aux2[j] = aux2[j].replace("[", "");
                    aux2[j] = aux2[j].replace("]", "");
                    //System.out.println(aux2[j].toString());
                    listEnd = listEnd + aux2[j].toString() + " \n";
                }
            }
        }
        return listEnd;
    }

    /**
     * mostra os urls associados a um url
     * @param ws
     * @return
     */
    public static String ligacoesALinks(String ws){
        String resultado= "\n";
        if (!ws.startsWith("http://") && !ws.startsWith("https://"))
            ws = "http://".concat(ws);
        if (mapUrls.containsKey(ws)){
            resultado = "Os urls relacionados são:" + obtemUrlsFromUrls(ws);
        }
        else{
            resultado="Não existe o link na base de dados";
        }
        return resultado;
    }


    /**
     * apresenta as palavras ordenadas por ordem de número de vezes que foi pesquisada
     * @return
     */
    public static String tabelaPalavras() {

        int maiorI = 0;
        String auxS = "";
        String lastKey="";
        String resultado = "\n";
        for (String key : Npalavras.keySet()) {
            if (Npalavras.get(key) > maiorI) {
                maiorI = Npalavras.get(key);
                auxS = key;
            }
        }
        resultado = resultado +"1. "+ auxS + "\t" + maiorI;
        for (int i = 2; i < 11; i++) {
            int aux = 0;
            for (String key : Npalavras.keySet()) {
                if (Npalavras.get(key) > aux && Npalavras.get(key) < maiorI ||maiorI==1) {
                    aux = Npalavras.get(key);
                    auxS = key;
                }
            }
            if (!lastKey.equals(auxS) && aux != 0) {
                resultado = resultado + "\n"+i+". " + auxS + "\t" + aux;
                lastKey=auxS;
            }
            maiorI = aux;
        }

        return resultado;
    }
}


/*
    public void search(String url, String searchWord)
    {
        while(this.paginasVisitadas.size() < N_paginas_A_visitar)
        {
            String currentUrl;
            WebCrawler2 leg = new WebCrawler2();
            if(this.paginas_A_Visitar.isEmpty())
            {
                currentUrl = url;
                this.paginasVisitadas.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl);
            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.paginas_A_Visitar.addAll(leg.getLinks());
        }
        System.out.println("\n**Done** Visited " + this.paginasVisitadas.size() + " web page(s)");
    }

 */
