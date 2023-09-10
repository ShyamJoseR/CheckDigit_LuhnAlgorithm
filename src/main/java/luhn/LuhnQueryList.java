package luhn;

import java.util.*;
import java.util.regex.Pattern;

public class LuhnQueryList {
    public static List<String> luhnQueryStrings;
    public static List<String> luhnValidStrings;
    public static Boolean isEligibleToQuery;
    private static Set<Integer> setOfMissingDigitPositions;
    private static HashMap<Integer, Character> mapOfInputCharacters;


    /** Public Static Methods*/

    /**
     * isEligible() Method
     *
     * @param input Used to Check the below pre-requisites like
     *              1. Check if the input string is only numbers and only one'?' symbol
     *              2. Log info to correct the input if the input String is not in expected format
     *              4. Return true if all validations are successful else return false
     */
    public static boolean isEligible(String input) {
        isEligibleToQuery = true;
        char[] charArray = input.toCharArray();
        setOfMissingDigitPositions = new HashSet<>();
        mapOfInputCharacters = new HashMap<>();
        Character character;
        Integer integer;
        for (int i = 0; i < charArray.length; i++) {
            character = charArray[i];
            mapOfInputCharacters.put(i, character);
            try {
                integer = Integer.parseInt(character.toString());
            } catch (NumberFormatException numberFormatException) {
                if (character == '?') {
                    setOfMissingDigitPositions.add(i);
                } else {
                    System.out.println("The input string '" + input + "' has invalid characters '" + character + "' at position " + i + "." +
                            "\nOnly integers and '?' mark are allowed in the input String." +
                            "\nPlease enter valid data to return valid results.");
                    isEligibleToQuery = false;
                    break;
                }
            }
        }
        return isEligibleToQuery;
    }

    /**
     * generateQueryStrings() Method
     *
     * @param input Takes the input String and generate a list of applicable Luhn Query Strings with only one '?'
     *              Note: only the last '?' is left out. all other values are auto-filled
     */
    public static List<String> generateQueryStrings(String input) {
        List<String> queryList = new LinkedList<>();
        if (isEligible(input)) {
            if (setOfMissingDigitPositions.size() > 1) {
                List<String> stringList = new LinkedList<>();
                String str = "";
                char[] charArray = input.toCharArray();
                for (int i = 0; i < input.length(); i++) {
                    if (setOfMissingDigitPositions.contains(i)) {
                        stringList.add(str);
                        str = "";
                    } else {
                        str = str + charArray[i];
                    }
                }
                if (!str.isBlank())
                    stringList.add(str);
                double exp = (double) setOfMissingDigitPositions.size() - 1;
                double endvalue = Math.pow(10, exp);
                for (double db = 0; db < endvalue; db++) {
                    Double tempDouble = db;
                    String tempstr = tempDouble.toString();
                    tempstr = tempstr.substring(0, tempstr.length() - 2);
                    int diff = (int) exp - tempstr.length();
                    for (int j = 0; j < diff; j++) {
                        tempstr = "0" + tempstr;
                    }
                    char[] permutechar = tempstr.toCharArray();
                    String queryString = "";
                    for (int i = 0; i < setOfMissingDigitPositions.size() - 1; i++) {
                        queryString = queryString + stringList.get(i) + permutechar[i];
                    }
                    if (stringList.size() > setOfMissingDigitPositions.size())
                        queryString = queryString + stringList.get(setOfMissingDigitPositions.size() - 1) + "?" + stringList.get(setOfMissingDigitPositions.size());
                    else
                        queryString = queryString + stringList.get(setOfMissingDigitPositions.size() - 1) + "?" ;
                    queryList.add(queryString);
                }
            } else {
                queryList.add(input);
            }
            try {
                luhnQueryStrings.clear();
                luhnQueryStrings.addAll(queryList);
            } catch (NullPointerException nullPointerException) {

            }
        }
        return queryList;
    }

    /**
     * generateLuhnNumbers() Method
     * @param input
     * Takes the input string and returns a list of possible numbers satisfying Luhn Algorithm
     * */
    public static List<String> generateLuhnNumbers(String input){
        List<String> queryList = new LinkedList<>();
        queryList = generateQueryStrings(input);
        List<String> luhnList = new LinkedList<>();
        for(int i=0; i<queryList.size(); i++){
            luhnList.add(Luhn.validNumber(queryList.get(i)));
        }
        try {
            luhnValidStrings.clear();
            luhnValidStrings.addAll(luhnList);
        } catch (NullPointerException nullPointerException) {

        }
        return luhnList;
    }

}
