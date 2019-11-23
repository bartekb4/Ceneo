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
    Document product_soup;
    List <String> titlesList=new ArrayList<>();
    List<Element> shopsList=new ArrayList<Element>();
    List <String> urlList=new ArrayList<>();

    {
        try {
            search_soup = ceneoAPIHandler.send_search_request(ceneoAPIHandler.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            product_soup=ceneoAPIHandler.send_product_request(find_best_product_ids());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String find_best_product_ids(){ //tu sie dzieje scraping
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

        Elements link = search_soup.select("a");
        for (Element t:titles) {
            urlList.add(t.attr("href")); //dodaje do listy wszysytkie nazwy pralek

        }
        System.out.println(urlList); //Tu sie wyswietlaja koncowki urli do prodouktu\
        return urlList.get(1);  // pierwszy element listy tylko zeby dzialalo - potem przydaloby sie wybierac po nazwie
    }
    public void request_product_soup(){
        Elements values=product_soup.select("*");
        System.out.println(values); ///odczytuje link dla danego produktu
    }
//nie ogarniam jak sie dostac do ID albo cen
}
