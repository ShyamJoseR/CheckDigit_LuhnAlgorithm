package org.example;

import luhn.Luhn;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String input = "379?72145111006";
        Luhn luhn = new Luhn();
        luhn.isEligible(input);
        System.out.println("Input Value is   : " + input);
        System.out.println("Missing Digit is : " + luhn.getMissingDigitValue());

    }
}
