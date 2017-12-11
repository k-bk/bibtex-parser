package agh.lab;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private List<Entry> entryList = new ArrayList<>();

    public Filter() {}
    public Filter(Entry entry) {
        entryList.add(entry);
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
