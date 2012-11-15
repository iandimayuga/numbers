/**
 *
 */
package icd3;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static tools to translate an American English representation of a number to its integer representation.
 *
 */
public class NumberParser
{
    // Regex for powers of one thousand. Add billions, trillions to extend.
    private static final String[] TRIPLE_POWERS = { "", "thousand ", "million " };

    // Constant multiplier for powers of ten
    private static final int ONE_THOUSAND = 1000;
    private static final int ONE_HUNDRED = 100;
    private static final int TEN = 10;

    // Regexes for negation and zero
    private static final String REGEX_NEGATIVE = "(minus|negative)";
    private static final String REGEX_ZERO = "(zero|naught)";

    // Regexes for numbers involved in triples
    private static final String REGEX_DIGIT = "(one|two|three|four|five|six|seven|eight|nine)";
    private static final String REGEX_TEEN = "(ten|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen)";
    private static final String REGEX_MULTIPLE_OF_TEN = "(twenty|thirty|forty|fifty|sixty|seventy|eighty|ninety)";
    private static final String REGEX_HUNDRED = "(hundred)";

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
     * exclusive. Does not accept empty or whitespace-only input.
     *
     * @param text The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [-999999999, 999999999], or if the input is empty or only whitespace.
     */
    public static int parseNumber(String text) throws InvalidNumberException
    {
        // Explicitly exclude the empty string
        if (null == text || text.trim().length() == 0)
        {
            throw new InvalidNumberException("Empty input is invalid.");
        }

        // Assume that text ends with a space. Append one to ensure it does.
        text = text.toLowerCase() + " ";

        // Build the master regex pattern string
        StringBuilder masterBuilder = new StringBuilder();

        // Add the leading whitespace and the negation
        masterBuilder.append(String.format("\\s*(?<%s>%s\\s+)?", GROUP_NEGATIVE, REGEX_NEGATIVE));

        // Add the zero and the "or" operator
        masterBuilder.append(String.format("((?<%s>%s)|(", GROUP_ZERO, REGEX_ZERO));

        // Add the triplet groups. This is extensible to billions, trillions, etc.
        for (int i = TRIPLE_POWERS.length - 1; i >= 0; --i)
        {
            // triple"n" refers to the triplet at the nth power of one thousand
            masterBuilder.append(String.format("((?<%s%d>[\\sa-z]+\\s+)%s)?", GROUP_TRIPLE, i, TRIPLE_POWERS[i]));
        }

        // Close the number group and add trailing whitespace
        masterBuilder.append("))\\s*");

        // For a number that only extends to the millions, this should produce the following regular expression:
        // \s*(?<negative>(minus|negative)\s+)?((?<zero>(zero|naught))|(((?<triple2>[a-z]+)\s+million\s+)?((?<triple1>[a-z]+)\s+thousand\s+)?(?<triple0>[a-z]+)?))\s*");
        Pattern masterPattern = Pattern.compile(masterBuilder.toString());

        Matcher matcher = masterPattern.matcher(text);

        if (!matcher.matches())
        {
            throw new InvalidNumberException(String.format("Invalid number format: '%s'", text.trim()));
        }

        // Separate into groups and parse each one
        int result = 0;

        // If we matched the zero group then we are done
        String zero = matcher.group(GROUP_ZERO);
        if (zero != null)
        {
            return result;
        }

        // Otherwise, we need to iterate through each triple and add the results together
        for (int i = 0; i < TRIPLE_POWERS.length; ++i)
        {
            String triple = matcher.group(String.format("%s%d", GROUP_TRIPLE, i));
            if (triple != null)
            {
                try
                {
                    // Parse the triplet and multiply it by its power
                    result += parseTriple(triple) * Math.pow(ONE_THOUSAND, i);
                } catch (InvalidNumberException e)
                {
                    // Add the information regarding the power and rethrow
                    throw new InvalidNumberException(String.format("Invalid triple in the %ss place: %s",
                            i > 0 ? TRIPLE_POWERS[i].trim() : "one", e.getMessage()));
                }
            }
        }

        String negative = matcher.group(GROUP_NEGATIVE);
        if (negative != null)
        {
            result = -result;
        }

        return result;
    }

    /**
     * Parses an American English representation of a number between zero and positive one thousand, exclusive. Does not
     * accept empty or whitespace-only input.
     *
     * @param triple The textual representation.
     * @return The integer representation.
     * @throws InvalidNumberException If the input is not a proper American English representation of a number within
     *             [1, 999], or if the input is empty or only whitespace.
     */
    public static int parseTriple(String triple) throws InvalidNumberException
    {
        if (null == triple || triple.trim().length() == 0)
        {
            throw new InvalidNumberException(String.format("Empty triple is invalid."));
        }
        // Need to assume triple ends in a space. Add a space to ensure it does.
        triple = triple.toLowerCase() + " ";
        StringBuilder tripleBuilder = new StringBuilder();
        // Add the hundreds place
        tripleBuilder.append(String.format("\\s*((?<%s>%s\\s+)%s\\s+)?", GROUP_HUNDRED, REGEX_DIGIT, REGEX_HUNDRED));
        // Add the multiples of ten
        tripleBuilder.append(String.format("(((?<%s>%s\\s+)?", GROUP_MULTIPLE_OF_TEN, REGEX_MULTIPLE_OF_TEN));
        // Add the digits and the or
        tripleBuilder.append(String.format("(?<%s>%s\\s+)?)|", GROUP_DIGIT, REGEX_DIGIT));
        // Add the teens
        tripleBuilder.append(String.format("(?<%s>%s\\s+))", GROUP_TEEN, REGEX_TEEN));
        Pattern triplePattern = Pattern.compile(tripleBuilder.toString());
        Matcher matcher = triplePattern.matcher(triple);
        if (!matcher.matches())
        {
            throw new InvalidNumberException(String.format("'%s' is not a valid triple.", triple.trim()));
        }
        // Look up each group
        int result = 0;
        // Get the hundreds place, if any
        String hundred = matcher.group(GROUP_HUNDRED);
        if (hundred != null)
        {
            // Because we're just looking up digits, we must multiply by one hundred
            result += s_numberLookup.get(hundred.trim()) * ONE_HUNDRED;
        }
        // Get either the tens and ones place individually, or as a teen
        String multipleOfTen = matcher.group(GROUP_MULTIPLE_OF_TEN);
        String digit = matcher.group(GROUP_DIGIT);
        String teen = matcher.group(GROUP_TEEN);

        if (teen != null)
        {
            result += s_numberLookup.get(teen.trim());
        }
        else
        {
            // Not a teen, so must be a ten and/or a one
            if (multipleOfTen != null)
            {
                result += s_numberLookup.get(multipleOfTen.trim());
            }
            if (digit != null)
            {
                result += s_numberLookup.get(digit.trim());
            }
        }

        return result;
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
