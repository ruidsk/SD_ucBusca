package ucBuscas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebCrawler {
    private static final int N_paginas_A_visitar = 2;
    private static Set<String> paginasVisitadas = new HashSet<String>();
    private static List<String> paginas_A_Visitar = new LinkedList<String>();
    private static List<String> links2 = new LinkedList<String>();


    public static boolean main(String ws) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Document doc =null;


        // Read website
        // System.out.print("Website: ");
        try {

            //String ws = bf.readLine();
            if (! ws.startsWith("http://") && ! ws.startsWith("https://"))
                ws = "http://".concat(ws);


            try {
                // Attempt to connect and get the document
                System.out.println("imprime");
                doc = Jsoup.connect(ws).get();  // Documentation: https://jsoup.org/
                System.out.println("nao imprime");
            }
            catch (MalformedURLException e){
                return false;
                //System.out.println("**Failure** Retrieved something other than HTML");
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
                links2.add(link.absUrl("href"));
                System.out.println("Link: " + link.attr("href"));
                System.out.println("Text: " + link.text() + "\n");
            }

            // Get website text and count words
            String text = doc.text(); // We can use doc.body().text() if we only want to get text from <body></body>
            countWords(text);
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
            //System.out.println("**Failure** Retrieving links!");
        }
        return true;
    }

    private static void countWords(String text) {
        Map<String, Integer> countMap = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;

        // Get words and respective count
        while (true) {
            try {
                if ((line = reader.readLine()) == null)
                    break;
                String[] words = line.split("[ ,;:.?!“”(){}\\[\\]<>']+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if ("".equals(word)) {
                        continue;
                    }
                    if (!countMap.containsKey(word)) {
                        countMap.put(word, 1);
                    }
                    else {
                        countMap.put(word, countMap.get(word) + 1);
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
        for (String word : countMap.keySet()) {
            if (word.length() >= 3) { // Shall we ignore small words?
                System.out.println(word + "\t" + countMap.get(word));
            }
        }
    }

    public static List<String> getLinks() {

        return links2;
    }


    public static boolean indexaRecursiva(String url){

        String ws =url ;
        if (! ws.startsWith("http://") && ! ws.startsWith("https://"))
            ws = "http://".concat(ws);

        while(paginasVisitadas.size() < N_paginas_A_visitar) {
            String currentUrl;
            if(paginas_A_Visitar.isEmpty()) {
                currentUrl = ws;
                paginasVisitadas.add(ws);
            }
            else {
                currentUrl = nextUrl();
            }
            main(currentUrl);
            paginas_A_Visitar.addAll(getLinks());
        }
        System.out.println("\n**Done** Visited " + paginasVisitadas.size() + " web page(s)");

        return true;
    }


    /**
     * função que vai percorrendo a lista de sites a usar e removendo-os da lista
     * @return
     */
    private static String nextUrl() {
        String nextUrl;
        do {
            nextUrl = paginas_A_Visitar.remove(0);
        } while(paginasVisitadas.contains(nextUrl));
        paginasVisitadas.add(nextUrl);
        return nextUrl;
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