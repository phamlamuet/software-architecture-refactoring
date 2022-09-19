package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws Exception {
        BillPrinter billPrinter = new BillPrinter("plays2.json","invoices2.json");
        System.out.println(billPrinter.statement());
    }
}