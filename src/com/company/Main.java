package com.company;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        String url="https://www.ceneo.pl/;szukaj-pralka+wsad+7+kg;m1000;n1600;0112-0.htm";

        CeneoAPIHandler cc=new CeneoAPIHandler();

        DataProcessor dp=new DataProcessor();
        //System.out.println(dp.search_soup);
        cc.send_search_request(url);
       // dp.find_best_product_ids();
        dp.request_product_soup();
    }

   }
