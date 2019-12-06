package com.company;
import java.util.Scanner;


public class SetItem {

    private Item swap = new Item();
    private double min_price = 0;
    private double number_double = -99.99;

    public Item Set(boolean first){


        Scanner user_text = new Scanner(System.in);
        String input;
        if(first){
            System.out.println("Podaj nazwę produktu:");

        }else{
            System.out.println("Podaj nazwę kolejnego produktu:");

        }

        input = user_text.nextLine();
        this.swap.setName(input);



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
        this.swap.setMin_reputation(number_double);



        reset();
        System.out.println("Podaj minimalną cenę produktu:");
        do{
            try {
                input = user_text.nextLine();
                number_double = Double.valueOf(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(number_double < 0);
        this.min_price = number_double;
        this.swap.setMin_price(number_double);



        reset();
        System.out.println("Podaj maksymalną cenę produktu [0 - brak ceny maksymalnej]:");
        do{
            try {
                input = user_text.nextLine();
                number_double = Double.valueOf(input);

                if(number_double < this.min_price && number_double != 0){
                    System.out.println("Trzeba było wybrać cenę maksymalną większą niż minimalną -_-");
                    this.swap.setMax_price(0);
                }
                else{
                    this.swap.setMax_price(number_double);
                }

            }
            catch (NumberFormatException e) {
                System.out.println("Podaj liczbę większą lub równą 0:");
            }
        }while(number_double < 0);

        return this.swap;
    }


    private void reset(){
        this.number_double = -99.99;
    }

}
