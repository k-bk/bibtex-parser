package agh.lab;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryParser {
    private LinkedList<String> inputBuffer;

    public EntryParser(LinkedList<String> inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public void parse() {

        Pattern typeAndIDPattern = Pattern.compile("\\s*@([^{(]+)[{(]\\s*(.+)\\s*,");
        /* Pattern explanation:
            \\s*@ - find @
            ([^({])[{(] - match any character till you find braces
            \\s*(.+)\\s*, - match any non-whitespace characters till you find comma
         */

        for(String line : inputBuffer) {
            Matcher m = typeAndIDPattern.matcher(line);
            if(m.find()) {
                String type = m.group(1).toLowerCase();
                String ID = m.group(2);
                System.out.println("type = " + type);
                System.out.println("ID = " + ID);
                break;
            }
        }

        Pattern assignPattern = Pattern.compile("\\s*(\\S+)\\s*=\\s*([{\"](.*)[}\"]|(\\d+)|(.*)),");
        /* Pattern explanation:
            \s*(\S+)\s*= - match anything except whitespaces till you find '=' sign
            ([{"](.*)[}"]) - match opening [{"], then anything, then closing [}"] (group 3)
            |(.*) - or match anything without delimiters (group 4)
         */

        for(String line : inputBuffer) {
            Matcher m = assignPattern.matcher(line);
            if(m.find()) {
                String name = m.group(1).toLowerCase();
                String value;

                /* check type of value:
                    surrounded with braces - normal treatment of string
                    number - same as above
                    something different - may contain strings and concatenation '#'
                 */
                if(m.group(3) != null) {
                    value = m.group(3);
                } else if(m.group(4) != null) {
                    value = m.group(4);
                } else {
                    // TODO here we should parse the concatenation
                    value = m.group(5);
                }
                System.out.println("key = " + name + ", value = " + value);
            }
        }
    }
}
