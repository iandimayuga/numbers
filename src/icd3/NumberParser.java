/**
 *
 */
package icd3;

/**
 * Static tools to translate an American English representation of a number to its integer representation.
 *
 */
public class NumberParser
{
    // Constant multiplier for powers of one thousand
    private static final int ONE_THOUSAND = 1000;

    // Regexes for negation and zero
    private static final String REGEX_NEGATIVE = "minus|negative";
    private static final String REGEX_ZERO = "zero|naught";

    // Regexes for powers of one thousand
    private static final String REGEX_THOUSAND = "thousand";
    private static final String REGEX_MILLION = "million";

    // Regexes used for parsing triples
    private static final String REGEX_DIGIT = "one|two|three|four|five|six|seven|eight|nine";
    private static final String REGEX_TEEN = "ten|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen";
    private static final String REGEX_MULTIPLE_OF_TEN = "twenty|thirty|forty|fifty|sixty|seventy|eighty|ninety";
    private static final String REGEX_HUNDRED = "hundred";


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
     * Parses an American English representation of a number between zero and positive one thousand, exclusive of one
     * thousand.
     *
     * @param triple The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [0, 999].
     */
    public static int parseTriple(String triple) throws InvalidNumberException
    {
        if (triple.equals("zero"))
        {
            return 0;
        }
        else
        {
            throw new InvalidNumberException("Only zero is allowed at this time.", 0);
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
    public static int parseMultiplesOfTen(String multipleOfTen) throws InvalidNumberException
    {
        if (multipleOfTen.equals("twenty"))
        {
            return 10;
        }
        else
        {
            throw new InvalidNumberException("Only twenty is allowed at this time.", 0);
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
            throw new InvalidNumberException("Only ten is allowed at this time.", 0);
        }
    }

    /**
     * Parses an American English representation of a number between zero and positive ten, exclusive of ten.
     *
     * @param digit The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [0, 9].
     */
    public static int parseDigit(String digit) throws InvalidNumberException
    {
        if (digit.equals("zero"))
        {
            return 0;
        }
        else
        {
            throw new InvalidNumberException("Only zero is allowed at this time.", 0);
        }
    }
}
