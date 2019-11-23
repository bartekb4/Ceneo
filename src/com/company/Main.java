package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        CeneoAPIHandler cc=new CeneoAPIHandler();

        DataProcessor dp=new DataProcessor();
        //System.out.println(dp.search_soup);
        dp.find_best_product_ids();
    }
    /*    URL url = new URL("https://www.ceneo.pl/;szukaj-pralka+wsad+7+kg;m1000;n1600;0112-0.htm");
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }*/
    }
