/**
 *
 */
package icd3;

import java.util.Scanner;

public class NumbersMain
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        StringBuilder lines = new StringBuilder();

        // Read to EOF
        while (in.hasNext())
        {
            lines.append(in.nextLine());
        }

        int result = 0;

        try
        {
            // Parse the number
            result = NumberParser.parseNumber(lines.toString());
        } catch (InvalidNumberException invalidNumberException)
        {
            System.err.println(invalidNumberException.getMessage());
            System.exit(7);
        } catch (Exception e)
        {
            System.err.printf("Error unrelated to number format: %s\n", e.getMessage());
            System.exit(7);
        }

        // Print result
        System.out.println(result);

        in.close();

        System.exit(0);
    }

}
