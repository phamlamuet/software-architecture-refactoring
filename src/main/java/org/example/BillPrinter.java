package org.example;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;

public class BillPrinter {
    JSONObject plays;
    JSONObject invoice;

    BillPrinter(String playsFile, String invoiceFile) {
        this.plays = getPlays(playsFile);
        this.invoice = getInvoice(invoiceFile);
    }

    String statement() {

        double totalAmount = 0;
        double volumeCredits = 0;
        String result = "Statement for " + invoice.get("customer") + "\n";
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        currencyFormatter.setMaximumFractionDigits(2);

        JSONArray performances = (JSONArray) invoice.get("performances");

        for (int i = 0; i < performances.length(); i++) {
            JSONObject perf = (JSONObject) performances.get(i);
            JSONObject play = (JSONObject) plays.get(((String) perf.get("playID")).toLowerCase());

            double thisAmount = 0;

            int perfAudience = Integer.parseInt(perf.get("audience").toString());
            switch (play.get("type").toString()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perfAudience > 30) {
                        thisAmount += 1000 * (perfAudience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perfAudience > 20) {
                        thisAmount += 10000 + 500 * (perfAudience - 20);
                    }
                    thisAmount += 300 * perfAudience;
                    break;
                default:
                    throw new RuntimeException("unknown type + " + play.get("type").toString());
            }
            // add volume credits
            volumeCredits += Math.max(perfAudience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(play.get("type"))) {
                volumeCredits += Math.floor((double) perfAudience / 5);
            }

            // print line for this order
            result = result + play.get("name") + " : " + currencyFormatter.format(thisAmount / 100) + " (" + perfAudience + " seats " + ")" + "\n";
            totalAmount += thisAmount;

        }
        result = result + "Amount owed is " + currencyFormatter.format(totalAmount / 100) + "\n";
        result = result + "You earned " + volumeCredits + " credits" + "\n";
        return result;

    }

    JSONObject getPlays(String playsFile) {
        //read plays.json file
        String plays = GetDataSource(playsFile);

        JSONObject playsList = new JSONObject(plays);

        return playsList;
    }

    private String GetDataSource(String sourceFile) {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/src/main/resources/" + sourceFile);
        Path playsFilePath = Paths.get(filePath);

        String data = "";

        try {
            data = Files.readString(playsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    JSONObject getInvoice(String invoiceFile) {
        String invoices = GetDataSource(invoiceFile);
        JSONArray invoicesList = new JSONArray(invoices);
        JSONObject invoice = invoicesList.getJSONObject(0);

        return invoice;
    }


}
