package luhn;

public class Luhn {

    private Integer sumOfOddPlaces = 0;
    private Integer sumOfEvenPlaces = 0;
    private Integer sumOfOddAndEvenPlaces = 0;
    private Integer modulusValue = 0;
    private Integer missingDigitPosition = 0;
    private Integer missingDigitValue;
    private Boolean isEligibleToCompute;

    /**
     * Getter Methods
     */
    public Integer getMissingDigitPosition() {
        return missingDigitPosition;
    }

    public Integer getMissingDigitValue() {
        return missingDigitValue;
    }

    public Boolean getIsEligibleToCompute() {
        return isEligibleToCompute;
    }

    /** Constructor*/


    /** Private Methods*/

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
    private Integer evenPlaceMapper(Integer integer) {
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
    private Integer evenPlaceReverseMapper(Integer integer) {
        return (((integer * 5) / 10) + ((integer * 5) % 10));
    }

    /**
     * isEligible() Method
     * @param input Used to Check the below pre-requisites like
     *              1. Check if the input string is only numbers and only one'?' symbol
     *              2. Log info to correct the input if the input String is not in expected format
     *              3. If all the validations are successful,
     *              (i)     compute 'sumOfOddPlaces' and 'sumOfEvenPlaces' variables
     *              (ii)    extract the position of '?' in the input into 'missingDigitPosition'
     *              (iii)   compute 'sumOfOddAndEvenPlaces', 'modulusValue' and 'missingDigitValue'
     *              4. Return true if all validations are successful else return false
     */
    public boolean isEligible(String input) {
        isEligibleToCompute = true;
        char[] charArray = input.toCharArray();
        Character character;
        Integer integer;
        int missingCount = 0;
        for (int i = charArray.length, j = 1; i > 0; i--, j++) {
            character = charArray[i - 1];
            try {
                integer = Integer.parseInt(character.toString());
                if (j % 2 == 0)
                    sumOfEvenPlaces = sumOfEvenPlaces + this.evenPlaceMapper(integer);
                else
                    sumOfOddPlaces = sumOfOddPlaces + integer;
            } catch (NumberFormatException numberFormatException) {
                if (character == '?' && missingCount == 0) {
                    missingDigitPosition = j;
                    missingCount++;
                } else if (missingCount > 0) {
                    System.out.println("The input string '" + input + "' has a second '?' missing digit at position " + i + "." +
                            "\nOnly one '?' mark is allowed in the input String." +
                            "\nPlease enter data with only one missing digit (?) to return valid results.");

                } else {
                    System.out.println("The input string '" + input + "' has invalid characters '" + character + "' at position " + i + ".\nOnly integers and '?' mark are allowed in the input String.\nPlease enter valid data to return valid results.");
                    isEligibleToCompute = false;
                    sumOfOddPlaces = 0;
                    sumOfEvenPlaces = 0;
                    missingDigitPosition = 0;
                    missingDigitValue = null;
                    break;
                }
            }
        }
        sumOfOddAndEvenPlaces = sumOfOddPlaces + sumOfEvenPlaces;
        modulusValue = (sumOfOddAndEvenPlaces%10);
        if (modulusValue % 10 != 0)
            missingDigitValue = 10 - modulusValue;
        if (missingDigitPosition % 2 == 0)
            missingDigitValue = this.evenPlaceReverseMapper(missingDigitValue);
        return isEligibleToCompute;
    }

    /**
     * clear() Method
     * To clear out all the private variables to their default value
     */
    public void clear() {
        sumOfOddPlaces = 0;
        sumOfEvenPlaces = 0;
        sumOfOddAndEvenPlaces = 0;
        modulusValue = 0;
        missingDigitPosition = 0;
        missingDigitValue = null;
        isEligibleToCompute = true;
    }


}
