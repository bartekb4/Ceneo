package com.company;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;

public class SearchException extends RuntimeException {
    private String string;
    private Throwable throwable;
    private URL url;
    private org.jsoup.nodes.Document document;
    private Connection connection;
    public SearchException(String empty_search, Throwable throwable) {
        super(empty_search,throwable);
    }
    public SearchException(){
        super();
    }

    public SearchException(Throwable throwable) {
    }

    public void emptyDocument(org.jsoup.nodes.Document document){

        this.document=document;
        this.throwable=new Throwable("Error, no response");
        if(document.text()==null){
            throw new SearchException(throwable);
        }
    }
    public void badStatus(Connection connection) throws IOException {
        this.connection=connection;
        if (connection.response().statusCode()!=200) {
            throw new SearchException(throwable);
        }

    }
    public void userError(String wiadomosc, String tytul){
        JOptionPane.showMessageDialog(null, wiadomosc, tytul, JOptionPane.INFORMATION_MESSAGE);
    }



}
