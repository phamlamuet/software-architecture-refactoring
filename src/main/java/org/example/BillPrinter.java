package org.example;

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
        JSONArray performances = (JSONArray) invoice.get("performances");

        for (int i = 0; i < performances.length(); i++) {
            JSONObject perf = (JSONObject) performances.get(i);
            volumeCredits += volumeCreditsFor(perf);
            totalAmount += amountFor(perf);
            result = result + playFor(perf).get("name") + " : " + formatToUSD(amountFor(perf) / 100) + " (" + audienceFor(perf) + " seats " + ")" + "\n";
        }
        result = result + "Amount owed is " + formatToUSD(totalAmount / 100) + "\n";
        result = result + "You earned " + volumeCredits + " credits" + "\n";
        return result;
    }

    String formatToUSD(double number) {
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        currencyFormatter.setMaximumFractionDigits(2);
        return currencyFormatter.format(number);
    }

    double volumeCreditsFor(JSONObject perf) {
        double result = 0;
        result += Math.max(audienceFor(perf) - 30, 0);
        if ("comedy".equals(playFor(perf).get("type"))) {
            result += Math.floor((double) audienceFor(perf) / 5);
        }
        return result;
    }

    JSONObject playFor(JSONObject perf) {
        return (JSONObject) plays.get(((String) perf.get("playID")).toLowerCase());
    }

    int audienceFor(JSONObject perf) {
        return Integer.parseInt(perf.get("audience").toString());
    }

    double amountFor(JSONObject perf) {
        JSONObject play = playFor(perf);
        int perfAudience = audienceFor(perf);
        double result = 0;

        switch (play.get("type").toString()) {
            case "tragedy" -> {
                result = 40000;
                if (perfAudience > 30) {
                    result += 1000 * (perfAudience - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (perfAudience > 20) {
                    result += 10000 + 500 * (perfAudience - 20);
                }
                result += 300 * perfAudience;
            }
            default -> throw new RuntimeException("unknown type + " + play.get("type").toString());
        }
        return result;
    }

    JSONObject getPlays(String playsFile) {
        String plays = GetDataSource(playsFile);
        return new JSONObject(plays);
    }

    JSONObject getInvoice(String invoiceFile) {
        String invoices = GetDataSource(invoiceFile);
        return new JSONArray(invoices).getJSONObject(0);
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


}
