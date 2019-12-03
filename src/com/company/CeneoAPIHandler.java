package com.company;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CeneoAPIHandler {
    String reponse=null;
    String url=null;
    private Connection HTTP_search_response=null;
    private Connection HTTP_product_response=null;
    private org.jsoup.HttpStatusException httpStatusException=null;
    private int searchStatus=0;
    private int productStatus=0;
    private Item item = null;

    public Connection getHTTP_product_response() {
        return HTTP_product_response;
    }

    public Connection getHTTP_search_response() {
        return HTTP_search_response;
    }

    public void setHTTP_product_response(Connection HTTP_product_response) {
        this.HTTP_product_response = HTTP_product_response;
    }

    public void setHTTP_search_response(Connection HTTP_search_response) {
        this.HTTP_search_response = HTTP_search_response;
    }

    public void setHttpStatusException(HttpStatusException httpStatusException) {
        this.httpStatusException = httpStatusException;
    }

    public void setSearchStatus(int searchStatus) {
        this.searchStatus = searchStatus;
    }

    public HttpStatusException getHttpStatusException() {
        return httpStatusException;
    }

    public int getSearchStatus() {
        return getSearchStatus();
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public CeneoAPIHandler(){

    }

    public void setItem(Item item)
    {
        this.item = item;
        this.url = item.getUrl();
    }

    public Item getItem()
    {
        return this.item;
    }


    public Connection send_search_request() throws IOException {

        HTTP_search_response=Jsoup.connect(this.url);
        //HTTP_search_response = connection1.get();
        System.out.println(HTTP_search_response.response().statusCode());
        return HTTP_search_response;
    }



    public Connection send_product_request(String linkhref) throws IOException {
        HTTP_product_response=Jsoup.connect(linkhref);

        //setProductStatus(HTTP_product_response.response().statusCode());
        return HTTP_product_response;
    }




}
