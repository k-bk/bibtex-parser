package agh.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class BibTex {

    public static void main(String[] args) {

        File file = new File(args[0]);
        char border = args[1].toCharArray()[0];
        FilterSystem filterSystem = new FilterSystem();
        TableVisualizer visualizer = new TableVisualizer();
        EntryTypes entryTypes = new EntryTypes();
        try {
            Scanner input = new Scanner(file);
            ChunkParser chunkParser = new ChunkParser(input);
            EntryParser entryParser = new EntryParser(filterSystem);
            while (chunkParser.hasNext()) {
                try {
                    String fileChunk = chunkParser.nextChunk();
                    entryParser.parse(fileChunk);
                } catch (ParserException e) {
                    System.out.println(e);
                }
            }
            input.close();
        } catch(FileNotFoundException ex) {
            System.out.println(ex);
        }

        try {
            entryTypes.checkEntries(filterSystem.getEntries());
        } catch (ParserException e) {
            System.out.println(e);
        }

        System.out.println(visualizer.dump(
                filterSystem.select("author", ""),
                border, 15, 75));
        //System.out.println(visualizer.dump(filterSystem.select(), border, 15, 75));
    }
}
