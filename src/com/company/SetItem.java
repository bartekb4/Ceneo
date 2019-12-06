package com.company;
import java.util.Scanner;


public class SetItem {

    private Item swap = new Item();

    public Item Set(boolean first){


        Scanner user_text = new Scanner(System.in);
        String input;
        if(first){
            System.out.println("Podaj nazwę produktu:");

        }else{
            System.out.println("Podaj nazwę kolejnego produktu:");

        }

        input = user_text.nextLine();
        swap.setName(input);


        double min_price  = 404;
        double number_double = -99.99;


        System.out.println("Podaj minimalną reputację sprzedającego:");
        do{
            try {
                input = user_text.nextLine();
                number_double = Double.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj wartość z zakresu [0-100]:");
            }
        }while(number_double > 100 || number_double <0);
        swap.setMin_reputation(number_double);



        System.out.println("Podaj minimalną cenę produktu:");
        do{
            try {
                input = user_text.nextLine();
                number_double = Double.valueOf(input);
                min_price = number_double;
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(number_double < 0);
        swap.setMin_price(number_double);



        System.out.println("Podaj maksymalną cenę produktu [0 - brak ceny maksymalnej]:");
        do{
            try {
                input = user_text.nextLine();
                number_double = Double.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(number_double < 0);
        swap.setMax_price(number_double);

        return swap;
    }



}
