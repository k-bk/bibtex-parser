package agh.lab;

import java.util.HashMap;
import java.util.Map;

public class Entry {

    private String type;
    private String id;
    private Map<String, String> tags = new HashMap<>();

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public Entry(String type, String id, Map<String, String> tags) throws ParserException {
        this.type = type;
        this.id = id;
        this.tags = tags;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(type + " | " + id + "\n");
        for (Map.Entry<String, String> e : tags.entrySet()) {
            result.append(e.getKey() + " | " + e.getValue() + "\n");
        }
        return result.toString();
    }

    public String toTableString(char border, int width1, int width2) {
        StringBuilder builder = new StringBuilder();
        builder.append(borderedString("type", type, width1, width2, border));
        builder.append(borderedString("ID", id, width1, width2, border));
        for (Map.Entry<String, String> e : tags.entrySet()) {
            if (e.getKey().equals("author")) {
                String first = "author";
                for (String part : e.getValue().split("\\s+and\\s+")) {
                    builder.append(borderedString(first, part, width1, width2, border));
                    first = "";
                }
            } else {
                builder.append(borderedString(e.getKey(), e.getValue(), width1, width2, border));
            }
        }
        return builder.toString();
    }

    private String borderedString(String first, String second, int width1, int width2, char border) {
        String firstCol = String.format("%-" + (width1 - 1) + "s", first);
        String secondCol = String.format("%-" + (width2 - 2) + "s", second);
        return(border + " " + firstCol
                + " " + border + " "
                + secondCol
                + border + "\n");
    }
}
