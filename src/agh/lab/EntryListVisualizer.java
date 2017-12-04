package agh.lab;

import java.util.List;
import java.util.Map;

public class EntryListVisualizer {

    /**
     * Convert given list of Entries into a table. The borders of table
     * are given as char Border argument, the widths of collums are
     * given as 3rd and 4th arguments.
     */
    public String dump(List<Entry> entryList, char border, int col1Width, int col2Width) {
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < col1Width + col2Width + 3; i++)
            line.append(border);
        line.append("\n");

        StringBuilder builder = new StringBuilder();
        builder.append(line.toString());
        for(Entry entry : entryList) {
            builder.append(entry.toTableString(border, col1Width, col2Width));
            builder.append(line.toString());
        }
        return builder.toString();
    }
}
