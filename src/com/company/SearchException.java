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


    public void userError(String wiadomosc, String tytul){
        JOptionPane.showMessageDialog(null, wiadomosc, tytul, JOptionPane.INFORMATION_MESSAGE);
    }



}
