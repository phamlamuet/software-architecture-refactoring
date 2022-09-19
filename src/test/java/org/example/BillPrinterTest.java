package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillPrinterTest {

    @Test
    void statement() {
        String expected = "Statement for BigCo\n" +
                "Hamlet : $650.00 (55 seats )\n" +
                "As You Like It : $580.00 (35 seats )\n" +
                "Othello : $500.00 (40 seats )\n" +
                "Amount owed is $1,730.00\n" +
                "You earned 47.0 credits\n";
        assertEquals(expected,new BillPrinter("plays.json","invoices.json").statement());
    }

    @Test
    void statement1(){
        String expected = "Statement for HoangTung\n" +
                "Hamlet : $5,300.00 (520 seats )\n" +
                "As You Like It : $351.00 (17 seats )\n" +
                "Othello : $336.00 (12 seats )\n" +
                "Amount owed is $5,987.00\n" +
                "You earned 495.0 credits\n";
        assertEquals(expected,new BillPrinter("plays1.json","invoices1.json").statement());
    }

    @Test
    void statement2(){
        String expected = "Statement for Lam Pham\n" +
                "Hamlet : $400.00 (24 seats )\n" +
                "As You Like It : $500.00 (25 seats )\n" +
                "Othello : $400.00 (27 seats )\n" +
                "Amount owed is $1,300.00\n" +
                "You earned 5.0 credits\n";
        assertEquals(expected,new BillPrinter("plays2.json","invoices2.json").statement());
    }
}