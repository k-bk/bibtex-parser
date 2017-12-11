package agh.lab;

import gnu.getopt.Getopt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BibTex {

    private File file;
    private char border = '#';
    private String tag;
    private String pattern = "";

    public static void main(String[] args) {

        BibTex bibTex = new BibTex();
        bibTex.readArgs(args);

        FilterSystem filterSystem = new FilterSystem();
        TableVisualizer visualizer = new TableVisualizer();
        EntryTypes entryTypes = new EntryTypes();
        try {
            Scanner input = new Scanner(bibTex.file);
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
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

        try {
            entryTypes.checkEntries(filterSystem.getEntries());
        } catch (ParserException e) {
            System.out.println(e);
        }

        if (bibTex.tag != null) {
            System.out.println(visualizer.dump(filterSystem.select(bibTex.tag, bibTex.pattern), bibTex.border, 15, 75));
        } else {
            System.out.println(visualizer.dump(filterSystem.select(), bibTex.border, 15, 75));
        }
    }

    public void readArgs(String[] args) {
        int c;
        Getopt g = new Getopt("bibtex-parser", args, "f:b:hs:p:");
        while ((c = g.getopt()) != -1) {
            switch(c) {
                case 'f':
                    file = new File(g.getOptarg());
                    break;
                case 's':
                    tag = g.getOptarg();
                    break;
                case 'p':
                    pattern = g.getOptarg();
                    break;
                case 'h':
                    System.out.println("-f \t FileName");
                    System.out.println("-s \t Select by tag eg. author, title");
                    System.out.println("-p \t Pattern used for selection.");
                    System.out.println("-b \t Frame character.");
                    System.out.println("-h \t Display help message.");
                    break;
                case 'b':
                    border = g.getOptarg().toCharArray()[0];
                    break;
                default:
                    System.out.println("Option " + c + " not recognized. Type -h for help.");



            }
        }
    }
}
