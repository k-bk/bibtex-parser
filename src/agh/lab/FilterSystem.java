package agh.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterSystem {
    private Map<String, Filter> filters = new HashMap<>();
    private Map<String, Entry> entries = new HashMap<>();

    public void update(Entry entry){
        if (!entry.getTags().containsKey("ignored")) {
            for (Map.Entry<String, String> e : entry.getTags().entrySet()) {
                if (filters.containsKey(e.getKey().toLowerCase())) {
                    filters.get(e.getKey().toLowerCase()).update(entry);
                } else {
                    filters.put(e.getKey().toLowerCase(), new Filter(entry));
                }
            }
            entries.put(entry.getId(), entry);
        }
    }

    public Entry getEntry(String id) {
        return entries.getOrDefault(id, null);
    }

    public Map<String, Entry> getEntries() {
        return entries;
    }

    public List<Entry> getEntriesWithTag(String tag) {
        tag = tag.toLowerCase();
        if(filters.containsKey(tag)) {
            return filters.get(tag).toList();
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

    public List<Entry> select() {
        List<Entry> resultList = new LinkedList<>();
        for (Entry e : entries.values()) {
            resultList.add(e);
        }
        return resultList;
    }
}
