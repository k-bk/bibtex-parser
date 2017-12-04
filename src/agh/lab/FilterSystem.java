package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterSystem {
    private Map<String, Filter> filters = new HashMap<>();

    public void update(Entry entry){
        for(Map.Entry<String, String> e : entry.getTags().entrySet()) {
            if(filters.containsKey(e.getKey())) {
                filters.get(e.getKey()).update(entry);
            } else {
                filters.put(e.getKey(), new Filter(entry));
            }
        }
    }

    public List<Entry> getEntriesWithTag(String tag) {
        if(filters.containsKey(tag.toLowerCase())) {
            return filters.get(tag.toLowerCase()).toList();
        } else {
            return new LinkedList<>();
        }
    }

    public List<Entry> select(String tag, String pattern) {
        tag = tag.toLowerCase();
        List<Entry> resultList = new LinkedList<>();
        for(Entry e : getEntriesWithTag(tag)) {
            Pattern p = Pattern.compile(".*" + pattern.toLowerCase() + ".*");
            String value = e.getTags().get(tag);
            Matcher m = p.matcher(value.toLowerCase());
            if(m.find()) {
                resultList.add(e);
            }
        }
        return resultList;
    }
}
