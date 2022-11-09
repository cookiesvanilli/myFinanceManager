package org.example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientRequest {
    private final String title;
    private final Date date;
    private final long sum;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public ClientRequest(String title, long sum) {
        this.title = title;
        this.date = new Date();
        this.sum = sum;
    }

    public String stringJSON() {
        return "{\"title\": " + "\"" + title + "\", \"date\": " + "\"" + dateFormat.format(date) + "\", \"sum\": " + sum + "}";
    }

    public void requestJSON(String s) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("request.json")) {
            out.println(s);
        }
    }
}
