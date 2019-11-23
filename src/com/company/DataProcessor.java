package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class DataProcessor {
    private CeneoAPIHandler ceneoAPIHandler=new CeneoAPIHandler();
    Document search_soup;
    List <String> best_ids=new ArrayList<>();

    {
        try {
            search_soup = ceneoAPIHandler.send_search_request(ceneoAPIHandler.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void find_best_product_ids(){ //tu sie dzieje scraping
        String id=null;
        Elements best_id=search_soup.getElementsByClass("js_seoUrl js_clickHash go-to-product");
        for (Element t:best_id) {
            best_ids.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek

        }
        System.out.println(best_id);
        System.out.println(best_ids);
    }
//nie ogarniam jak sie dostac do ID albo cen
}
