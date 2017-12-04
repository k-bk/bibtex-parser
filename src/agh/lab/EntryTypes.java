package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryTypes {
    public Map<String, List<String>> required = new HashMap<>();
    public Map<String, String> optional = new HashMap<>();

    public EntryTypes(){
        LinkedList<String> article = new LinkedList<>();
        article.add("author");
        article.add("title");
        article.add("journal");
        article.add("year");
        article.add("volume");
    }

    public List<String> getRequired(String type) {
        return required.get(type);
    }
}
