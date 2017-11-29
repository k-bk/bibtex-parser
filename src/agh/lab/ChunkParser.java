package agh.lab;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChunkParser {
    private Scanner inputFile;
    private Pattern chunkBegin = Pattern.compile("\\w*@");

    public ChunkParser(Scanner inputFile) {
        this.inputFile = inputFile;
    }

    public LinkedList<String> nextChunk() {

        LinkedList<String> chunk = new LinkedList<>();
        String line = "";

        while(inputFile.hasNextLine()) {
            line = inputFile.nextLine();
            Matcher m = chunkBegin.matcher(line);
            if(m.find()) {
                break;
            }
        }


        int braces = countBraces(line);
        chunk.add(line);

        while(braces != 0 && inputFile.hasNextLine()) {
            line = inputFile.nextLine();
            chunk.add(line);
            braces += countBraces(line);
        }

        return chunk;
    }

    public boolean hasNext() {
        return inputFile.hasNextLine();
    }

    private int countBraces(String line) {
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
