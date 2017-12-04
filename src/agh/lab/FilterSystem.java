package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FilterSystem {
    private Map<String, EntryFilter> filters = new HashMap<>();

    public void update(Entry entry){
        for(Map.Entry<String, String> e : entry.getTags().entrySet()) {
            if(filters.containsKey(e.getKey())) {
                filters.get(e.getKey()).update(entry);
            } else {
                filters.put(e.getKey(), new EntryFilter(entry));
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
}
