package agh.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEntryParser {
    private static Pattern stringPattern = Pattern.compile("\\s*.*[({]\\s*(\\S+)\\s*=\\s*\"([^\"]+).*");
    private static Pattern noquote = Pattern.compile("\"(.*)\"");
    private Map<String, String> stringMap = new HashMap<>();

    public String parse(String line) {
        StringBuilder parsedLine = new StringBuilder();
        for(String part : line.split("\\s*#\\s*")) {
            if(stringMap.containsKey(part.toLowerCase())) {
               parsedLine.append(stringMap.get(part.toLowerCase()));
            } else {
                Matcher nq = noquote.matcher(part);
                if(nq.find())
                    parsedLine.append(nq.group(1));
                else
                    parsedLine.append(part);
            }
        }
        return parsedLine.toString();
    }

    public void add(String stringEntry) {
        Matcher m = stringPattern.matcher(stringEntry);
        if(m.find()) {
            stringMap.put(m.group(1).toLowerCase(), m.group(2));
        }
    }
}
