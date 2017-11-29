package agh.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class BibTex {

    public static void main(String[] args) {

        File file = new File(args[0]);
        try {
            Scanner input = new Scanner(file);
            ChunkParser chunkParser = new ChunkParser(input);
            while (chunkParser.hasNext()) {
                LinkedList<String> entryChunk = chunkParser.nextChunk();
                EntryParser entryParser = new EntryParser(entryChunk);
                entryParser.parse();
                System.out.println();
            }
            input.close();

        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
