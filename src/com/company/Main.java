package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        CeneoAPIHandler cc=new CeneoAPIHandler();

        DataProcessor dp=new DataProcessor();
        //System.out.println(dp.search_soup);
        dp.find_best_product_ids();
    }

   }
