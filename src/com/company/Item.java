package com.company;

import java.sql.Timestamp;

public class Item {

    private String name = "";
    private String url = "https://www.ceneo.pl/";
    private double min_price = 0;
    private double max_price = 0;
    private double min_reputation = 404;
    private int quanity = 1;
    //na stronie ceneo nie znalazlem czasu a nie wiem jak wziac z naglowka http, wiec jest z systemu
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    public Item(){}

    public void setName(String name) {
        this.name = name.replaceAll(" ", "-");;
    }

    public void setMin_reputation(double min_reputation)
    {
        if (min_reputation >= 0 && min_reputation <= 100)
        {
            this.min_reputation = min_reputation;
        }
    }

    public void setMax_price(double max_price)
    {
        if(max_price > 0)
        {
            this.max_price = max_price;
        }
    }

    public void setQuanity(int quanity)
    {
        if (quanity > 1) //Teoretycznie mozna dac 0 + warunek w zapytaniu, ze jak jest 0 to nawet nie wysyla, ale nie wiem czy jest sens
        {
            this.quanity = quanity;
        }
    }

    public void setMin_price(double min_price)
    {
        if(min_price > 0)
        {
            this.min_price = min_price;
        }
    }

    public String getUrl()
    {
        if (!name.isEmpty())
        {
            this.url = this.url + ";szukaj-" + name;
        }
        else
        {
//          TODO komunikat o bledzie jestli nie ma nazwy
            this.url = this.url + ";szukaj-decide";
        }


        if(min_price > 0)
        {
            this.url = this.url + ";m" + min_price;
        }

        if(max_price > 0)
        {
            this.url = this.url + ";n" + max_price;
        }
        //sortowanie po najnizszej cenie ???
        return this.url + ";0112-0";
    }



    public double getMin_price() {
        return min_price;
    }

    public double getMin_reputation() {
        return min_reputation;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getMax_price() {
        return max_price;
    }
    public String getName() {
        return name;
    }

    public int getQuanity() {
        return quanity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}