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
    JSONObject plays = getPlays();

    JSONObject invoice = getInvoices().getJSONObject(0);

    String statement() throws Exception {

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
                    thisAmount = 4000;
                    if (perfAudience > 30) {
                        thisAmount += 1000 * (perfAudience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perfAudience > 20) {
                        thisAmount += 300 * perfAudience;
                        break;
                    }
                default:
                    throw new Exception("unknown type + " + play.get("type").toString());
            }
            // add volume credits
            volumeCredits += Math.max(perfAudience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.get("type")) {
                volumeCredits += Math.floor((double) perfAudience / 5);
            }

            // print line for this order
            result = result + play.get("name") + " : " + currencyFormatter.format(thisAmount / 100) + " ("+perfAudience  + " seats " + ")" + "\n";
            totalAmount += thisAmount;

        }
        result = result + "Amount owed is " + currencyFormatter.format(totalAmount / 100) + "\n";
        result = result + "You earned " + volumeCredits + " credits" + "\n";
        return result;

    }

    JSONObject getPlays() {
        //read plays.json file
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/src/main/resources/plays.json");
        Path playsFilePath = Paths.get(filePath);

        String plays = "";

        try {
            plays = Files.readString(playsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject playsList = new JSONObject(plays);

        return playsList;
    }

    JSONArray getInvoices() {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/src/main/resources/invoices.json");
        Path invoicesFilePath = Paths.get(filePath);

        String invoices = "";

        try {
            invoices = Files.readString(invoicesFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray invoicesList = new JSONArray(invoices);

        return invoicesList;
    }

}
