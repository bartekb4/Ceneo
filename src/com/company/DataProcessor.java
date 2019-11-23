package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    private CeneoAPIHandler ceneoAPIHandler=new CeneoAPIHandler();
    Document search_soup;
    List <String> titlesList=new ArrayList<>();
    List<Element> shopsList=new ArrayList<Element>();

    {
        try {
            search_soup = ceneoAPIHandler.send_search_request(ceneoAPIHandler.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void find_best_product_ids(){ //tu sie dzieje scraping
        String id=null;
        Elements titles=search_soup.getElementsByClass("js_seoUrl go-to-product btn btn-primary btn-cat btn-cta js_force-conv js_clickHash");
        for (Element t:titles) {
            titlesList.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek

        }
        System.out.println(titles);
        System.out.println(titlesList);

        Elements shops=search_soup.select("span.value");

        System.out.println(shops);
        //System.out.println(shopsList);
    }
//nie ogarniam jak sie dostac do ID albo cen
}
