/**
 *
 */
package icd3;

import java.util.HashMap;
import java.util.Map;

/**
 * Static tools to translate an American English representation of a number to its integer representation.
 *
 */
public class NumberParser
{
    // Regex for powers of one thousand. Add billions, trillions to extend.
    private static final String[] TRIPLE_POWERS = { "", "thousand", "million" };

    // Constant multiplier for powers of ten
    private static final int ONE_THOUSAND = 1000;
    private static final int ONE_HUNDRED = 100;
    private static final int TEN = 10;

    // Regexes for negation and zero
    private static final String REGEX_NEGATIVE = "minus|negative";
    private static final String REGEX_ZERO = "zero|naught";

    // Regexes for numbers involved in triples
    private static final String REGEX_DIGIT = "one|two|three|four|five|six|seven|eight|nine";
    private static final String REGEX_TEEN = "ten|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen";
    private static final String REGEX_MULTIPLE_OF_TEN = "twenty|thirty|forty|fifty|sixty|seventy|eighty|ninety";
    private static final String REGEX_HUNDRED = "hundred";

    // Words used for numbers
    private static final String[] DIGITS = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
            "nine" };
    private static final String[] TEENS = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
            "seventeen", "eighteen", "nineteen" };
    private static final String[] MULTIPLES_OF_TEN = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty",
            "seventy", "eighty", "ninety" };

    // Capture group names
    private static final String GROUP_NEGATIVE = "negative";
    private static final String GROUP_ZERO = "zero";
    private static final String GROUP_TRIPLE = "triple";
    private static final String GROUP_DIGIT = "digit";
    private static final String GROUP_TEEN = "teen";
    private static final String GROUP_MULTIPLE_OF_TEN = "multipleOfTen";
    private static final String GROUP_HUNDRED = "hundred";

    // Numeric lookup table generated using the above constants
    private static final Map<String, Integer> s_numberLookup = generateLookup();

    /**
     * Parses an American English representation of a number between negative one billion and positive one billion,
     * exclusive.
     *
     * @param text The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [-999999999, 999999999]
     */
    public static int parseNumber(String text) throws InvalidNumberException
    {
        if (text.equals("zero"))
        {
            return 0;
        }
        else
        {
            throw new InvalidNumberException("Only zero is allowed at this time.", 0);
        }
    }

    /**
     * Parses an American English representation of a number between zero and positive one thousand, exclusive.
     *
     * @param triple The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [1, 999].
     */
    public static int parseTriple(String triple) throws InvalidNumberException
    {
        if (triple.equals("one"))
        {
            return 1;
        }
        else
        {
            throw new InvalidNumberException("Only one is allowed at this time.");
        }
    }

    /**
     * Parses an American English representation of a multiple of ten between twenty and one hundred, exclusive of one
     * hundred.
     *
     * @param multipleOfTen The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [20, 90] and equivalent to 0 mod 10.
     */
    public static int parseMultipleOfTen(String multipleOfTen) throws InvalidNumberException
    {
        if (multipleOfTen.equals("twenty"))
        {
            return 10;
        }
        else
        {
            throw new InvalidNumberException("Only twenty is allowed at this time.");
        }
    }

    /**
     * Parses an American English representation of a number between ten and twenty, exclusive of twenty.
     *
     * @param teen The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [10, 19].
     */
    public static int parseTeen(String teen) throws InvalidNumberException
    {
        if (teen.equals("ten"))
        {
            return 10;
        }
        else
        {
            throw new InvalidNumberException("Only ten is allowed at this time.");
        }
    }

    /**
     * Parses an American English representation of a number between zero and positive ten, exclusive.
     *
     * @param digit The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [1, 9].
     */
    public static int parseDigit(String digit) throws InvalidNumberException
    {
        if (digit.equals("one"))
        {
            return 1;
        }
        else
        {
            throw new InvalidNumberException("Only one is allowed at this time.");
        }
    }

    private static Map<String, Integer> generateLookup()
    {
        Map<String, Integer> numberLookup = new HashMap<>();

        // Generate powers of one thousand dynamically to keep this extensible to billions etc.
        for (int i = 1; i < TRIPLE_POWERS.length; ++i)
        {
            numberLookup.put(TRIPLE_POWERS[i], i * ONE_THOUSAND);
        }

        // Generate digits
        for (int i = 0; i < DIGITS.length; ++i)
        {
            numberLookup.put(DIGITS[i], i);
        }

        // Generate teens (including ten)
        for (int i = 0; i < TEENS.length; ++i)
        {
            numberLookup.put(TEENS[i], i + TEN);
        }

        // Generate multiples of ten
        for (int i = 0; i < MULTIPLES_OF_TEN.length; ++i)
        {
            numberLookup.put(MULTIPLES_OF_TEN[i], i * TEN);
        }

        return numberLookup;
    }
}
