package duke;

/**
 * Class for parsing input of user for duke.Duke.
 * @author Liew Jian Hong
 */

public class Parser {

    private static final int COMMAND_SPACING = 4;
    /**
     * Returns the result of parsing the user input in an array of string where index 0 is command, index 1 is
     * task description and index 2 is by/at date and time.
     *
     * @param item User's input.
     * @return String array of parsed item.
     */
    public String[] parse(String item) {
        String[] result = new String[]{"", "", "", "false"};
        String[] split = item.split(" ");
        int firstWordLength = split[0].length();
        result[0] = split[0];

        String remainder = "";
        int separator = 0;

        //obtain information of task, after the /at or /by command
        if (split.length > 1) {
            remainder = item.substring(firstWordLength + 1);
            for (int i = 0; i < remainder.length(); i++) {
                if (Character.toString(remainder.charAt(i)).equals("/")) {
                    separator = i;
                    break;
                }
            }
        }

        //separate sections of the input, eg [deadline, return books, 2021-09-06, false]
        if (separator == 0) {
            result[1] = remainder;
        } else {
            result[1] = remainder.substring(0, separator - 1);
            result[2] = remainder.substring(separator + COMMAND_SPACING);
            System.out.println(result[2]);
        }
        return result;
    }

    /**
     * Returns parsed input from file containing stored tasks.
     *
     * @param input String from file.
     * @return Parsed input from file.
     */
    public String[] parseFromFile(String input) {
        String[] result = new String[]{"", "", "", ""};
        String[] split = input.split("--");
        result[0] = split[0];
        result[1] = split[2];
        result[2] = split[3];
        result[3] = split[1];
        return result;
    }

}
