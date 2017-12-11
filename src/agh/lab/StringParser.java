package agh.lab;

import jdk.nashorn.internal.runtime.ParserException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {
    private static Pattern stringPattern = Pattern.compile("\\s*.*[({]\\s*(\\S+)\\s*=\\s*(\"[^\"]+.).*");
    private static Pattern noquote = Pattern.compile("[\"|{](.*)[\"|}]");
    private static Pattern isDigit = Pattern.compile("(\\d+)");
    private Map<String, String> stringMap = new HashMap<>();

    public StringParser() {
        stringMap.put("jan","January");
        stringMap.put("feb","February");
        stringMap.put("mar","March");
        stringMap.put("apr","April");
        stringMap.put("may","May");
        stringMap.put("jun","June");
        stringMap.put("jul","July");
        stringMap.put("aug","August");
        stringMap.put("sep","September");
        stringMap.put("oct","October");
        stringMap.put("nov","November");
        stringMap.put("dec","December");
    }

    public String parse(String line) throws ParserException {
        StringBuilder parsedLine = new StringBuilder();
        for(String part : line.split("\\s*#\\s*")) {
            if(stringMap.containsKey(part.toLowerCase())) {
               parsedLine.append(stringMap.get(part.toLowerCase()));
            } else {
                Matcher nq = noquote.matcher(part);
                if(nq.find())
                    parsedLine.append(nq.group(1));
                else
                    if(isDigit.matcher(part).find()) {
                        parsedLine.append(isDigit.matcher(part).group(1));
                    } else {
                        throw new ParserException("No string \"" + part + "\" was defined\n Line: " + line);
                    }

            }
        }
        return parsedLine.toString();
    }

    public void add(String stringEntry) {
        Matcher m = stringPattern.matcher(stringEntry);
        if(m.find()) {
            String value = parse(m.group(2));
            stringMap.put(m.group(1).toLowerCase(), value);
        }
    }
}
