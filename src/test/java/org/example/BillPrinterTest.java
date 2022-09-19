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
        assertEquals(expected,new BillPrinter().statement());
    }
}