package agh.lab;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChunkParser {
    private Scanner inputFile;
    private Pattern chunkBegin = Pattern.compile("\\w*@");

    public ChunkParser(Scanner inputFile) {
        this.inputFile = inputFile;
    }

    public String nextChunk() throws ParserException {

        // Find next occurrence of the chunkBegin pattern
        String line = "";
        while (inputFile.hasNextLine()) {
            line = inputFile.nextLine();
            Matcher m = chunkBegin.matcher(line);
            if (m.find()) {
                break;
            }
        }

        // Load lines into a chunk till you find ending bracket
        int brackets = countBrackets(line);

        StringBuilder chunkBuilder = new StringBuilder();
        chunkBuilder.append(line + "\n");

        while (brackets > 0 && inputFile.hasNextLine()) {
            line = inputFile.nextLine();
            chunkBuilder.append(line + "\n");
            brackets += countBrackets(line);
        }

        if (brackets < 0 || (brackets > 0 && !inputFile.hasNextLine()))
            throw new ParserException("Unmatched {} bracket at \"" + line + "\" line.");

        return chunkBuilder.toString();
    }

    public boolean hasNext() {
        return inputFile.hasNextLine();
    }

    private int countBrackets(String line) {
        int result = 0;
        for(char c : line.toCharArray()) {
            if(c == '{')
                result++;
            if(c == '}')
                result--;
        }
        return result;
    }

}
