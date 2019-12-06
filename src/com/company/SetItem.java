package com.company;
import java.util.Scanner;


public class SetItem {

    public Item Set(Item item, boolean end){

        Scanner user_text = new Scanner(System.in);
        String input;
        if(end){
            System.out.println("Podaj nazwę produktu:");

        }else{
            System.out.println("Podaj nazwę kolejnego produktu:");

        }

        input = user_text.nextLine();
        item.setName(input);


        int ile_int = 404;
        double ile_double = -99.99;


        System.out.println("Podaj minimalną reputację produktu:");
        do{
            try {
                input = user_text.nextLine();
                ile_int = Integer.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj wartość z zakresu [0-100]:");
            }
        }while(ile_int > 100 || ile_int <0);
        item.setMin_reputation(ile_int);



        System.out.println("Podaj minimalną cenę produktu:");
        do{
            try {
                input = user_text.nextLine();
                ile_double = Double.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(ile_double < 0);
        item.setMin_price(ile_double);



        System.out.println("Podaj maksymalną cenę produktu:");
        do{
            try {
                input = user_text.nextLine();
                ile_double = Double.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(ile_double < 0);
        item.setMax_price(ile_double);

        return item;
    }



}
