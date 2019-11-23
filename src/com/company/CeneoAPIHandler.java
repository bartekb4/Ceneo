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
    String url="https://www.ceneo.pl/;szukaj-pralka+wsad+7+kg;m1000;n1600;0112-0.htm";
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

    public Document send_search_request(String url) throws IOException {

        HTTP_search_response = Jsoup.connect(url).get();

       return HTTP_search_response;
    }
    public Document send_product_request(String linkhref) throws IOException {
        String url2 = "https://www.ceneo.pl/55280858;0284-0.htm";
        HTTP_product_response = Jsoup.connect(url2).get();
        return HTTP_product_response;
    }




}
