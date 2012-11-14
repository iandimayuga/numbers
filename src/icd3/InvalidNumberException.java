package icd3;

import java.text.ParseException;

public class InvalidNumberException extends ParseException
{
    public InvalidNumberException(String s, int errorOffset)
    {
        super(s, errorOffset);
    }
}
