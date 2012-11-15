package icd3;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
 * @author ian
 *
 */
public class NumberParserTest
{
    /**
     * Structured basis test for {@link NumberParser#parseNumber(String)}.
     */
    @Test
    public void testParseNumber() throws InvalidNumberException
    {
        Map<String, Integer> m = new HashMap<>();

        // Test zeroes
        m.put("zero", 0);
        m.put("naught", 0);

        // Test individual places
        m.put("one", 1);
        m.put("twenty", 20);
        m.put("three hundred", 300);
        m.put("four thousand", 4000);
        m.put("fifty thousand", 50000);
        m.put("six hundred thousand", 600000);
        m.put("seven million", 7000000);
        m.put("eighty million", 80000000);
        m.put("nine hundred million", 900000000);

        // Test teen
        m.put("seventeen", 17);

        // Test places together with one triple missing
        m.put("one hundred twenty three thousand five hundred sixty seven", 123567);
        m.put("one hundred nine million twenty", 109000020);
        m.put("thirteen million eight hundred eleven thousand", 13811000);

        // Test negative
        m.put("negative zero", 0);
        m.put("minus one hundred twenty three million four hundred fifty six thousand seven hundred eighty nine", -123456789);
        m.put("negative forty two", -42);


        // case insensitivity
        m.put("nIne HundRed eiGhtY SEVEN thOUSand FiFtEeN", 987015);

        // whitespace insensitivity
        m.put("   negative   five\t hundred \n eleven  million seventeen    thousand  \r\n  one  ", -511017001);

        for (Entry<String, Integer> entry : m.entrySet())
        {
            String input = entry.getKey();
            int expected = entry.getValue();

            assertEquals(expected, NumberParser.parseNumber(input));
        }
    }

    /**
     * Structured basis test for {@link NumberParser#parseTriple(String)}.
     */
    @Test
    public void testParseTriple() throws InvalidNumberException
    {
        Map<String, Integer> m = new HashMap<>();

        // ones place
        m.put("two", 2);

        // tens place
        m.put("thirty", 30);

        // hundreds place
        m.put("nine hundred", 900);

        // teen
        m.put("seventeen", 17);

        // tens and ones
        m.put("forty two", 42);

        // hundreds and ones
        m.put("four hundred nine", 409);

        // hundreds and teen
        m.put("two hundred ten", 210);

        // hundreds and tens
        m.put("seven hundred twenty", 720);

        // all places
        m.put("three hundred sixty five", 365);

        // case insensitivity
        m.put("nIne HundRed eiGhtY SEVEN", 987);

        // whitespace insensitivity
        m.put("      five\t hundred \n eleven   \r\n   ", 511);

        for (Entry<String, Integer> entry : m.entrySet())
        {
            String input = entry.getKey();
            int expected = entry.getValue();

            assertEquals(expected, NumberParser.parseTriple(input));
        }
    }
}
