package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class DataProcessor {
    private CeneoAPIHandler ceneoAPIHandler=new CeneoAPIHandler();
    private Document search_soup;
    private Document product_soup;
    private List <String> titlesList=new ArrayList<>();
    private List<String> valuesList=new ArrayList<String>();
    private List <String> urlList=new ArrayList<>();
    private List<Double> pricesList=new ArrayList<>();


    public String find_best_product_ids() throws IOException, ParseException { //tu sie dzieje scraping
        search_soup = ceneoAPIHandler.send_search_request(ceneoAPIHandler.url);
        String id=null;
        Elements titles=search_soup.getElementsByClass("js_seoUrl go-to-product btn btn-primary btn-cat btn-cta js_force-conv js_clickHash");
        for (Element t:titles) {
            titlesList.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek

        }
        System.out.println(titles);
        System.out.println(titlesList);


        for (Element t:titles) {
            urlList.add(t.attr("href")); //dodaje do listy wszysytkie linki pralek
        }

        for(int i=0;i<titlesList.size();i++) {
            valuesList.add(search_soup.getElementsByAttributeValue("title", titlesList.get(i)).
                    select("span.value,span.penny").
                    text().replaceAll("\\s","").replaceAll(",","."));
          //  values.add(search_soup.getElementsByAttributeValue("title", titlesList.get(i)).select("span.penny").text());
        }


        System.out.println(urlList); //Tu sie wyswietlaja koncowki urli do prodouktu\
        System.out.println(valuesList);//tu sie wyswietlaja ceny\


        for(int i=0;i<valuesList.size();i++) {
            pricesList.add(Double.parseDouble(valuesList.get(i)));

        }
        System.out.println(titlesList.get(1)+" ; "+pricesList.get(1)+" ; "+urlList.get(1)); //i tu se wyswietlam jeden obiekt, nazwa, cena, url
        return urlList.get(0);  // pierwszy element listy tylko zeby dzialalo - potem przydaloby sie wybierac po nazwie
    }
    public void request_product_soup() throws IOException {
        Elements sth=product_soup.select("class");
       // product_soup=ceneoAPIHandler.send_product_request(find_best_product_ids());
        //System.out.println(sth); //odzczytana strona dla danego produktu

        //tutaj trzeba odczytac dostawce
    }
//nie ogarniam jak sie dostac do ID albo cen
}
