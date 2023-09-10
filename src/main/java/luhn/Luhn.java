package luhn;

import java.util.LinkedList;
import java.util.List;

public class Luhn {
    private static Integer missingDigitLuhnPosition = 0;
    private static Integer missingDigitPosition = 0;
    private static Integer missingDigitValue;
    private static Boolean isEligibleToCompute;
    private static List<Character> listOfInputCharacters;

    /**
     * Getter Methods
     */
    public static Integer getmissingDigitPosition() {
        return missingDigitPosition;
    }

    public static Integer getMissingDigitValue() {
        return missingDigitValue;
    }

    public static Boolean getIsEligibleToCompute() {
        return isEligibleToCompute;
    }

    /** Private Static Methods*/

    /**
     * evenPlaceMapper() Method
     *
     * @param integer Returns the integer value used to sum the even places in Luhn's Algorithm
     *                Consider the example 75962246816. Here the even places are 10s, 1000s, 100000s, etc units (highlighted with a ↕ symbol below)
     *                ↕ ↕ ↕ ↕ ↕
     *                [EVEN PLACES]
     *                According to Luhn's Algorithm, we multiple the even places by 2. If the multiplied value is greater than 10, then we add the digits. (eg. 14 ==> 1+4=5)
     *                Thus the even place mapping would be
     *                0   -->     0
     *                1   -->     2
     *                2   -->     4
     *                3   -->     5
     *                4   -->     6
     *                5   -->     1
     *                6   -->     3
     *                7   -->     5
     *                8   -->     7
     *                9   -->     9
     */
    private static Integer evenPlaceMapper(Integer integer) {
        return (((integer * 2) / 10) + ((integer * 2) % 10));
    }

    /**
     * evenPlaceReverseMapper() Method
     *
     * @param integer Returns the integer value mapped on the even places of Luhn's Algorithm. (ie. a reversal of the luhn sum value to arrive at the original value)
     *                Consider the example 75962246816. Here the even places are 10s, 1000s, 100000s, etc units (highlighted with a ↕ symbol below)
     *                ↕ ↕ ↕ ↕ ↕
     *                1 3 4 3 2
     *                [EVEN PLACES]
     *                According to Luhn's Algorithm, we multiple the even places by 2. If the multiplied value is greater than 10, then we add the digits. (eg. 14 ==> 1+4=5)
     *                Thus the even place reverse mapping would be
     *                Original    EvenMap     ReverseEvenMap
     *                0   -->     0   -->     0
     *                1   -->     2   -->     1
     *                2   -->     4   -->     2
     *                3   -->     5   -->     3
     *                4   -->     6   -->     4
     *                5   -->     1   -->     5
     *                6   -->     3   -->     6
     *                7   -->     5   -->     7
     *                8   -->     7   -->     8
     *                9   -->     9   -->     9
     */
    private static Integer evenPlaceReverseMapper(Integer integer) {
        return (((integer * 5) / 10) + ((integer * 5) % 10));
    }

    /** Public Static Methods*/

    /**
     * isEligible() Method
     *
     * @param input Used to Check the below pre-requisites like
     *              1. Check if the input string is only numbers and only one'?' symbol
     *              2. Log info to correct the input if the input String is not in expected format
     *              3. If all the validations are successful,
     *              (i)     compute 'sumOfOddPlaces' and 'sumOfEvenPlaces' variables
     *              (ii)    extract the position of '?' in the input into 'missingDigitPosition'
     *              (iii)   compute 'sumOfOddAndEvenPlaces', 'modulusValue' and 'missingDigitValue'
     *              4. Return true if all validations are successful else return false
     */
    public static boolean isEligible(String input) {
        isEligibleToCompute = true;
        Integer sumOfEvenPlaces = 0;
        Integer sumOfOddPlaces = 0;
        Integer sumOfOddAndEvenPlaces = 0;
        Integer modulusValue = 0;
        char[] charArray = input.toCharArray();
        Character character;
        Integer integer;
        int missingCount = 0;
        listOfInputCharacters = new LinkedList<>();
        for (int i = charArray.length, j = 1; i > 0; i--, j++) {
            character = charArray[i - 1];
            listOfInputCharacters.add(character);
            try {
                integer = Integer.parseInt(character.toString());
                if (j % 2 == 0)
                    sumOfEvenPlaces = sumOfEvenPlaces + evenPlaceMapper(integer);
                else
                    sumOfOddPlaces = sumOfOddPlaces + integer;
            } catch (NumberFormatException numberFormatException) {
                if (character == '?' && missingCount == 0) {
                    missingDigitLuhnPosition = j;
                    missingCount++;
                } else if (missingCount > 0) {
                    System.out.println("The input string '" + input + "' has a second '?' missing digit at position " + i + "." +
                            "\nOnly one '?' mark is allowed in the input String." +
                            "\nPlease enter data with only one missing digit (?) to return valid results.");

                } else {
                    System.out.println("The input string '" + input + "' has invalid characters '" + character + "' at position " + i + "." +
                            "\nOnly integers and '?' mark are allowed in the input String." +
                            "\nPlease enter valid data to return valid results.");
                    isEligibleToCompute = false;
                    sumOfOddPlaces = 0;
                    sumOfEvenPlaces = 0;
                    missingDigitLuhnPosition = 0;
                    missingDigitValue = null;
                    break;
                }
            }
        }
        sumOfOddAndEvenPlaces = sumOfOddPlaces + sumOfEvenPlaces;
        modulusValue = (sumOfOddAndEvenPlaces % 10);
        if (modulusValue % 10 != 0)
            missingDigitValue = 10 - modulusValue;
        if (missingDigitLuhnPosition % 2 == 0)
            missingDigitValue = evenPlaceReverseMapper(missingDigitValue);
        missingDigitPosition = listOfInputCharacters.size() - missingDigitLuhnPosition + 1;
        return isEligibleToCompute;
    }

    /**
     * clear() Method
     * To clear out all the private variables to their default value
     */
    public static void clear() {
        missingDigitLuhnPosition = 0;
        missingDigitValue = null;
        isEligibleToCompute = true;
    }

    /**
     * findMissingDigit() Method
     *
     * @param input Used to return the missing digit in the string
     *              1. Check if the input string is in valid format
     *              2. If it is valid, the method returns the missing number using Luhn's Algorithm
     *              3. If it is not valid, the method returns null
     */
    public static Integer findMissingDigit(String input) {
        if (isEligible(input))
            return missingDigitValue;
        else
            return null;
    }

    /**
     * validNumber() Method
     * @param input Used to return the string by filling in the valid value in place of '?' mark
     */
    public static String validNumber(String input) {
        if (isEligible(input)) {
            String validString = "";
            for (int i = listOfInputCharacters.size();i>0;i--){
                if(i == (missingDigitLuhnPosition))
                    validString = validString + missingDigitValue.toString();
                else
                    validString = validString + listOfInputCharacters.get(i-1);
            }
            return validString;
        } else
            return null;
    }


}
