package agh.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEntryParser {
    Pattern stringPattern = Pattern.compile("\\s*.*[({]\\s*(\\S+)\\s*=\\s*\"([^\"]+).*");
    Map<String, String> stringMap = new HashMap<>();

    public String parse(String line) {
        for(String part : line.split("\\s*#\\s*")) {
            if(stringMap.containsKey(part.toLowerCase())) {
                System.out.println("wowo found a string");
            }
        }
        System.out.println("------------------");
        return line;
    }

    public void add(String stringEntry) {
        Matcher m = stringPattern.matcher(stringEntry);
        if(m.find()) {
            stringMap.put(m.group(1).toLowerCase(), m.group(2));
            System.out.println(stringMap.size());
        }
    }
}
