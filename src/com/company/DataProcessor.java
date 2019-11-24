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
    private CeneoAPIHandler ceneoAPIHandler = null; //Tylko inicjacja, w metodach dodalem przekazanie obiektu
    private Document search_soup;
    private Document product_soup;
    private List <String> titlesList=new ArrayList<>();
    private List<String> valuesList=new ArrayList<String>();
    private List <String> urlList=new ArrayList<>();
    private List<Double> pricesList=new ArrayList<>();
    private List<Double> starList=new ArrayList<>();
    private List<String> opinionList=new ArrayList<>();
    private String value;
    private int dotIndex;

    public String find_best_product_ids(CeneoAPIHandler ceneoAPIHandler) throws IOException, ParseException { //tu sie dzieje scraping
        search_soup = ceneoAPIHandler.send_search_request();

        Elements titles=search_soup.getElementsByClass
                ("js_seoUrl go-to-product btn btn-primary btn-cat btn-cta js_force-conv js_clickHash");
        for (Element t:titles) {
            titlesList.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek
        }
        System.out.println("Nazwy"+titlesList);

        for (Element t:titles) {
            urlList.add(t.attr("href")); //dodaje do listy wszysytkie linki pralek
        }


        for(int i=0;i<titlesList.size();i++) {

            value = search_soup.getElementsByAttributeValue("title", titlesList.get(i)).select("span.value,span.penny").text().replaceAll("\\s","").replaceAll(",",".");

            //obejscie wspanialego Ceneo, ktore wysyla wartosci po 2 razy
            dotIndex = value.indexOf(".");
            if (dotIndex != -1)
            {
                value = value.substring(0, dotIndex+3);
            }
            valuesList.add(value);

        }

        System.out.println("Linki"+urlList); //Tu sie wyswietlaja koncowki urli do prodouktu\
        System.out.println("Ceny"+valuesList);//tu sie wyswietlaja ceny\

        for(int i=0;i<valuesList.size();i++) {
            pricesList.add(Double.parseDouble(valuesList.get(i)));
        }

//        TODO wyrzuca wyjatki kiedy nie znaleziono produktow
        System.out.println("Jakis obiekcik "+titlesList.get(0)+" ; "+pricesList.get(0)+" ; "+urlList.get(0)); //i tu se wyswietlam jeden obiekt, nazwa, cena, url
        return urlList.get(1);  // pierwszy element listy tylko zeby dzialalo - potem przydaloby sie wybierac po nazwie
    }


    //        TODO wyrzuca wyjatki kiedy nie znaleziono produktow
    public void request_product_soup(CeneoAPIHandler ceneoAPIHandler) throws IOException, ParseException {
        product_soup=ceneoAPIHandler.send_product_request("https://www.ceneo.pl"+find_best_product_ids(ceneoAPIHandler)+";0284-0.htm");
        Elements stars=product_soup.select("span.score-marker.score-marker--s");
        for (Element t:stars) {
            starList.add(Double.valueOf(t.attr("style").
                    replaceAll("width: ","").replaceAll("%;",""))); //dodaje do listy wszysytkie gwiazdki jako numer
                        //100 = 5 stars, 90 = 4.5 stars
        }
        System.out.println("gwiazdki sprzedawcow "+starList); //lista opinii dla dostawcow
        Elements opinions=product_soup.select("span.dotted-link.js_mini-shop-info.js_no-conv");
        for (Element t:opinions) {
            opinionList.add(t.getElementsByAttribute("data-mini-shop-info-url").text().replace(" opinii","").replace(" opinie","").replace(" opinia",""));

        }
        System.out.println("Liczba opinii sprzedawcow"+opinionList);


        //tutaj trzeba odczytac dostawce
    }
}
