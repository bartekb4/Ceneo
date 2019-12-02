package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DataProcessor {
    private CeneoAPIHandler ceneoAPIHandler = null; //Tylko inicjacja, w metodach dodalem przekazanie obiektu
    private Document search_soup;
    private Document product_soup;
    private List <String> titlesList=new ArrayList<>();
    private List<String> valuesList=new ArrayList<String>();
    private List<Double> pricesList=new ArrayList<>();
    private List <String> urlList=new ArrayList<>();
    private List <String> shopLinkList=new ArrayList<>();
    private List<Double> pricesShopList=new ArrayList<>();
    private List<Double> starList=new ArrayList<>();
    private List<Double> opinionList=new ArrayList<>();
    private List<Integer> shopNumbers=new ArrayList<>();
    private List<Integer> potentialPrices=new ArrayList<>();
    private List<Integer> potentialSellers=new ArrayList<>();
    private ArrayList<List<Object>> endResults=new ArrayList<>();
    private String value;
    private int dotIndex;
    Item item=new Item();
    SearchException searchException=new SearchException();

    public String find_best_product_ids(CeneoAPIHandler ceneoAPIHandler) throws IOException, ParseException { //tu sie dzieje scraping

        search_soup = ceneoAPIHandler.send_search_request().get();


        Elements titles=search_soup.getElementsByClass
                ("js_seoUrl go-to-product btn btn-primary btn-cat btn-cta js_force-conv js_clickHash"); //ta klasa oznacza ze guzik to porównaj ceny?
        if(!titles.isEmpty()) {
            for (Element t : titles) {
                titlesList.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek
            }
        }
        else
            System.out.println("Empty");

        System.out.println("Nazwy"+titlesList);

        for (Element t:titles) {
            urlList.add(t.attr("href")); //dodaje do listy wszysytkie linki pralek
        }
/*
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

        tych cen w zasadzie nie trzeba


//        System.out.println("Ceny"+valuesList);//tu sie wyswietlaja ceny\

        for(int i=0;i<valuesList.size();i++) {
            pricesList.add(Double.parseDouble(valuesList.get(i)));
        }

 */
        System.out.println("Linki"+urlList); //Tu sie wyswietlaja koncowki urli do prodouktu\
        Elements shopNumber = search_soup.select("span.shop-numb");
        for (Element t:shopNumber) {
            shopNumbers.add(Integer.valueOf(String.valueOf(t).replaceAll("<span class=\"shop-numb\"></span>","0").
                    replaceAll("<span class=\"shop-numb\">","").replaceAll("</span>","").
                    replaceAll("w ","").replaceAll(" sklepach","").replaceAll(" sklepie","")));
            shopNumbers.removeAll(Collections.singleton(0));
        }
        System.out.println("Sklepy " + shopNumbers);  //Liczba dostepnych sklepow dla danej oferty
        System.out.println(shopNumbers);


//        TODO wyrzuca wyjatki kiedy nie znaleziono produktow

        String linkhref =null;
        for(int i=0;i<shopNumbers.size();i++) {
            if(shopNumbers.get(i)>5){
                potentialPrices.add(i);
            }
        }

        linkhref=urlList.get(potentialPrices.get(0));//wybieram najtansza oferte z palca
        System.out.println(linkhref);

        return linkhref;




    }


    //        TODO wyrzuca wyjatki kiedy nie znaleziono produktow
    public Document request_product_soup(CeneoAPIHandler ceneoAPIHandler) throws ParseException, IOException {

        product_soup= ceneoAPIHandler.send_product_request("https://www.ceneo.pl"+find_best_product_ids(ceneoAPIHandler)+";0284-0.htm").get();
        return product_soup;

    }
    public ArrayList find_best_deal_for_id(Document product_soup){
        Elements stars=product_soup.select("span.stars.js_mini-shop-info.js_no-conv");
        for (Element t:stars) {
            starList.add(Double.valueOf(t.select("span.score-marker.score-marker--s").attr("style").
                    replaceAll("width: ","").replaceAll("%;",""))); //dodaje do listy wszysytkie gwiazdki jako numer
            //100 = 5 stars, 90 = 4.5 stars
        }

        System.out.println("gwiazdki sprzedawcow "+starList.size()); //lista opinii dla dostawcow
        Elements opinions=product_soup.select("span.dotted-link.js_mini-shop-info.js_no-conv");
        for (Element t:opinions) {
            opinionList.add(Double.valueOf(t.getElementsByAttribute("data-mini-shop-info-url").text().replace(" opinii","").replace(" opinie","").replace(" opinia","")));

        }

        System.out.println("Liczba opinii sprzedawcow"+opinionList);

        Elements prices = product_soup.select("a.product-price.go-to-shop"); //wszystkie ceny dla danego produktu
        for (Element t:prices) {
            pricesShopList.add(Double.valueOf(t.select("span.value,span.penny").text().replaceAll("\\s","").replaceAll(",",".")));
        }

        Elements shoplink=product_soup.select("tr.product-offer.clickable-offer.js_offer-container-click.js_product-offer"); //linki do konkretnej oferty
        for (Element t:shoplink) {
            shopLinkList.add(t.attr("data-click-url"));
        }

        System.out.println("Linki do sklepow: " + shopLinkList.size());
        System.out.println("Cenki w sklepie: "+pricesShopList);



        double minRep=80;
        double minOpin=20; //tu wpisane z palca

        for (int i=0;i<opinionList.size();i++){    //wybieram tak na chama potencjalnych sprzedawcow i daje ich do listy
            if(starList.get(i)>minRep && opinionList.get(i)>minOpin){
                potentialSellers.add(i);
            }
        }
        System.out.println("seler" + potentialSellers);

//Teoretycznie do przerzucenia do Klasy Output ale nie mam pomyslu jak
        System.out.println("\n \n \n \nWyniki: ");

        System.out.println("Dzien dobry, proponuje: \n");

        for(int i=0;i<3;i++) {
            System.out.println("Produkt: "+ (i+1) );
            System.out.println("\nTutaj cenka:");
            System.out.println(pricesShopList.get(potentialSellers.get(i)));   //3 najlepsze ceny - to wszystko dziala na ID w Listach, raczej glupio, ale dziala
            System.out.println("Tutaj link: ");
            System.out.println("http://www.ceneo.pl"+shopLinkList.get(potentialSellers.get(i))+"\n");  //linki
        }
        endResults.add(Collections.singletonList(pricesShopList));
        endResults.add(Collections.singletonList(potentialSellers));
        endResults.add(Collections.singletonList(shopLinkList));
        return endResults;
    }
}
