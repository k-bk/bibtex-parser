package agh.lab;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EntryParser {

    private FilterSystem filterSystem;
    private static StringParser stringParser = new StringParser();

    // Patterns
    private static Pattern typePattern = Pattern.compile("\\s*@([^{(]+).*");
        /* Pattern explanation:
            \\s*@ - find @
            ([^({]+).* - match any character till you find braces
         */
    private static Pattern IDPattern = Pattern.compile("[{(]\\s*(.*)\\s*");
    private static Pattern assignPattern = Pattern.compile("\\s*(\\S+)\\s*=\\s*([{\"](.*)[}\"]|(\\d+)|(.*))");
        /* Pattern explanation:
            \s*(\S+)\s*= - match anything except whitespaces till you find '=' sign
            ([{"](.*)[}"]) - match opening [{"], then anything, then closing [}"] (group 3)
            |(.*) - or match anything without delimiters (group 4)
         */

    public EntryParser(FilterSystem filterSystem) {
        this.filterSystem = filterSystem;
    }

    public void parse(String inputBuffer) throws ParserException {

        String[] splitBuffer = inputBuffer.split(",\\n");
        String header = splitBuffer[0];

        String id;
        String type;
        Map<String, String> tags = new HashMap<>();

        Matcher typeM = typePattern.matcher(header);
        if (typeM.find()) {
            type = typeM.group(1).toLowerCase();
            if (type.equals("string")) {
                stringParser.add(inputBuffer);
                return;
            } else if (type.equals("comment") || type.equals("preamble")) {
                return;
            } else {
                Matcher idM = IDPattern.matcher(header);
                if (idM.find())
                    id = idM.group(1);
                else
                    throw new ParserException("No ID specified for \"@" + type + "{\" declared");
            }
        } else {
            throw new ParserException("No type specified after @.");
        }

        for (String line : splitBuffer) {
            Matcher m = assignPattern.matcher(line);
            if (m.find()) {
                String name = m.group(1).toLowerCase();
                String value;
                /* check type of value:
                    pattern = "\s*(\S+)\s*=\s*([{"](.*)[}"]|(\d+)|(.*))"
                    surrounded with braces - normal treatment of string
                    number - same as above
                    something different - may contain strings and concatenation '#'
                 */
                if (m.group(3) != null) {
                    value = m.group(3);
                } else if (m.group(4) != null) {
                    value = m.group(4);
                } else {
                    value = stringParser.parse(m.group(5));
                }
                tags.put(name.toLowerCase(), value);
            }
        }

        if (!type.isEmpty() && !id.isEmpty() && !tags.isEmpty()) {
            Entry newEntry = new Entry(type, id, tags);
            filterSystem.update(newEntry);
        }
    }
}
