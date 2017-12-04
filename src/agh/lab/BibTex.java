package agh.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BibTex {

    public static void main(String[] args) {

        File file = new File(args[0]);
        List<Entry> entries = new ArrayList<>();
        FilterSystem filterSystem = new FilterSystem();
        try {
            Scanner input = new Scanner(file);
            ChunkParser chunkParser = new ChunkParser(input);
            while (chunkParser.hasNext()) {
                LinkedList<String> entryChunk = chunkParser.nextChunk();
                Entry entry = new Entry(entryChunk, filterSystem);
                entries.add(entry);
            }
            input.close();

        } catch(FileNotFoundException ex) {
            System.out.println(ex);
        }

        System.out.println(filterSystem.getEntriesWithTag("publisher"));
    }
}
