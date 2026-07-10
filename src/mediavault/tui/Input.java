package mediavault.tui;

import java.util.Arrays;
import java.util.Scanner;

abstract public class Input
{

    private static Scanner scanner = new Scanner(System.in);

    /* helper funcs */

    /**
     * Prompts the user and reads a trimmed line of text from standard input.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the trimmed raw input String.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @return String The raw input text.
     */
    private static String readLine(String prompt)
    {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    /* strings */

    /**
     * Pauses application execution until the user presses the Enter key.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Blocks thread execution until a newline sequence is registered.
     * </p>
     * @param prompt The wait message shown to the user.
     * @return void
     */
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
            String input = readLine(prompt);

            if (valid.length == 0)
                return input;

            if (Arrays.asList(valid).contains(input.toUpperCase()))
                return input;

            System.out.println("Invalid option, please try again.");
        }
    }

    /**
     * Captures multiple lines of input text from the console until a blank line is submitted.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the combined lines of text separated by newline characters.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @return String The concatenated multiline input block.
     */
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
     * Captures an integer constraint while handling formatting errors.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns a structurally validated integer.
     * </p>
     * @param prompt The console message instruction.
     * @return int The valid integer read.
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

    /**
     * Captures a float metric value while catching parsing errors.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns a structurally validated floating-point value.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @return float The parsed float.
     */
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

    /**
     * Captures a float, ensuring it sits strictly within a designated range.
     * <p>
     * <b>Precondition:</b> min must be less than or equal to max.<br>
     * <b>Postcondition:</b> Returns a validated float safely clamped between the min and max bounds.
     * </p>
     * @param prompt UI message instructions presented to the terminal.
     * @param min    Lower bounding threshold.
     * @param max    Upper bounding threshold.
     * @return float The bounded floating-point choice.
     */
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
