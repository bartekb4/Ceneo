package com.company;

import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws  ParseException {

        SearchException searchException = new SearchException();

        CeneoAPIHandler cc = new CeneoAPIHandler();
        CeneoAPIHandler cc1=new CeneoAPIHandler();

        DataProcessor dp = new DataProcessor();
        DataProcessor dp2 = new DataProcessor();

        Item pralka = new Item();
        Item suszarka=new Item();
        pralka.setName("acer swift 3");
        suszarka.setName("red dead redeption 2");
 //0565cee8d226e513c73abc146619121e71dc116c
        pralka.setMin_price(0);
        pralka.setMax_price(0);
        pralka.setMin_reputation(90);
        suszarka.setMin_price(0);
        suszarka.setMax_price(0);
        cc.setItem(pralka);
        cc1.setItem(suszarka);


        try {
            dp.find_best_deal_for_id(dp.request_product_soup(cc,cc.getItem().getMin_reputation()),dp2.request_product_soup(cc1,cc1.getItem().getMin_reputation()));
            //dp2.find_best_deal_for_id(dp2.request_product_soup(cc1),cc1.getItem().getMin_reputation());//Troche spaghetti z tym min rep, ale na razie dziala
        } catch (HttpStatusException e) {
            searchException.userError("Co robisz bandyto","Halko");
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        try {

            Throwable throwable = new Throwable("ERROR");
            searchException = new SearchException("Empty Search", throwable);
            searchException.emptyResponse(cc.send_search_request());
        } catch (SearchException e) {
           e.userError("Co ty robisz", "Halko");
        }
        // dp.find_best_product_ids();
        try {
            dp.find_best_deal_for_id(dp.request_product_soup(cc)); // przekazanie CeneoAPIHandler
        } catch (IOException e) {
            System.err.println("No resp");
        } catch (ParseException e) {
            System.err.println("No resp");
        }
    }*/

    }
}
