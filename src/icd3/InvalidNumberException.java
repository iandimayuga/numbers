package icd3;

import java.text.ParseException;

public class InvalidNumberException extends Exception
{
    public InvalidNumberException(String message)
    {
        super(message);
    }
}
