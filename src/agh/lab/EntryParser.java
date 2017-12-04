package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryParser {
    private LinkedList<String> inputBuffer;
    private static StringEntryParser stringParser = new StringEntryParser();

    public EntryParser(LinkedList<String> inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public Map<String, String> parse() {

        Map<String, String> tags = new HashMap<>();

        Pattern typePattern = Pattern.compile("\\s*@([^{(]+).*");
        /* Pattern explanation:
            \\s*@ - find @
            ([^({]+).* - match any character till you find braces
         */

        for(String line : inputBuffer) {
            Matcher m = typePattern.matcher(line);
            if(m.find()) {
                String type = m.group(1).toLowerCase();
                switch(type) {
                    case "string":
                        stringParser.add(line);
                    case "preamble":
                    case "comment":
                        return new HashMap<>();
                    default:
                        Pattern typeAndIDPattern = Pattern.compile("\\s*@([^{(]+)[{(]\\s*(.*)\\s*,");
                        Matcher m2 = typeAndIDPattern.matcher(line);
                        if(m2.find()) {
                            String ID = m2.group(2);
                            tags.put("type", type);
                            tags.put("ID", ID);
                        }
                }
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
                    value = stringParser.parse(m.group(5));
                }
                tags.put(name, value);
            }
        }

        return tags;
    }
}
