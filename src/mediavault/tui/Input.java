package mediavault.tui;

import java.util.Arrays;
import java.util.Scanner;

abstract public class Input
{

    private static Scanner scanner = new Scanner(System.in);

    /* helper funcs */

    private static String readLine(String prompt)
    {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    /* strings */


    public static void holdScreen(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }


    /**
     * Prompts the user for a single-line string and loops until a valid token matching constraints is supplied.
     * <p>
     * <b>Precondition:</b> valid array contains standardized upper-case string configurations to benchmark against.<br>
     * <b>Postcondition:</b> Returns a matching trimmed uppercase input string that successfully matched a value inside valid tokens.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @param valid  Varargs array representing the permitted code inputs.
     * @return String The approved user selection.
     */
    public static String getStrInput(String prompt, String... valid)
    {

        // this is solely for menu interactions

        while (true) {
            String input = readLine(prompt).toUpperCase();

            if (valid.length == 0)
                continue;

            if (Arrays.asList(valid).contains(input))
                return input;

            System.out.println("Invalid option, please try again.");
        }
    }

    public static String getMultilineInput(String prompt)
    {

        // dis is for multiline strings (synopsis)

        System.out.println(prompt);
        System.out.println("(Type >END on its own line to finish)");

        StringBuilder sb = new StringBuilder();

        while (true) {

            String line = scanner.nextLine();

            if (line.equals(">END"))
                break;

            sb.append(line).append('\n');
        }

        if (sb.length() > 0)
            sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    /* integers */

    /**
     * Captures a single integer while guaranteeing that it sits safely between boundaries.
     * <p>
     * <b>Precondition:</b> min must be less than or equal to max.<br>
     * <b>Postcondition:</b> Returns a structurally validated integer bounded perfectly between the minimum and maximum arguments.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @param min    Lower numeric limit allowed (inclusive).
     * @param max    Upper numeric limit allowed (inclusive).
     * @return int Verified numerical choice.
     */
    public static int getIntInput(String prompt)
    {

        // single int

        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static int getIntInput(String prompt, int min, int max)
    {

        // int range (inclusive)

        while (true) {
            try {
                int value = Integer.parseInt(readLine(prompt));

                if (value < min || value > max) {
                    System.out.printf(
                        "Please enter a value between %d and %d.%n",
                        min, max
                    );
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    /* float */

    public static float getFloatInput(String prompt)
    {
        while (true) {

            try {
                return Float.parseFloat(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static float getFloatInput(String prompt, float min, float max)
    {
        while (true) {
            try {
                float value = Float.parseFloat(readLine(prompt));

                if (value < min || value > max) {
                    System.out.printf(
                        "Please enter a value between %.2f and %.2f.%n",
                        min, max
                    );
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
