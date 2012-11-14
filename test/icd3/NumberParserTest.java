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

    @Test
    public void testParseMultipleOfTen() throws InvalidNumberException
    {
        assertEquals(30, NumberParser.parseMultipleOfTen("thirty"));
    }

    @Test
    public void testParseTeen() throws InvalidNumberException
    {
        assertEquals(13, NumberParser.parseTeen("thirteen"));
    }

    @Test
    public void testParseDigit() throws InvalidNumberException
    {
        assertEquals(5, NumberParser.parseDigit("five"));
    }

}
