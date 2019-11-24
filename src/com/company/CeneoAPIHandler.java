package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CeneoAPIHandler {
    String reponse=null;
//    String url="https://www.ceneo.pl/;szukaj-iphone";
    String url=null;
    private Document HTTP_search_response=null;
    private Document HTTP_product_response=null;

    public Document getHTTP_product_response() {
        return HTTP_product_response;
    }

    public Document getHTTP_search_response() {
        return HTTP_search_response;
    }

    public void setHTTP_product_response(Document HTTP_product_response) {
        this.HTTP_product_response = HTTP_product_response;
    }

    public void setHTTP_search_response(Document HTTP_search_response) {
        this.HTTP_search_response = HTTP_search_response;
    }

    public CeneoAPIHandler(){

    }

//    TODO: settery dla dodatkowych argumento -  cena, reputacja itp.
    public void set_name(String name)
    {
        this.url = "https://www.ceneo.pl/;szukaj-"+name;
    }

//    public Document send_search_request(String url) throws IOException {
//
//        HTTP_search_response = Jsoup.connect(url).get();
//
//       return HTTP_search_response;
//    }

    public Document send_search_request() throws IOException {

        HTTP_search_response = Jsoup.connect(this.url).get();

       return HTTP_search_response;
    }



    public Document send_product_request(String linkhref) throws IOException { ;
        HTTP_product_response = Jsoup.connect(linkhref).get();
        return HTTP_product_response;
    }




}
