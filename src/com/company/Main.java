package com.company;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {


        CeneoAPIHandler cc=new CeneoAPIHandler();

        DataProcessor dp=new DataProcessor();

        Item pralka = new Item();


        pralka.setName("laptop macbook air");
        pralka.setMin_price(0);
        pralka.setMax_price(0);
//        TODO ustawienie reputacji w zapytaniu
        pralka.setMin_reputation(90);


        //System.out.println(dp.search_soup);
        cc.setItem(pralka); // przedmiot, ktory jest wyszukiwany
        cc.send_search_request();
       // dp.find_best_product_ids();
        dp.request_product_soup(cc); // przekazanie CeneoAPIHandler
        dp.find_best_deal_for_id(cc);

    }

   }
