package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Entry{

    private String type;
    private Map<String, String> tags = new HashMap<>();

    public Entry(LinkedList<String> input, FilterSystem filterSystem) {
        EntryParser parser = new EntryParser(input);
        tags = parser.parse();
        filterSystem.update(this);
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> e : tags.entrySet()) {
            result.append(e.getKey() + " | " + e.getValue() + "\n");
        }
        return result.toString();
    }
}