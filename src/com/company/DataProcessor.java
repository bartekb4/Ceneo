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
    private List<String> titlesList = new ArrayList<>();
    private List<String> valuesList = new ArrayList<String>();
    private List<Double> pricesList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private List<String> shopLinkList = new ArrayList<>();
    private List<String> pricesShopList = new ArrayList<>();
    private List<Double> pricesShopList2 = new ArrayList<>();
    private List<Double> starList = new ArrayList<>();
    private List<Double> opinionList = new ArrayList<>();
    private List<String> priceWithDelivMod2 = new ArrayList<String>();
    private List<Integer> shopNumbers = new ArrayList<>();
    private List<String> delieveryCostsMod = new ArrayList<>();
    private List<Integer> potentialPrices = new ArrayList<>();
    private List<Integer> potentialSellers = new ArrayList<>();
    private List<String> endResults = new ArrayList<>();
    private List<String> shopNames = new ArrayList<>();
    private List<String> delieveryCosts = new ArrayList<String>();
    private List<String> priceWithDeliv = new ArrayList<>();
    private List<Double> priceWithDelivMod = new ArrayList<>();

    private String value;
    private int dotIndex;
    //    Item item=new Item();  //przekazywane w CeneoApiHandler
    SearchException searchException = new SearchException();

    public String find_best_product_ids(CeneoAPIHandler ceneoAPIHandler) throws IOException, ParseException { //tu sie dzieje scraping

        search_soup = ceneoAPIHandler.send_search_request().get();
        Elements empty = search_soup.select("div.alert");
        String emptysearch = String.valueOf(empty.select("h3"));
        if (emptysearch.contains("Niestety")) {
            System.out.println("pusto");
        } else {
            Elements titles = search_soup.getElementsByClass
                    ("js_seoUrl go-to-product btn btn-primary btn-cat btn-cta js_force-conv js_clickHash"); //ta klasa oznacza ze guzik to porównaj ceny?
            if (!titles.isEmpty()) {
                for (Element t : titles) {
                    titlesList.add(t.attr("title")); //dodaje do listy wszysytkie nazwy pralek
                }
            } else
                System.out.println("Empty");

            //System.out.println("Nazwy"+titlesList);

            for (Element t : titles) {
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
            // System.out.println("Linki"+urlList); //Tu sie wyswietlaja koncowki urli do prodouktu\
            Elements shopNumber = search_soup.select("span.shop-numb");
            for (Element t : shopNumber) {
                shopNumbers.add(Integer.valueOf(String.valueOf(t).replaceAll("<span class=\"shop-numb\"></span>", "0").
                        replaceAll("<span class=\"shop-numb\">", "").replaceAll("</span>", "").
                        replaceAll("w ", "").replaceAll(" sklepach", "").replaceAll(" sklepie", "")));
                shopNumbers.removeAll(Collections.singleton(0));
            }
            //System.out.println("Sklepy " + shopNumbers);  //Liczba dostepnych sklepow dla danej oferty

            int mostShops = Collections.max(shopNumbers);
            //System.out.println(shopNumbers.indexOf(mostShops));

//        TODO wyrzuca wyjatki kiedy nie znaleziono produktow

            String linkhref = null;
        /*
        for(int i=0;i<shopNumbers.size();i++) {
            if(shopNumbers.get(i)>5){
                potentialPrices.add(i);
            }
        }
*/
            linkhref = urlList.get(shopNumbers.indexOf(mostShops));//wybieram oferte z najwieksza liczba sklepow
            System.out.println(linkhref);

            return linkhref;
        }
        return null;
    }


    //        TODO wyrzuca wyjatki kiedy nie znaleziono produktow
    public List<String> request_product_soup(CeneoAPIHandler ceneoAPIHandler, double minRep) throws ParseException, IOException {
        if(find_best_product_ids(ceneoAPIHandler)==null){
            System.out.println("tez pusto");
        }
        else {
            product_soup = ceneoAPIHandler.send_product_request("https://www.ceneo.pl" + find_best_product_ids(ceneoAPIHandler) + ";0284-0.htm").get();
            //return product_soup;
            Elements stars = product_soup.select("span.stars.js_mini-shop-info.js_no-conv");
            for (Element t : stars) {
                starList.add(Double.valueOf(t.select("span.score-marker.score-marker--s").attr("style").
                        replaceAll("width: ", "").replaceAll("%;", ""))); //dodaje do listy wszysytkie gwiazdki jako numer
                //100 = 5 stars, 90 = 4.5 stars
            }

            System.out.println("gwiazdki sprzedawcow " + starList); //lista opinii dla dostawcow
            Elements opinions = product_soup.select("span.dotted-link.js_mini-shop-info.js_no-conv");
            for (Element t : opinions) {
                opinionList.add(Double.valueOf(t.getElementsByAttribute("data-mini-shop-info-url").text().replace(" opinii", "").replace(" opinie", "").replace(" opinia", "")));
            }

            System.out.println("Liczba opinii sprzedawcow" + opinionList);

            Elements prices = product_soup.select("a.product-price.go-to-shop"); //wszystkie ceny dla danego produktu
            for (Element t : prices) {
                pricesShopList.add((t.select("span.value,span.penny").text().replaceAll("\\s", "").replaceAll(",", ".")));
            }

            Elements shoplink = product_soup.select("tr.product-offer.clickable-offer.js_offer-container-click.js_product-offer"); //linki do konkretnej oferty
            for (Element t : shoplink) {
                shopLinkList.add(t.attr("data-click-url"));
                shopNames.add(t.attr("data-shopurl"));
                //delieveryCosts.add(t.select("td.cell-price").attr("data-offset-x"));
            }

            Elements deliveryCost = product_soup.select("td.cell-price"); //koszty dostawy=z wysylka X albo darmowa wysylka
            for (Element t : deliveryCost) {
                delieveryCosts.add((t.getElementsByClass("product-delivery-info js_deliveryInfo").text().toString()));
            }
            //


            double minOpin = 20; //tu wpisane z palca

            for (int i = 0; i < shopNames.size(); i++) {    //wybieram tak na chama potencjalnych sprzedawcow i daje ich do listy
                if (starList.get(i) > minRep && opinionList.get(i) > minOpin) {
                    shopNames.remove(i);
                    pricesShopList.remove(i);
                    delieveryCosts.remove(i);
                    shopLinkList.remove(i);
                    //przycinam listy sklepow,cen i kosztow z dostawy zeby dzialac tylko na tych co nas interesuja
                }
            }
            System.out.println("Cenki w sklepie: " + pricesShopList);
            System.out.println("Nazwy sklepow: " + shopNames);
            System.out.println("Dostawa: " + delieveryCosts);
            System.out.println("Linki do sklepow: " + shopLinkList.size());

            //System.out.println("seler" + potentialSellers);

            // trzeba dodac warunek zeby oferty co maja "szczegoly dostawy" usunąć"
            for (int i = 0; i < delieveryCosts.size(); i++) {

                if (delieveryCosts.get(i).matches("Darmowa wysyłka")) {
                    priceWithDeliv.add(i, pricesShopList.get(i));
                } else {                                                    //lacze w jedna liste ofery z kosztem wysylki i z darmowa
                    priceWithDeliv.add(i, delieveryCosts.get(i));
                }

                //tu jest jazda xd
                //ta lista jest pomocnicza do przekonwertowania na liczby tesktu z wysylka x zl
                priceWithDelivMod.add(Double.valueOf(priceWithDeliv.get(i).replaceAll("Z wysyłką od ", "").replaceAll(" zł", "").replaceAll(",", ".")));
                //ta jest do przekonwertowania wszystkich cen na double
                pricesShopList2.add(Double.valueOf(pricesShopList.get(i)));
                //a tutaj wyliczam koszt wysylki odejmujac koszt przedmiotu z wysylka i koszt wysylki
                priceWithDelivMod2.add(i, String.valueOf(Math.round(priceWithDelivMod.get(i) - pricesShopList2.get(i))));
            }
            //ceny w liczbach
            System.out.println("Koszty dostawy  " + priceWithDelivMod2);

            endResults.addAll(shopNames);
            endResults.addAll(priceWithDelivMod2);   //Do tej listy list xd jest dodane nazwy sklepow, koszt bez dostawy,
            endResults.addAll(pricesShopList);
            endResults.addAll(shopLinkList);
            //i koszt dostawy, to wszystko jest po to zeby sie dalo to jakos przekazac
            System.out.println(endResults);      //do nastepnej funkcji, i w niej porownywac dla kilku obiektow szukanych


            System.out.println(endResults);
            return endResults;
        }
        return null;
//Teoretycznie do przerzucenia do Klasy Output ale nie mam pomyslu jak
     /*   System.out.println("\n \n \n \nWyniki: ");

        System.out.println("Dzien dobry, proponuje: \n");

        for (int i = 0; i < 2; i++) {
            System.out.println("Produkt: " + (i + 1));
            System.out.println("\nTutaj cenka:");
            System.out.println(priceWithDelivMod.get(i));   //3 najlepsze ceny - to wszystko dziala na ID w Listach, raczej glupio, ale dziala
            System.out.println("Tutaj link: ");
            System.out.println("http://www.ceneo.pl" + shopLinkList.get(i) + "\n");  //linki
        }

    }
    */
    }

    public List<String> find_best_deal_for_id(List<String> endResults1, List<String> endResults2, List<String> endResults3) {



        List<String> equalShops2 = new ArrayList<>();

        List<String> shopNamesSublist1 = new ArrayList<>();   //sublisty do rozwalenia endresults na kilka kawalkow
        List<String> shopNamesSublist2 = new ArrayList<>();
        List<String> shopNamesSublist3 = new ArrayList<>();
        List<String> delcostSublist1 = new ArrayList<>();
        List<String> delcostSublist2 = new ArrayList<>();
        List<String> delcostSublist3 = new ArrayList<>();
        List<String> priceSublist1 = new ArrayList<>();
        List<String> priceSublist2 = new ArrayList<>();
        List<String> priceSublist3 = new ArrayList<>();
        List<String> linkSublist1 = new ArrayList<>();
        List<String> linkSublist2 = new ArrayList<>();
        List<String> linkSublist3 = new ArrayList<>();

        //To ponizej to chyba jest glupie bardzo ale dziala, po prostu z tej duzej listy wybieram mniejsze
        //one z warunkow wczesniejszych powinny byc takie same wiec to raczej dziala
        shopNamesSublist1.addAll(endResults1.subList(0, endResults1.size() / 4));
        shopNamesSublist2.addAll(endResults2.subList(0, endResults2.size() / 4));
        shopNamesSublist3.addAll(endResults3.subList(0, endResults3.size() / 4));

        delcostSublist1.addAll(endResults1.subList((endResults1.size() / 4), 2*endResults1.size() / 4));
        delcostSublist2.addAll(endResults2.subList((endResults2.size() / 4), 2*endResults2.size() / 4));
        delcostSublist3.addAll(endResults3.subList((endResults3.size() / 4), 2*endResults3.size() / 4));

        priceSublist1.addAll(endResults1.subList((2*endResults1.size() / 4), (3*endResults1.size() / 4)));
        priceSublist2.addAll(endResults2.subList((2*endResults2.size() / 4), (3*endResults2.size() / 4)));
        priceSublist3.addAll(endResults3.subList((2*endResults3.size() / 4), (3*endResults3.size() / 4)));

        linkSublist1.addAll(endResults1.subList((3*endResults1.size() / 4), endResults1.size() / 4));
        linkSublist2.addAll(endResults2.subList((3*endResults2.size() / 4), endResults2.size() / 4));
        linkSublist3.addAll(endResults3.subList((3*endResults3.size() / 4), endResults3.size() / 4));

        System.out.println(shopNamesSublist1);
        System.out.println(shopNamesSublist2);      //
        System.out.println(shopNamesSublist3);
        System.out.println(delcostSublist1);
        System.out.println(delcostSublist2);
        System.out.println(delcostSublist3);
        System.out.println(priceSublist1);
        System.out.println(priceSublist2);
        System.out.println(priceSublist3);
        int biggest_result = Math.max(Math.max(shopNamesSublist1.size(), shopNamesSublist2.size()), shopNamesSublist3.size());
        System.out.println(biggest_result);

//Sprawdzam powtarzajace sie sklepy
        for (String name : shopNamesSublist2) {
            boolean in2 = shopNamesSublist1.contains(name);
            boolean in3 = shopNamesSublist3.contains(name);
            if (in2 && in3)
                equalShops2.add(name);
            else if (!in2 && in3)
                equalShops2.add(name);
            else if (in2 && !in3)
                equalShops2.add(name);
        }

            //System.out.println("Powtarzające sie sklepy " + equalShops);
            System.out.println("Powtarzające sie sklepy " + equalShops2);


            List<Integer> shop_index1 = new ArrayList<>();
            List<Integer> shop_index2 = new ArrayList<>();
            List<Integer> shop_index3 = new ArrayList<>();


            //Sprawdzam indexy sklepow powtarzajacych sie w tablicach
            if(equalShops2.size()!=0){
                for (String name : equalShops2) {
                    if(shopNamesSublist1.contains(name)){
                        shop_index1.add(shopNamesSublist1.indexOf(name));
                    }
                    if(shopNamesSublist2.contains(name)){
                        shop_index2.add(shopNamesSublist2.indexOf(name));
                    }
                    if(shopNamesSublist3.contains(name)){
                        shop_index3.add(shopNamesSublist3.indexOf(name));
                    }
                }
            }
            List <Double> final_price1=new ArrayList<>();
            List <Double> final_price2=new ArrayList<>();
            List <Double> final_price3=new ArrayList<>();
            List <Double> final_priceMinDel1=new ArrayList<>();
            List <Double> final_priceMinDel2=new ArrayList<>();
            List <Double> final_priceMinDel3=new ArrayList<>();

            System.out.println(shop_index1);
            System.out.println(shop_index2);
            System.out.println(shop_index3);
            //Dodaje koszt wysylki do kosztu produktu
            for(int i=0;i<priceSublist1.size();i++){
                final_price1.add(Double.valueOf(priceSublist1.get(i))+Double.valueOf(delcostSublist1.get(i)));
            }
            for(int i=0;i<priceSublist2.size();i++){
                final_price2.add(Double.valueOf(priceSublist2.get(i))+Double.valueOf(delcostSublist2.get(i)));
            }
            for(int i=0;i<priceSublist3.size();i++){
                final_price3.add(Double.valueOf(priceSublist3.get(i))+Double.valueOf(delcostSublist3.get(i)));
            }
            System.out.println(final_price1.size());
            System.out.println(final_price2.size());
            System.out.println(final_price3.size());

            //Sortuje:
            Collections.sort(final_price1);
            Collections.sort(final_price2);
            Collections.sort(final_price3);
            //Pierwsza opcja ze nie mamy powtarzajacych sie sklepow
            if (equalShops2.isEmpty()) {
                System.out.println(final_price1.get(0)+final_price2.get(0)+final_price3.get(0));//po prostu najtansze
            }

            //Tutaj troche kosmos, biore sobie ceny dla tych sklepow ktore sie powtarzaja i wrzucam je do nowej listy
            else if(!equalShops2.isEmpty()) {

                if(shop_index1.isEmpty()) { //jezeli sie nie powtarza sklep to dodaje najtansza oferte
                    System.out.println(shop_index1);
                    final_priceMinDel1.add(final_price1.get(0));
                }
                else {
                    for (int i = 0; i < shop_index1.size(); i++) {
                        System.out.println(shop_index1); 
                        final_priceMinDel1.add(final_price1.get(shop_index1.get(i))); //tu dodaje ceny z powtarzajcych sie sklepow do listy
                    }
                }
                if(shop_index2.isEmpty()) {
                    final_priceMinDel2.add(final_price2.get(0));
                }
                else {
                    for (int i = 0; i < shop_index2.size(); i++) {
                        System.out.println(shop_index2);
                        final_priceMinDel2.add(final_price2.get(shop_index2.get(i)));
                    }
                }
                if (shop_index3.isEmpty()) {
                    final_priceMinDel3.add(final_price3.get(0));
                }
                else {
                    for (int i = 0; i < shop_index3.size(); i++) {
                        System.out.println(shop_index3);
                        final_priceMinDel3.add(final_price3.get(shop_index3.get(i)));
                    }
                }
            }

            /*Collections.sort(final_priceMinDel1);
            Collections.sort(final_priceMinDel2);
            Collections.sort(final_priceMinDel3);
            */
            System.out.println(final_priceMinDel1);
            System.out.println(final_priceMinDel2);
            System.out.println(final_priceMinDel3);
            //Tutaj tylko do testowania tak to napislem, trzeba owarunkowac
            double finMinDelivPrice=final_priceMinDel1.get(0)+final_priceMinDel2.get(0)+final_price3.get(0);
            double finMinPrice=final_price1.get(0)+final_price2.get(0)+final_price3.get(0);
            double finMinPrice=final_price1.get(0)+final_price2.get(0)+final_price3.get(0);            double result=Math.min(finMinDelivPrice,finMinPrice);
            System.out.println(finMinPrice);
            // zapomnialem, trzeba odjac dostawe od finMinDelivPrice, ale to powinno byc easy
            //Trzeba wrocic do linku, ew.nazwy? dla najnizszej ceny
            System.out.println("\n \n \n \n Miejmy nadzieje ze to jest poprawny wynik:  "+result);
            return equalShops2;
        }
    }

