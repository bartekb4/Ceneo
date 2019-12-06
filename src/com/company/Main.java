package com.company;

import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws  ParseException {

        SearchException searchException = new SearchException();

        CeneoAPIHandler cc = new CeneoAPIHandler();
        CeneoAPIHandler cc2 = new CeneoAPIHandler();
        CeneoAPIHandler cc3 = new CeneoAPIHandler();

        DataProcessor dp = new DataProcessor();
        DataProcessor dp2 = new DataProcessor();
        DataProcessor dp3 = new DataProcessor();

        System.out.println("\n" + new Timestamp(System.currentTimeMillis()) + "\n");

        Scanner user_text = new Scanner(System.in);

        long time_start = 0;
        int ile_przedmiotow = 404; //ile przedmiotow wyszukujemy

        //Ilosc produktow do porownania
        System.out.println("Ile różnych przedmiotów chcesz wyszukać? [1-3]:");
        String input = "";

        do{
            try {
                input = user_text.nextLine();
                ile_przedmiotow = Integer.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj wartość z zakresu [1-3]:");
            }
        }while(ile_przedmiotow > 4 || ile_przedmiotow <0);


        switch(ile_przedmiotow){
            case 0:

                System.out.println("Dziękujemy za skorzystanie z naszej wyszukiwarki. Może następnym razem uda nam się jakoś pomóc");
                return;

            case 1:

                SetItem Creator = new SetItem();

                cc.setItem(Creator.Set(true));

                System.out.println("\n" + new Timestamp(System.currentTimeMillis()) + "\n");
                System.out.println("Proszę czekać...\n\n");
                time_start = System.currentTimeMillis();

                try {
                    dp.find_best_deal_for_id(dp.request_product_soup(cc));
                } catch (HttpStatusException e) {
                    searchException.userError("Co ty tutaj robisz?","Jak?");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("\n" + "Czas zapytania:" +Float.valueOf((System.currentTimeMillis() - time_start)/1000) + "sek" +"\n");

                break;


            case 2:
                SetItem Creator21 = new SetItem();
                SetItem Creator22 = new SetItem();

                cc.setItem(Creator21.Set(true));
                cc2.setItem(Creator22.Set(false));

                System.out.println("\n" + new Timestamp(System.currentTimeMillis()) + "\n");
                System.out.println("Proszę czekać...\n\n");
                time_start = System.currentTimeMillis();

                try {
                    dp.find_best_deal_for_id(dp.request_product_soup(cc),dp2.request_product_soup(cc2));
                } catch (HttpStatusException e) {
                    searchException.userError("Co ty tutaj robisz?","Jak?");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("\n" + "Czas zapytania:" +Float.valueOf((System.currentTimeMillis() - time_start)/1000) + "sek" +"\n");

                break;


            case 3:

                SetItem Creator31 = new SetItem();
                SetItem Creator32 = new SetItem();
                SetItem Creator33 = new SetItem();

                cc.setItem(Creator31.Set(true));
                cc2.setItem(Creator32.Set(false));
                cc3.setItem(Creator33.Set(false));

                System.out.println("\n" + new Timestamp(System.currentTimeMillis()) + "\n");
                System.out.println("Proszę czekać...\n\n");
                time_start = System.currentTimeMillis();

                try {
                    dp.find_best_deal_for_id(dp.request_product_soup(cc),dp2.request_product_soup(cc2),dp3.request_product_soup(cc3));
                } catch (HttpStatusException e) {
                    searchException.userError("Co ty tutaj robisz?","Jak?");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("\n" + "Czas zapytania:" +Float.valueOf((System.currentTimeMillis() - time_start)/1000) + "sek" +"\n");

                break;
        }

    }
}
