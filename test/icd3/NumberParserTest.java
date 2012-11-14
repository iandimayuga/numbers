package icd3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NumberParserTest
{
    @Test
    public void testParseNumber() throws InvalidNumberException
    {
        assertEquals(
                -987654321,
                NumberParser
                        .parseNumber("negative nine hundred eighty seven million six hundred fifty four thousand three hundred twenty one"));
    }

    @Test
    public void testParseTriple() throws InvalidNumberException
    {
        assertEquals(123, NumberParser.parseTriple("one hundred twenty three"));
    }
}
