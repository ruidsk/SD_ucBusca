package ucBuscas;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private static final int N_paginas_A_visitar = 10;
    private Set<String> paginasVisitadas = new HashSet<String>();
    private List<String> paginas_A_Visitar = new LinkedList<String>();

    /**
     * aqui metemos o URL e a palavra, ainda tenho de fazer um construtor p receber so o url
     * ou só a palavra
     * @param url
     * @param searchWord
     */

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


    /**
     * função que vai percorrendo a lista de sites a usar e removendo-os da lista
     * @return
     */
    private String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = this.paginas_A_Visitar.remove(0);
        } while(this.paginasVisitadas.contains(nextUrl));
        this.paginasVisitadas.add(nextUrl);
        return nextUrl;
    }
}