package org.example;

import luhn.Luhn;
import luhn.LuhnQueryList;

import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        String input = "3798721411?????";
        List<String> check = LuhnQueryList.generateLuhnNumbers(input);
        System.out.println("Input: " + input);
        System.out.println("Total Possible Combinations: " + check.size());
        System.out.println("List of Possible Values:");
        Iterator iterator = check.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }
}
