package com.company;

import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws  ParseException {

        SearchException searchException = new SearchException();

        CeneoAPIHandler cc = new CeneoAPIHandler();

        DataProcessor dp = new DataProcessor();

        Item pralka = new Item();





        pralka.setName("laptop");
 //0565cee8d226e513c73abc146619121e71dc116c
        pralka.setMin_price(0);
        pralka.setMax_price(0);
        pralka.setMin_reputation(90);
        cc.setItem(pralka);


        try {
            dp.find_best_deal_for_id(dp.request_product_soup(cc),cc.getItem().getMin_reputation()); //Troche spaghetti z tym min rep, ale na razie dziala
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
