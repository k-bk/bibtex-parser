package agh.lab;

import java.util.ArrayList;
import java.util.List;

public class EntryFilter{
    private List<Entry> entryList = new ArrayList<>();

    public EntryFilter(Entry entry) {
        entryList.add(entry);
    }

    public EntryFilter() {

    }

    public void update(Entry entry){
        if(!entryList.contains(entry)) {
            entryList.add(entry);
        }
    }

    public List<Entry> toList() {
        return entryList;
    }
}
