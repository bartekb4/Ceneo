package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CeneoAPIHandler {
    String reponse=null;
    String url="https://www.ceneo.pl/;szukaj-pralka+wsad+7+kg;m1000;n1600;0112-0.htm";
    private Document HTTP_search_response=null;

    public CeneoAPIHandler(){

    }

    public Document send_search_request(String url) throws IOException {

        HTTP_search_response = Jsoup.connect(url).get();

        return HTTP_search_response;
    }




}
